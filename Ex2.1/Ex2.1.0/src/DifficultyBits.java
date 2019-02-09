import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;

public class DifficultyBits {

    private static final BigDecimal TARGET_ORIGIN = new BigDecimal("26959535291011309493156476344723991336010898738574164086137773096960");

    /**
     * @param lastTarget bits de la cible
     * Calcul la difficulté en divisant la cible original par la cible en paramètre
     */
    private void difficulty(String bits){


        StringBuilder targetHex = new StringBuilder();
        if (bits.startsWith("0x")) bits = bits.substring(2);
        int exponent = Integer.parseInt(bits.substring(0, 2), 16)*2; // on prend l'exposant

        targetHex.append(bits.substring(2)); //on insère le coeff

        while (targetHex.length() < exponent) targetHex.append("00"); // on ajoute des octets à 0 pour arriver à une taille égale à l'exposant

        BigDecimal target = new BigDecimal(new BigInteger(targetHex.toString(), 16)); // on converti en décimale

        DecimalFormat df = new DecimalFormat("##.00"); //le format de la précision

        System.out.println(df.format(TARGET_ORIGIN.divide(target,  MathContext.DECIMAL128))); // on divise la première cible existante par la cible en paramètre

    }


    public static void main(String[] args){

        DifficultyBits difficulty = new DifficultyBits();
        
        String bits = "0x1c0ae493";

        difficulty.difficulty(bits);

    }
}
