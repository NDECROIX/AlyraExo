
import java.util.ArrayList;
import java.util.List;

public class TargetReached {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16
    private static final String BASE_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

    private boolean targetReached(String coefficient, String exposant, String hash){

        List<String> target = new ArrayList<>();
        List<String> hashTest = new ArrayList<>();


        int exponent = getDecimal(exposant); // converti l'exposnat en decimale

        for (int i = 0; i < hash.length(); i+=2){
            hashTest.add(hash.substring(i, i+2));
        }

        while(hashTest.size() != 0 && hashTest.get(0).startsWith("00")){ // retire les premiers octets à zéro
            hashTest.remove(0);
        }

        if (hashTest.size() < exponent) return true; // si la taille du hash est inférieur à l'exposant return vrai
        else if (hashTest.size() > exponent) return false; // si la taille du hash est supérieur à l'exposant return faut

        exponent -= (coefficient.length()/2); // l'on supprime les octets nécessaire pour le coefficient
        for (int i = 0; i < coefficient.length(); i+=2){ // l'on construit la target
            target.add(coefficient.substring(i, i+2));
        }
        for (int i = 0; i < exponent; i++) target.add("00");


        for (int i = 0; i < target.size(); i++){
            if (getDecimal(hashTest.get(i)) < getDecimal(target.get(i))){
                return true;
            }
        }

        System.out.println(target);
        System.out.println(hashTest);

        return false;
    }

    private boolean targetReached2(String coefficient, String exposant, String hash){

        StringBuilder sb = new StringBuilder().append(BASE_HASH);
        int exponent = BASE_HASH.length() - getDecimal(exposant)*2;

        sb.replace(exponent, exponent + coefficient.length(), coefficient);


        System.out.println(sb.toString());

        return true;
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
        String hash = "00000000000000000696f4000000000000000000000000000000000000000000";
        System.out.println(tR.targetReached2(coeff, expo, hash));
        //"00000000000000000696f4000000000000000000000000000000000000000000";
        //"00000000000000000696f4000000000000000000000000000000000000000000"
        //"000000000004864c000000000000000000000000000000000000000000000000"

    }


}
