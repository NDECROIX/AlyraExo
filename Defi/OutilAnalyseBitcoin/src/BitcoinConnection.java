import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class BitcoinConnection {



    public static void connectionBitcoind(){

        Scanner sc;
        System.out.println("rpcuser : ");
        sc = new Scanner(System.in);
        String user = sc.nextLine();
        System.out.println("rpcpassword : ");
        sc = new Scanner(System.in);
        String password = sc.nextLine();
        System.out.println("host : ");
        sc = new Scanner(System.in);
        String host = sc.nextLine(); //"127.0.0.1";
        System.out.println("port : ");
        sc = new Scanner(System.in);
        String port = sc.nextLine(); //"18332";

        try {
            URL url = new URL("http://" + user + ':' + password + "@" + host + ":" + port + "/");

            BitcoinJSONRPCClient bitcoinClient = new BitcoinJSONRPCClient(url);
            methode(bitcoinClient);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static void methode(BitcoinJSONRPCClient bitcoinClient){

        boolean restart = true;
        do {
            System.out.println("\nVeuillez choisir une des options suivantes :\n" +
                    "\n 1 : getBlockCount()                             1 : Retourne le nombre de blocs" +
                    "\n 2 : getBlock(int height)                        2 : Retourne les infos d'un bloc à partir d'une hauteur" +
                    "\n 3 : getBlock(String blockHash)                  3 : Retourne les infos d'un bloc à partir d'une TxId " +
                    "\n 4 : Quitter \n   ");

            switch (CheckEnter.enterNumber("Saisir un nombre entre 1 et 4 :", 1, 4)) {

                case 1:
                    System.out.println(bitcoinClient.getBlockCount());
                    break;
                case 2:
                    System.out.println(bitcoinClient.getBlock(CheckEnter.enterNumber("Saisir un bloc existant :", 0, bitcoinClient.getBlockCount()-1)));
                    break;
                case 3:
                    System.out.println(bitcoinClient.getBlock(CheckEnter.enterHexadecimal("TxID en hex : ")));
                    break;
                case 4:
                    restart = false;
                    break;
                default:
                    break;
            }
        }while(restart);

    }


}
