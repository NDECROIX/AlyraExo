import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LittleEndianToHex {

    public static void littleEndianToHex(){

        String hex = CheckEnter.enterHexadecimal("Little endian : ");

        String sb = convLittEndianToHex(hex);

        System.out.println("La valeur Hexadecimal du little endian : " + hex + " est : " + sb);

    }

    public static String convLittEndianToHex(String hex){
        List<String> hexDecimal = new ArrayList<>();

        if (hex.length()%2 != 0) hex = "0" + hex;

        for (int i = 0; i < hex.length(); i+=2){
            hexDecimal.add(hex.substring(i, i+2));
        }

        Collections.reverse(hexDecimal);

        StringBuilder sb = new StringBuilder();

        for (String s : hexDecimal) sb.append(s).append(' ');

        return sb.toString();
    }

}
