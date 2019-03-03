

public class Main {

    public static void main(String[] args){

        AsciiArt.asciiBitcoin();
        boolean restart = true;
        do {
            System.out.println("\nVeuillez choisir une des options suivantes :\n" +
                    "\n 1 : Hexadécimal -> décimal                      1 : 0x 0B 17 C0 DE --> 186106078" +
                    "\n 2 : Décimal -> hexadécimal                      2 : 186106078 --> 0x 0B 17 C0 DE" +
                    "\n 3 : Hexadécimal little endian -> hexadécimal    3 : 0x DE C0 17 0B --> 0x 0B 17 C0 DE " +
                    "\n 4 : VarInt -> décimal                           4 : 0x FE DE C0 17 0B --> 186106078" +
                    "\n 5 : Champ Bits -> Cible correspondante          5 : 0x 18 06 96 F4 --> 0x00000000000000000696f4000000000000000000000000000000000000000000" +
                    "\n 6 : Cible -> Difficulté                         6 : Bits : 0x 1C 0A E4 93 --> 23.5" +
                    "\n 7 : Details transaction                         7 : 0100000001f129de033c5758... --> Version : 01000000 -> Input Count : 01... " +
                    "\n 8 : Connection à Bitcoind " +
                    "\n 9 : Quitter \n   ");

            switch (CheckEnter.enterNumber("Saisir un nombre entre 1 et 9 :", 1, 9)) {

                case 1:
                    HexToDecimal.hexToDecimal();
                    break;
                case 2:
                    DecimalToHex.decimalToHex();
                    break;
                case 3:
                    LittleEndianToHex.littleEndianToHex();
                    break;
                case 4:
                    VarIntToDecimal.VarIntToDecimal();
                    break;
                case 5:
                    BitsToTarget.bitsToTarget();
                    break;
                case 6:
                    TargetToDifficulty.targetToDifficulty();
                    break;
                case 7:
                    ExtractChamps.extractChampsTransaction();
                    break;
                case 8:
                    BitcoinConnection.connectionBitcoind();
                    break;
                case 9:
                    restart = false;
                    break;
                default:
                    break;
            }
        }while(restart);
    }
}
