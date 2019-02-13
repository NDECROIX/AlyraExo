public class BitsToTarget {

    private static final String BASE_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

    public static void bitsToTarget(){

        String bits = CheckEnter.enterHexadecimal("Bits : ");
        String coefficient = bits.substring(2);
        int exponentHex = Integer.parseInt(bits.substring(0,2), 16);

        StringBuilder target = new StringBuilder().append(BASE_HASH);  // on prend une base de 32 octets à 00
        int exponent = BASE_HASH.length() - exponentHex*2; // on prend l'exposant pour insérer le coeff
        target.replace(exponent, exponent + coefficient.length(), coefficient); // on remplace les 00 par le coeff

        System.out.println(bits + " = " + target.toString());

    }

}
