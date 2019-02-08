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

        for (int i = 0 ; i < 64 ; i+=2) if (getDecimal(hash.substring(i, i+2)) > getDecimal(target.substring(i, i+2))) return false; // on compare les octets un par un

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
        String hash = "000000000004864c000000000000000000000000000000000000000000000000";
        String hash2 = "00000000000000000596f4000000000000000000000000000000000000000000";

        System.out.println(tR.targetReached2(coeff, expo, hash));

        System.out.println(tR.targetReached2(coeff, expo, hash2));

        //"00000000000000000696f4000000000000000000000000000000000000000000" Target
        //"000000000004864c000000000000000000000000000000000000000000000000"
        //"00000000000000000596f4000000000000000000000000000000000000000000"

    }

    // Base avant modification
    /*
     * @param coefficient 3 octets en hex
     * @param exposant un octets en hex
     * @param hash de 32 octets en hex
     * @return vrais si le hash est inférieur à la cible sinon faut
     * compare la taille du hash sans octet et de l'exposant
     * construit la target dans une liste
     *//*
    private boolean targetReached(String coefficient, String exposant, String hash){

        List<String> target = new ArrayList<>();
        List<String> hashTest = new ArrayList<>();


        int exponent = getDecimal(exposant); // converti l'exposant en decimale

        for (int i = 0; i < hash.length(); i+=2){ // on fait du hash une liste d'octet
            hashTest.add(hash.substring(i, i+2));
        }

        while(hashTest.size() != 0 && hashTest.get(0).startsWith("00")){ // retire les premiers octets à zéro
            hashTest.remove(0);
        }

        if (hashTest.size() < exponent) return true; // si la taille du hash est inférieur à l'exposant return vrai
        else if (hashTest.size() > exponent) return false; // si la taille du hash est supérieur à l'exposant retourne faut

        exponent -= (coefficient.length()/2); // l'on supprime les octets nécessaire pour le coefficient
        for (int i = 0; i < coefficient.length(); i+=2){ // l'on construit la target
            target.add(coefficient.substring(i, i+2));
        }
        for (int i = 0; i < exponent; i++) target.add("00");


        for (int i = 0; i < target.size(); i++){
            if (getDecimal(hashTest.get(i)) < getDecimal(target.get(i))) return true;  // compare les octets

        }

        return false;
    }*/


}
