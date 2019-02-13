import java.math.BigInteger;

public class HexToDecimal {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16

    public static void hexToDecimal(){

        String hexadecimal = CheckEnter.enterHexadecimal("Nombre Hexadécimal : ");
        BigInteger decimal = new BigInteger(hexadecimal, 16);
        System.out.println("La valeur décimale de : " + hexadecimal + " est : " + decimal);

    }

}
