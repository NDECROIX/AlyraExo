import java.math.BigInteger;

public class DecimalToHex {

    public static void decimalToHex(){

        String decimal = CheckEnter.enterDecimal();
        String hexadecimal = new BigInteger(decimal).toString(16);
        System.out.println("La valeur Hexadecimal de : " + decimal + " est : " + hexadecimal );

    }

}
