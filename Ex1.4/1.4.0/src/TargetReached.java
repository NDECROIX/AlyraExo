//1.4.13

public class TargetReached {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16
    private static final String BASE_HASH = "0000000000000000000000000000000000000000000000000000000000000000"; // 32 octets

    /**
     * @param coefficient 3 octets en hex
     * @param exposant un octets en hex
     * @param hash de 32 octets en hex
     * @return vrais si le hash est inférieur à la cible sinon faut
     *
     */
    private boolean targetReached2(String coefficient, String exposant, String hash){

        StringBuilder target = new StringBuilder().append(BASE_HASH);  // on prend une base de 32 octets à 00
        int exponent = BASE_HASH.length() - getDecimal(exposant)*2; // on prend l'exposant pour insérer le coeff
        target.replace(exponent, exponent + coefficient.length(), coefficient); // on remplace les 00 par le coeff

        for (int i = 0 ; i < 64 ; i+=2){
            if (getDecimal(hash.substring(i, i+2)) < getDecimal(target.substring(i, i+2))){
                return true; // on compare les octets un par un
            }
            if (getDecimal(hash.substring(i, i+2)) > getDecimal(target.substring(i, i+2))){
                return false;
            }
        }

        return false; // si égale retourne false
    }

    /**
     * @param hex hexadéciaml en String
     * @return La valeur en int
     */
    private static int getDecimal(String hex){

        int val = 0;
        for (int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            int d = HEX_CHAR.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    public static void main(String[] args){

        TargetReached tR = new TargetReached();

        String coeff = "0696f4";
        String expo = "18";
        String hash = "000000000004864c000000000000000000000000000000000000000000000000";
        String hash2 = "00000000000000000596f5000000000000000000000000000000000000000000";

        System.out.println(tR.targetReached2(coeff, expo, hash));

        System.out.println(tR.targetReached2(coeff, expo, hash2));

        //"00000000000000000696f4000000000000000000000000000000000000000000" Target
        //"000000000004864c000000000000000000000000000000000000000000000000"
        //"00000000000000000596f4000000000000000000000000000000000000000000"
        //"00000000000000000596f5000000000000000000000000000000000000000000"

    }
}
