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

    public static void main(String[] args) {

        try{
            // BITMEX
            JSONParser parser = new JSONParser();
            JSONArray json =  (JSONArray) parser.parse(display("https://www.bitmex.com/api/v1/orderBook/L2?symbol=XBT&depth=10"));

            ObjectMapper mapper = new ObjectMapper();

            Bitmex obj = mapper.readValue(json.get(9).toString(), Bitmex.class);

            System.out.println("BITMEX");

            Double prixB = obj.getPrice();
            System.out.println("Prix du Bitcoin : " + prixB);
            Double nombreB = obj.getSize() *  0.00000001;
            System.out.println("Nombre de Bitcoin disponible : " + nombreB );


            // BITFINEX
            JSONParser parser1 = new JSONParser();
            JSONObject json1 = (JSONObject) parser1.parse(display("https://api.bitfinex.com/v1/book/btcusd?limit_bids=10&limit_asks=10"));

            Bitfinex obj1 = mapper.readValue(json1.toString(), Bitfinex.class);

            System.out.println("\nBITFINEX");

            Double prixB1 = Double.valueOf(obj1.getAsks().get(0).getPrice());
            System.out.println("Prix du Bitcoin : " + prixB1 );
            Double nombreB1 = Double.valueOf(obj1.getAsks().get(0).getAmount());
            System.out.println("Nombre de Bitcoin disponible : " + nombreB1);

            //Sur quelle place de marché est-il le plus intéressant d’acheter 0.0001 bitcoin?
            int b = 9;
            Double nombre = 0.0001;
            Double calculTotalB = 0d;
            Double calculTotalB1 = 0d;
            if (nombreB != 0 && nombreB1 != 0){
                while(nombreB < nombre && b < 10){ // On boucle jusqu'à atteindre 0.0001 bitcoin sur BITMEX
                    obj = mapper.readValue(json.get(b).toString(), Bitmex.class);
                    prixB = obj.getPrice();
                    nombreB = obj.getSize() *  0.00000001;
                    if (nombre > nombreB){
                        nombre -= nombreB;
                        calculTotalB += nombreB * prixB;
                    }
                    b--;
                }

                calculTotalB += nombre * prixB;

                nombre = 0.0001;

                b = 1;
                while(nombreB1 < nombre && b < 10){  // On boucle jusqu'à atteindre 0.0001 bitcoin sur BITFINEX
                    prixB1 = Double.valueOf(obj1.getBids().get(b).getPrice());
                    nombreB1 = Double.valueOf(obj1.getBids().get(b).getAmount());
                    if (nombre > nombreB1){
                        nombre -= nombreB1;
                        calculTotalB1 += nombreB1 * prixB1;
                    }
                    b++;
                }
                calculTotalB1 += nombre * prixB1;
            }

            System.out.println((calculTotalB < calculTotalB1)? "\nBITMEX est plus intéressant" : "\nBITFINEX est plus intéressant"); // On compare le prix

            //Sur quelle place de marché est-il le plus intéressant d’acheter 20 dollars de bitcoins?
            b = 1;
            calculTotalB = 0d;
            calculTotalB1 = 0d;
            int prix = 20;

            obj = mapper.readValue(json.get(0).toString(), Bitmex.class);
            prixB = obj.getPrice();
            nombreB = obj.getSize() *  0.00000001;

            if (nombreB != 0 && nombreB1 != 0){
                while(nombreB * prixB < prix && b < 10){ // On boucle jusqu'à atteindre 20 $ sur BITMEX

                    obj = mapper.readValue(json.get(b).toString(), Bitmex.class);
                    prixB = obj.getPrice();
                    nombreB = obj.getSize() *  0.00000001;

                    if (nombreB * prixB < prix){

                        prix -= nombreB * prixB;
                        calculTotalB += nombreB;
                    }

                    b++;
                }
                calculTotalB += prix * nombreB / prixB ;

                b = 1;

                prixB1 = Double.valueOf(obj1.getBids().get(0).getPrice());
                nombreB1 = Double.valueOf(obj1.getBids().get(0).getAmount());

                while(nombreB1 * prixB1 < prix && b < 10){ // On boucle jusqu'à atteindre 20 $ sur BITFINEX

                    prixB1 = Double.valueOf(obj1.getBids().get(b).getPrice());
                    nombreB1 = Double.valueOf(obj1.getBids().get(b).getAmount());

                    if (nombreB1 * prixB1 < prix){

                        prix -= nombreB1 * prixB1;
                        calculTotalB1 += nombreB1;
                    }
                    b++;
                }
                calculTotalB1 += prix * nombreB / prixB ;
            }

            System.out.println((calculTotalB > calculTotalB1)? "\nBITMEX est plus intéressant" : "\nBITFINEX est plus intéressant"); // On compare le nombre de bitcoin



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
