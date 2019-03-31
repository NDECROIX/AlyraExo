package orderBook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class OrderBook {

    private static final int BTC_SATOSHIS = 100_000_000;

    public static void main(String[] args) {

        try{
            // BITMEX
            JSONParser parser = new JSONParser();
            int nbrCall = 10;
            JSONArray json =  (JSONArray) parser.parse(display("https://www.bitmex.com/api/v1/orderBook/L2?symbol=XBT&depth="+nbrCall));

            ObjectMapper mapper = new ObjectMapper();

            Bitmex obj = mapper.readValue(json.get(nbrCall-1).toString(), Bitmex.class);

            System.out.println("BITMEX");

            double prixBitmex = obj.getPrice();
            System.out.println("Prix du Bitcoin : " + prixBitmex);
            int nombreBitmex = obj.getSize();
            System.out.println("Nombre de Satoshis disponible : " + nombreBitmex );


            // BITFINEX
            JSONParser parser1 = new JSONParser();
            JSONObject json1 = (JSONObject) parser1.parse(display("https://api.bitfinex.com/v1/book/btcusd?limit_bids="+nbrCall+"&limit_asks="+nbrCall));

            Bitfinex obj1 = mapper.readValue(json1.toString(), Bitfinex.class);

            System.out.println("\nBITFINEX");

            Double prixBitfinex = Double.valueOf(obj1.getAsks().get(0).getPrice());
            System.out.println("Prix du Bitcoin : " + prixBitfinex );

            Double nombreBitfinex = Double.valueOf(obj1.getAsks().get(0).getAmount());
            System.out.println("Nombre de Bitcoin disponible : " + nombreBitfinex + "\n");

            //Sur quelle place de marché est-il le plus intéressant d’acheter 0.0001 bitcoin?
            int satoshiDemande = 10_000;
            boolean calculFini = false;
            int b = 0;

            int satoshiBitmex;
            int satoshiBitfinex;
            double ttBitmex = 0d;
            double ttBitfinex = 0d;

            while(!calculFini){

                obj = mapper.readValue(json.get(nbrCall-(1+b)).toString(), Bitmex.class);
                if (obj.getSide() == "Sell") throw new IndexOutOfBoundsException("ERROR obj.getSide() != Sell ");

                prixBitmex = obj.getPrice();
                satoshiBitmex = obj.getSize();

                if (satoshiDemande < satoshiBitmex){
                    ttBitmex += prixBitmex / BTC_SATOSHIS * satoshiDemande;
                    calculFini = true;
                }
                else{
                    satoshiDemande = satoshiDemande - satoshiBitmex;
                    ttBitmex += prixBitmex / BTC_SATOSHIS * satoshiBitmex;
                    b++;
                }

            }

            calculFini = false;
            b = 0;
            satoshiDemande = 10_000;

            while(!calculFini){

                prixBitfinex = Double.valueOf(obj1.getAsks().get(b).getPrice());
                nombreBitfinex = Double.valueOf(obj1.getAsks().get(b).getAmount());
                satoshiBitfinex = (int) (nombreBitfinex * BTC_SATOSHIS);

                if (satoshiDemande < satoshiBitfinex){
                    ttBitfinex += prixBitfinex / BTC_SATOSHIS * satoshiDemande;
                    calculFini = true;
                }
                else{
                    satoshiDemande = satoshiDemande - satoshiBitfinex;
                    ttBitfinex += prixBitfinex / BTC_SATOSHIS * satoshiBitfinex;
                    b++;
                }
            }

            System.out.print("Sur quelle place de marché est-il le plus intéressant d’acheter 0.0001 bitcoin? ");
            System.out.println((ttBitmex < ttBitfinex)? "BITMEX" : "BITFINEX");

            //Sur quelle place de marché est-il le plus intéressant d’acheter 20 dollars de bitcoins?
            double prixDemande = 20;
            calculFini = false;
            b = 0;
            ttBitmex = 0d;
            ttBitfinex = 0d;
            double prix;

            while(!calculFini){

                obj = mapper.readValue(json.get(nbrCall-(1+b)).toString(), Bitmex.class);
                prixBitmex = obj.getPrice();
                satoshiBitmex = obj.getSize();

                prix = prixBitmex / BTC_SATOSHIS * satoshiBitmex;

                if (prixDemande < prix){
                    ttBitmex += (int) (prixDemande / (prixBitmex / BTC_SATOSHIS));
                    calculFini = true;
                }
                else{
                    prixDemande = prixDemande - prix;
                    ttBitmex += satoshiBitmex;
                    b++;
                }

            }

            prixDemande = 20;
            calculFini = false;
            b = 0;

            while(!calculFini){

                prixBitfinex = Double.valueOf(obj1.getAsks().get(b).getPrice());
                nombreBitfinex = Double.valueOf(obj1.getAsks().get(b).getAmount());
                satoshiBitfinex = (int) (nombreBitfinex * BTC_SATOSHIS);

                prix = prixBitfinex / BTC_SATOSHIS * satoshiBitfinex;

                if (prixDemande < prix){
                    ttBitfinex += (int) (prixDemande / (prixBitfinex / BTC_SATOSHIS));
                    calculFini = true;
                }
                else{
                    prixDemande = prixDemande - prix;
                    ttBitfinex += satoshiBitfinex;
                    b++;
                }
            }

            System.out.print("\nSur quelle place de marché est-il le plus intéressant d’acheter 20 dollars de bitcoins? ");
            System.out.println((ttBitmex > ttBitfinex)? "BITMEX" : "BITFINEX");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String display(String urlApi) {


        try {

            URL url = new URL(urlApi);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String inline = "";
            int responsecode = conn.getResponseCode();

            if (responsecode != 200)throw new RuntimeException("HttpResponseCode : " + responsecode);
            else {

                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()){
                    inline += sc.nextLine();
                }
                sc.close();
            }

            return inline;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
