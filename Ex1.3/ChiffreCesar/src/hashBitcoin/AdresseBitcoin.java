package hashBitcoin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AdresseBitcoin {


    private String hachage(String key){




        if (key != null) {
            try {

                MessageDigest md = MessageDigest.getInstance("SHA-256"); // on par sur la fonction de hachage SHA-256


                byte[] nomberHex = hexStringToByteArray(key);
                byte[] hashSHA = md.digest(nomberHex); // hash la clef en octets en SHA-256
                byte[] hashRipemd =  Ripemd160.getHash(hashSHA); // hash la clef SHA-256 en RIPEMD160

                StringBuilder sb = new StringBuilder();


                sb.append("00"); //Identifiant 0x00 pour les adresses du réseau principal  et 0x6f pour les adresses du réseau testnet.

                for (int i = 0; i < hashRipemd.length; i++) { // on convertie en hex
                    sb.append(String.format("%02x", hashRipemd[i]));
                }

                System.out.println(sb.toString());
                nomberHex = hexStringToByteArray(sb.toString());

                byte[] hashControl = md.digest(md.digest(nomberHex)); // double hash (SHA-256) de l’adresse de l’étape précédente

                for (int i = 0; i < 4; i++) { // 4 octets de controle
                    sb.append(String.format("%02x", hashControl[i]));
                }

                System.out.println(sb.toString());


                return new Base58().convertToBase58(sb.toString());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }

        } else
        {
            System.out.println("Hachage impossible sur une clef null");
            return null;
        }

    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static void main(String[] args){

        AdresseBitcoin fl = new AdresseBitcoin();

        System.out.println(fl.hachage("0250863ad64a87ae8a2fe83c1af1a8403cb53f53e486d8511dad8a04887e5b2352"));


    }


}
