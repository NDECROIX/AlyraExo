import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionFields {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16

    private static final int TXID = 32; // 32 octets
    private static final int VOUT = 4; // 4 octets

    private void separatedFields(String transaction){

        List<String> listChamp = new ArrayList();
        List<String> hexDecimal = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int tailleChamp;

        if (transaction.length()%2 != 0)transaction = "0" + transaction;

        for (int i = 0; i < transaction.length(); i += 2) hexDecimal.add(transaction.substring(i, i+2)); // on fait une liste d'octet

        // AFFICHE Input Count
        listChamp.add("\nInput Count : " + hexDecimal.remove(0));

        // AFFICHE TXID
        Collections.reverse(hexDecimal.subList(0,TXID)); // on passe en big endian
        for (int i = 0; i < TXID ; i++) sb.append(hexDecimal.get(i));
        listChamp.add("TXID : " + sb.toString());
        hexDecimal = hexDecimal.subList(TXID, hexDecimal.size());

        // AFFICHE VOUT
        sb = new StringBuilder();
        Collections.reverse(hexDecimal.subList(0,VOUT)); // on passe en big endian
        for (int i = 0; i < VOUT ; i++) sb.append(hexDecimal.get(i));
        listChamp.add("VOUT : " + sb.toString());
        hexDecimal = hexDecimal.subList(VOUT, hexDecimal.size());

        // AFFICHE ScriptSig Size
        tailleChamp = getDecimal(hexDecimal.get(0));
        listChamp.add("ScriptSig Size : " + hexDecimal.remove(0));

        // AFFICHE ScriptSig
        sb = new StringBuilder();
        for (int i = 0; i < tailleChamp ; i++) sb.append(hexDecimal.get(i));
        listChamp.add("ScriptSig : " + sb.toString());
        hexDecimal = hexDecimal.subList(tailleChamp, hexDecimal.size());

        // AFFICHE la Sequence
        sb = new StringBuilder();
        Collections.reverse(hexDecimal); // on passe en big endian
        for (int i = 0; i < hexDecimal.size() ; i++) sb.append(hexDecimal.get(i));
        listChamp.add("Sequence : " + sb.toString());

        for (String c : listChamp){
            System.out.println(c);
        }
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

        TransactionFields ct = new TransactionFields();

        String transaction = "1941e985075825e09de53b08cdd346bb67075ef0ce5c94f98853292d4bf94c10d010000006b483045022100ab44ef4" +
                "25e6d85c03cf301bc16465e3176b55bba9727706819eaf07cf84cf52d02203f7dc7ae9ab36bead14dd3c83c8c030bf8" +
                "ce596e692021b66441b39b4b35e64e012102f63ae3eba460a8ed1be568b0c9a6c947abe9f079bcf861a7fdb2fd577ed" +
                "48a81Feffffff";

        ct.separatedFields(transaction);

    }
}
