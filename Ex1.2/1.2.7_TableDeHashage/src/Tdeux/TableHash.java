package Tdeux;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TableHash {

    private String[][] hashtable;
    private int nbrElements;

    /**
     * @param nbrElements que contient le tableau
     */
    private TableHash(int nbrElements) {

        this.nbrElements = nbrElements;

        hashtable = new String[2][nbrElements];
    }

    /**
     * @param key Valeur sur la quelle l'on calcule le hash
     * @return le hash de la cle
     */
    private String hachage(String key){

        if (key != null){
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1"); // on par sur la fonction de hachage MD5
                byte[] hashInBytes = md.digest(key.getBytes(StandardCharsets.UTF_8)); // hash la clef en octets

                StringBuilder sb = new StringBuilder();

                for (int i = 0 ; i < 4; i++){ // on prends seuelement les 8 premiers octets du hash
                    sb.append(String.format("%02x", hashInBytes[i]));
                }

                return sb.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
        else
        {
            System.out.println("Hachage impossible sur une clef null");
            return null;
        }

    }

    /**
     * @param v Lieu du serveur
     * @param k adresse ip du serveur
     */
    private void addVK( String v, String k){

        String kH = hachage(k);
        if (kH != null){
           for (int i = 0; i < nbrElements; i++){
               if (hashtable[0][i] == null){
                    hashtable[0][i] = kH;
                    hashtable[1][i] = v + " : " + k; // on concatène la valeur et la clef
                       //  j'aurais pu faire un object pour differencier le serveur et l'ip mais ce n'est pas nécessaire ici
                    break;
               }
           }
        }
    }

    /**
     * Affiche tout le tableau
     */
    private void afficheTable(){

        for (int i = 0; i < nbrElements; i++){

            if (hashtable[0][i] != null){

                System.out.println( hashtable[0][i] + " = " + hashtable[1][i]);
                System.out.println("---------------------------------------------------------");
            }
        }
    }


    public static void main(String[] args)
    {

        TableHash m = new TableHash(16);

        m.addVK("Amsterdam", "153.8.223.72");
        m.addVK("Chennai", "169.38.84.49");
        m.addVK("Dallas", "169.46.49.112");
        m.addVK("Dallas, TX, USA", "184.173.213.155");
        m.addVK("Frankfurt", "159.122.100.41");
        m.addVK("Hong Kong", "119.81.134.212");
        m.addVK("London", "5.10.5.200");
        m.addVK("London", "158.176.81.249");
        m.addVK("Melbourne", "168.1.168.251");
        m.addVK("Mexico City", "169.57.7.230");
        m.addVK("Milan", "159.122.142.111");
        m.addVK("Paris", "159.8.78.42");
        m.addVK("San Jose", "192.155.217.197");
        m.addVK("São Paulo", "169.57.163.228");
        m.addVK("Toronto", "169.56.184.72");
        m.addVK("Washington DC", "50.87.60.166");

        m.afficheTable();

    }




}
