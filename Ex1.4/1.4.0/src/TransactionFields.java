import java.util.ArrayList;
import java.util.List;

public class TransactionFields {

    private static final int HASH = 64; // 32 octets
    private static final int INDEX = 8; // 4 octets
    private static final int SEQUENCE = 8;



    private void separatedFields(String transaction){

        List<String> listChamp = new ArrayList();
        int transactionSize = transaction.length();
        int cP; // curseur position

        listChamp.add(transaction.substring(0, cP = HASH)); // Le hash de la transaction passée où sont les bitcoins à dépenser (sur 32 octets)

        listChamp.add(transaction.substring(cP, cP += INDEX)); // L’index de la sortie (output) de cette transaction concernée (sur 4 octets)
        listChamp.add(transaction.substring(cP, cP = transactionSize-SEQUENCE)); // ScriptSig...
        listChamp.add(transaction.substring(cP)); // Séquence (sur 4 octets)


        for (String c : listChamp){
            System.out.println(c);
        }

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
