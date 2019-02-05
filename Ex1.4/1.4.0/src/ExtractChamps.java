import java.util.ArrayList;
import java.util.List;

public class ExtractChamps {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16

    private static final int VERSION = 8; // 4 octets

    private static final int INPUT_COUNT = 2; // 1 octet
    private static final int INPUT_TXID = 64; // 32 octets
    private static final int INPUT_VOUT = 8; // ...
    private static final int INPUT_SCRIPTSIG_SIZE = 2;
    private static final int INPUT_SEQUENCE = 8;

    private static final int OUTPUT_COUNT = 2;
    private static final int OUTPUT_VALUE = 16;
    private static final int OUTPUT_SCRIPTPUBKEY_SIZE = 2;

    public static final int LOCKTIME = 8;

    public void extractChampsTrasaction(String transaction){

        List<String> listChamp = new ArrayList();
        int pC = 0; // position curseur
        String upcomingOf;
        int uO;

        listChamp.add( "\nVersion : " + transaction.substring(pC, pC = VERSION));

        listChamp.add( "\nInput Count : " + (upcomingOf = transaction.substring(pC, pC += INPUT_COUNT)));
        uO = getDecimal(upcomingOf);


        for (int i = 0; i < uO; i++){
            transaction = transaction.substring(pC);
            listChamp.add("\nInput" +
                    "\nTXID : " + transaction.substring(0, pC = INPUT_TXID) +
                    "\nVOUT : " + transaction.substring(pC, pC += INPUT_VOUT) +
                    "\nScriptSig Size : " + (upcomingOf = transaction.substring(pC, pC += INPUT_SCRIPTSIG_SIZE)) +
                    "\nScriptSig : " + transaction.substring(pC, pC += getDecimal(upcomingOf)*2) +
                    "\nSequence : " + transaction.substring(pC, pC += INPUT_SEQUENCE)
            );
        }


        listChamp.add("\nOutput Count : " + (upcomingOf = transaction.substring(pC, pC += OUTPUT_COUNT)));
        uO = getDecimal(upcomingOf);


        for (int i = 0; i < uO; i++){
            transaction = transaction.substring(pC);
            listChamp.add("\nOutput" +
                    "\nValue : " + transaction.substring(0, pC = OUTPUT_VALUE) +
                    "\nScriptPubKey Size : " + (upcomingOf = transaction.substring(pC, pC += OUTPUT_SCRIPTPUBKEY_SIZE)) +
                    "\nScriptPubKey : " + transaction.substring(pC, pC += getDecimal(upcomingOf)*2)
                    );
        }

        listChamp.add("\nLockTime " +transaction.substring(transaction.length() - 8));


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

        ExtractChamps eC = new ExtractChamps();

        String transaction = "0100000001f129de033c57582efb464e94ad438fff493cc4de4481729b859712368582" +
                             "75c2010000006a4730440220155a2ea4a702cadf37052c87bfe46f0bd24809759acff8" +
                             "d8a7206979610e46f6022052b688b784fa1dcb1cffeef89e7486344b814b0c578133a7" +
                             "b0bce5be978a9208012103915170b588170cbcf6380ef701d19bd18a526611c0c69c62" +
                             "d2c29ff6863d501affffffff02ccaec817000000001976a9142527ce7f0300330012d6" +
                             "f97672d9acb5130ec4f888ac18411a000000000017a9140b8372dffcb39943c7bfca84" +
                             "f9c40763b8fa9a068700000000";


        eC.extractChampsTrasaction(transaction);


    }



}
