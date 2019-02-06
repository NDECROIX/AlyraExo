package bitcoin;

import java.math.BigInteger;

public class Base58 {

    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"; // base 58
    private static final BigInteger BIG_INTEGER_0 = BigInteger.ZERO;
    private static final BigInteger BIG_INTEGER_58 = BigInteger.valueOf(58);


    /**
     * @param hash hash en hexadecimal
     * @return hash en base 58
     */
    public String convertToBase58(String hash) {
        int base = 16;
        BigInteger x;

        if (hash.substring(0, 2).equals("0x")) {
            x = new BigInteger(hash.substring(2), 16);
        } else {
            x = new BigInteger(hash, base);   // convertir Hexadecimal en Decimale
        }

        StringBuilder sb = new StringBuilder();


            while (x.compareTo(BIG_INTEGER_0) > 0) { // compare x à 0
            int r = x.mod(BIG_INTEGER_58).intValue(); // x modulo 58
            sb.append(ALPHABET.charAt(r));
            x = x.divide(BIG_INTEGER_58); // on divise x par 58
        }

        sb.append(ALPHABET.charAt(0));


        return sb.reverse().toString();
    }
}