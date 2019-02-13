import java.util.ArrayList;
import java.util.List;


/**
 * http://learnmeabitcoin.com/glossary/transaction-data
 */
public class ExtractChamps {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16

    private static final int VERSION = 8; // 4 octets

    private static final int INPUT_TXID = 64; // 32 octets
    private static final int INPUT_VOUT = 8; // ...
    private static final int INPUT_SEQUENCE = 8;

    private static final int OUTPUT_COUNT = 2;
    private static final int OUTPUT_VALUE = 16;
    /**
     *      affiche tous les champs d'une transaction
     */
    public static void extractChampsTransaction(){

        String transaction = CheckEnter.enterHexadecimal("Veuillez rentrer la chaîne hexadécimale complète d'une transaction" +
                " ou bloc sur une ligne :" +
                "\nExemple : 0100000001f129de033c57582efb464e94ad438fff493cc4de4481729b85971236858275c2010000006a4730440220155a2ea4" +
                "a702cadf37052c87bfe46f0bd24809759acff8d8a7206979610e46f6022052b688b784fa1dcb1cffeef89e7486344b814b0c578133a7b0bce5b" +
                "e978a9208012103915170b588170cbcf6380ef701d19bd18a526611c0c69c62d2c29ff6863d501affffffff02ccaec817000000001976a91425" +
                "27ce7f0300330012d6f97672d9acb5130ec4f888ac18411a000000000017a9140b8372dffcb39943c7bfca84f9c40763b8fa9a068700000000");

        if (transaction.startsWith("0x")) transaction = transaction.substring(2);

        List<String> listChamp = new ArrayList();
        int pC = 0; // position curseur
        String upcomingOf;
        int uO;

        listChamp.add( "\nVersion : " + LittleEndianToHex.convLittEndianToHex(transaction.substring(pC, pC = VERSION)));

        int inputCount = 2;
        switch(transaction.substring(pC, pC +2)){

            case "fd":
                transaction = transaction.substring(pC + 2);
                inputCount = 4;
                break;
            case "fe":
                transaction = transaction.substring(pC + 2);
                inputCount = 8;
                break;
            case "ff":
                transaction = transaction.substring(pC + 2);
                inputCount = 16;
            default:
                break;

        }

        listChamp.add( "\nInput Count : " + Integer.parseInt(upcomingOf = transaction.substring(pC, pC += inputCount), 16));
        uO = Integer.parseInt(upcomingOf); // on récupère le nombre de Input(s)


        for (int i = 0; i < uO; i++){ // on affiche le(s) Input(s)

            transaction = transaction.substring(pC);
            listChamp.add("\nInput" +
                    "\nTXID : " + LittleEndianToHex.convLittEndianToHex(transaction.substring(0, pC = INPUT_TXID)) +
                    "\nVOUT : " + LittleEndianToHex.convLittEndianToHex(transaction.substring(pC, pC += INPUT_VOUT)));

            int scriptSigSize = 2;
            switch(transaction.substring(pC, pC +2)){

                case "fd":
                    transaction = transaction.substring(pC + 2);
                    scriptSigSize = 4;
                    break;
                case "fe":
                    transaction = transaction.substring(pC + 2);
                    scriptSigSize = 8;
                    break;
                case "ff":
                    transaction = transaction.substring(pC + 2);
                    scriptSigSize = 16;
                default:
                    break;

            }

            listChamp.add(
                    "ScriptSig Size : " + Integer.parseInt(upcomingOf = transaction.substring(pC, pC += scriptSigSize), 16) + // on récupère la taille du ScriptSig
                    "\nScriptSig : " + transaction.substring(pC, pC += getDecimal(upcomingOf)*2) +
                    "\nSequence : " + LittleEndianToHex.convLittEndianToHex(transaction.substring(pC, pC += INPUT_SEQUENCE))
            );
        }


        listChamp.add("\nOutput Count : " + Integer.parseInt(upcomingOf = transaction.substring(pC, pC += OUTPUT_COUNT),16)); // on récupère le nombre de output(s)
        uO = getDecimal(upcomingOf);


        for (int i = 0; i < uO; i++){ // on affiche le(s) Output(s)

            transaction = transaction.substring(pC);
            listChamp.add("\nOutput" +
                    "\nValue : " + LittleEndianToHex.convLittEndianToHex(transaction.substring(0, pC = OUTPUT_VALUE)));

            int scriptPubKeySize = 2;
            switch(transaction.substring(pC, pC +2)){

                case "fd":
                    transaction = transaction.substring(pC + 2);
                    scriptPubKeySize = 4;
                    break;
                case "fe":
                    transaction = transaction.substring(pC + 2);
                    scriptPubKeySize = 8;
                    break;
                case "ff":
                    transaction = transaction.substring(pC + 2);
                    scriptPubKeySize = 16;
                default:
                    break;

            }

            listChamp.add(
                    "ScriptPubKey Size : " + Integer.parseInt(upcomingOf = transaction.substring(pC, pC += scriptPubKeySize), 16) + // on récupère la taille du ScriptPubKey
                    "\nScriptPubKey : " + transaction.substring(pC, pC += getDecimal(upcomingOf)*2)
            );
        }

        listChamp.add("\nLockTime " + LittleEndianToHex.convLittEndianToHex(transaction.substring(pC)));


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

    /*public static void main(String[] args){

        ExtractChamps eC = new ExtractChamps();

        String transaction = "0100000001f129de033c57582efb464e94ad438fff493cc4de4481729b859712368582" +
                "75c2010000006a4730440220155a2ea4a702cadf37052c87bfe46f0bd24809759acff8" +
                "d8a7206979610e46f6022052b688b784fa1dcb1cffeef89e7486344b814b0c578133a7" +
                "b0bce5be978a9208012103915170b588170cbcf6380ef701d19bd18a526611c0c69c62" +
                "d2c29ff6863d501affffffff02ccaec817000000001976a9142527ce7f0300330012d6" +
                "f97672d9acb5130ec4f888ac18411a000000000017a9140b8372dffcb39943c7bfca84" +
                "f9c40763b8fa9a068700000000";


        eC.extractChampsTransaction(transaction);


    }*/



}
