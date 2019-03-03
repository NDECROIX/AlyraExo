import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VarIntToDecimal {


    public static void VarIntToDecimal(){

        String hex = CheckEnter.enterHexadecimal("VarInt : ");
        String varInt = "0x" + hex;
        List<String> hexDecimal = new ArrayList<>();
        int taille = 0;

        switch (hex.substring(0,2)){
            case "fd":
                taille = 2;
                break;
            case "fe":
                taille = 4;
                break;
            case "ff":
                taille = 8;
            default:
                break;
        }

        hex = hex.substring(2);

        if (hex.length()%2 != 0) hex = "0" + hex;

        for (int i = 0; i < hex.length(); i+=2){
            hexDecimal.add(hex.substring(i, i+2));
        }

        Collections.reverse(hexDecimal);

        taille -= hex.length();
        while(taille > 0 ){
            hexDecimal.add("00");
            taille-=2;
        }

        //ff f7 0e 6d 3b 997002999
        StringBuilder sb = new StringBuilder();

        for (String s : hexDecimal) sb.append(s);

        BigInteger decimal = new BigInteger(sb.toString(), 16);

        System.out.println("La valeur decimal du VarInt : " + varInt + ", est : " + decimal);

    }

    public static String varInt(String varInt){

        int taille = 1;
        System.out.println(varInt);
        System.out.println(varInt.substring(0,1));

        switch (varInt.substring(0,1)){

            case "fd":
                taille = 2;
                break;
            case "fe":
                taille = 4;
                break;
            case "ff":
                taille = 8;
            default:
                break;
        }

        String varIntHex;

        if (taille == 1) varIntHex = varInt.substring(0, 2);
        else varIntHex = varInt.substring(2, 2+taille*2);

        int result = new BigInteger(varIntHex, 16).intValueExact();

        return String.valueOf(taille + result);

    }

}
