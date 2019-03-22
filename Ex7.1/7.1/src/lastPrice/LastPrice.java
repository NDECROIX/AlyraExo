package lastPrice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class LastPrice {

    public static void main(String[] args){

        try {

            JSONParser parser = new JSONParser();
            JSONObject json =  (JSONObject) parser.parse(display("https://api.bitfinex.com/v1/pubticker/btceur"));

            ObjectMapper mapper = new ObjectMapper();

            BitfinexLastPrice obj = mapper.readValue(json.toString(), BitfinexLastPrice.class);

            System.out.println("BITFINEX");

            System.out.println("Prix du dernier échange sur bitfinex : " + obj.getLastPrice() + " € ");

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
