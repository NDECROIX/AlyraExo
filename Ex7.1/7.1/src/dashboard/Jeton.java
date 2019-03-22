package dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class Jeton {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir votre jeton");
        String jeton = sc.nextLine();

        try {

            JSONParser parser = new JSONParser();
            JSONObject json =  (JSONObject) parser.parse(display("https://data.messari.io/api/v1/assets/"+jeton+"/metrics"));

            ObjectMapper mapper = new ObjectMapper();

            JetonDashboard obj = mapper.readValue(json.toString(), JetonDashboard.class);

            System.out.println("MESSARI");

            Double prix = obj.getData().getMarketData().getPriceUsd();
            Double prixMax = Double.valueOf(obj.getData().getAllTimeHigh().getPrice());

            System.out.println("Prix : " + prix);
            System.out.println("Prix max : " + prixMax);
            System.out.println("Difference est de : " + (prixMax - prix) + " $");

            BigDecimal circu = BigDecimal.valueOf(obj.getData().getSupply().getCirculating());
            BigDecimal en2050 = BigDecimal.valueOf(obj.getData().getSupply().getY2050());

            System.out.println("Quantité en circulation : " + circu);
            System.out.println("Quantité en 2050 : " + obj.getData().getSupply().getY2050());
            System.out.println("Il en reste : " + (en2050.subtract(circu)) + " à produire");

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

            switch (responsecode){
                case 400:
                    throw new RuntimeException("invalid param value for field id : " + responsecode);
                case 401:
                    throw new RuntimeException("unauthorized (authentication) : " + responsecode);
                case 403:
                    throw new RuntimeException("forbidden (authorization) : " + responsecode);
                case 404:
                    throw new RuntimeException("User error : " + responsecode);
                case 429:
                    throw new RuntimeException("rate limited : " + responsecode);
                case 500:
                    throw new RuntimeException("An internal server error occurred : " + responsecode);
                default:
                    break;
            }

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
