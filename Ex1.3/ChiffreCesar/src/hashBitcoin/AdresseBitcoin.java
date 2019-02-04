package hashBitcoin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Classe qui génère une adresse type bitcoin à partir d’un nombre
 */
public class AdresseBitcoin {


    /**
     * @param key nombre en Hex dans un string
     * @return un hache de la clé
     */
    private String hachage(String key){

        if (key != null) {
            try {

                MessageDigest md = MessageDigest.getInstance("SHA-256"); // on par sur la fonction de hachage SHA-256


                byte[] keyToByteArray = new BigInteger(key, 16).toByteArray(); // clé Hex en Byte
                byte[] hashSHA = md.digest(keyToByteArray); // hash la clef en octets en SHA-256
                byte[] hashRipemd =  Ripemd160.getHash(hashSHA); // hash la clef SHA-256 en RIPEMD160

                StringBuilder sb = new StringBuilder();

                sb.append("00"); //Identifiant 0x00 pour les adresses du réseau principal  et 0x6f pour les adresses du réseau testnet.

                for (int i = 0; i < hashRipemd.length; i++) { // on convertie en hex
                    sb.append(String.format("%02x", hashRipemd[i]));
                }

                keyToByteArray = new BigInteger(sb.toString(), 16).toByteArray();

                byte[] hashControl = md.digest(md.digest(keyToByteArray)); // double hash (SHA-256) de l’adresse de l’étape précédente

                for (int i = 0; i < 4; i++) { // 4 octets de controle
                    sb.append(String.format("%02x", hashControl[i]));
                }

                return new Base58().convertToBase58(sb.toString()); // return le hash en base 58

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            System.out.println("Hachage impossible sur une clef null");
            return null;
        }

    }

    public static void main(String[] args){

        AdresseBitcoin aB = new AdresseBitcoin();

        System.out.println(aB.hachage("0250863ad64a87ae8a2fe83c1af1a8403cb53f53e486d8511dad8a04887e5b2352"));


    }


}
