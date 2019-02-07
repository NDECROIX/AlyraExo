package bitcoin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Classe qui vérifie un Pay To Pubkey Hash P2PKH
 */
public class VerificationP2PKH {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16

    private static final int PUBLIC_KEY_COMPRESSED_SIZE = 66;
    private static final int KEY_SIZE = 2;
    private static final int OP_SIZE = 2;

    private static final String OP_EQUALVERIFY = "88";
    private static final String HEXADECIMAL = "0x";
    private static final String OP_CHECKSIG = "ac";
    private static final String OP_HASH160 = "a9";
    private static final String OP_DUP = "76";


    /**
     * @param scriptSig
     * @param scriptPubSig
     * @return vrais si le P2PKH et bon sinon false
     * Fonction qui vérifie un Pay To Pubkey Hash P2PKH
     */
    private boolean verificationP2PKH(String scriptSig, String scriptPubSig){

        Deque<String> p2pkh = new ArrayDeque<>();
        boolean validation = false;

        if (scriptSig.startsWith(HEXADECIMAL)) scriptSig = scriptSig.substring(2); // on retire la notation hexadecimal
        p2pkh.add(scriptSig.substring(0, scriptSig.length() - PUBLIC_KEY_COMPRESSED_SIZE)); // on met dans la pile la signature
        p2pkh.add(scriptSig.substring(scriptSig.length() - PUBLIC_KEY_COMPRESSED_SIZE)); // on ajoute la clef publique

        if (scriptPubSig.startsWith(HEXADECIMAL)) scriptPubSig = scriptPubSig.substring(2); // on retire la notation hexadecimal

        if (scriptPubSig.startsWith(OP_DUP)){ // on effectue la duplication de la clé public
            p2pkh.add(p2pkh.getLast());
            scriptPubSig = scriptPubSig.substring(OP_SIZE);
        }

        if (scriptPubSig.startsWith(OP_HASH160)){ // on fait un hash160 de la clef dupliquée
            p2pkh.add(hash160(p2pkh.pollLast()));
            scriptPubSig = scriptPubSig.substring(2);
        }

        int publicKeySize = getDecimal(scriptPubSig.substring(0, KEY_SIZE))*2; // on récupère la taille de la clef public
        scriptPubSig = scriptPubSig.substring(KEY_SIZE);

        p2pkh.add(scriptPubSig.substring(0, publicKeySize)); // on ajoute à la pile la clé public du scriptPubSig
        scriptPubSig = scriptPubSig.substring(publicKeySize);

        if (scriptPubSig.startsWith(OP_EQUALVERIFY)){ // on compare les deux clés publiques dans la pile
            validation = p2pkh.pollLast().equals(p2pkh.pollLast());
            scriptPubSig = scriptPubSig.substring(OP_SIZE);
        }

        if (scriptPubSig.equals(OP_CHECKSIG) && validation){  // on vérifie la validité de la clef publique avec la signature

            validation = true;

        }else {
            validation = false;
        }



        System.out.println(validation);
        return validation;

    }

    /**
     * @param hash clef publique
     * @return le hash de la clef en SHA-256 et RIPEMD160
     */
    private String hash160(String hash){

        MessageDigest md = null; // on par sur la fonction de hachage SHA-256
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] keyToByteArray = new BigInteger(hash, 16).toByteArray(); // clé Hex en Byte
        byte[] hashSHA = md.digest(keyToByteArray); // hash la clef en octets en SHA-256
        byte[] hashRipemd =  Ripemd160.getHash(hashSHA); // hash la clef SHA-256 en RIPEMD160

        StringBuilder sb = new StringBuilder();

        for (byte b : hashRipemd) { // on convertie en hex
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    /**
     * @param hex hexadéciaml en String
     * @return La valeur en int
     */
    private static int getDecimal(String hex){

        int val = 0;
        for (int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            int d = HEX_CHAR.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    public static void main(String[] args){
        VerificationP2PKH p2PKH = new VerificationP2PKH();

        String scriptSig = "0x483045022100d544eb1ede691f9833d44e5266e923dae058f702d2891e4ee87621a433ccdf4" +
                           "f022021e405c26b0483cd7c5636e4127a9510f3184d1994015aae43a228faa608362001210372" +
                           "cc7efb1961962bba20db0c6a3eebdde0ae606986bf76cb863fa460aee8475c";

        String scriptPubSig = "0x76a9147c3f2e0e3f3ec87981f9f2059537a355db03f9e888ac";

        p2PKH.verificationP2PKH(scriptSig, scriptPubSig);

    }

}
