import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

public class Difficulty {

    private static final BigDecimal TARGET_ORIGIN = new BigDecimal("26959535291011309493156476344723991336010898738574164086137773096960");


    /**
     * @param lastTarget
     * Calcul la difficulté en divisant la cible original par la cible en paramètre
     */
    private void difficulty(String pTarget){

        BigDecimal target = new BigDecimal(pTarget);
        DecimalFormat df = new DecimalFormat("##.00"); //le format de la précision

        System.out.println(df.format(TARGET_ORIGIN.divide(target,  MathContext.DECIMAL128))); // on divise la première cible existante par la cible en paramètre

    }


    public static void main(String[] args){

        Difficulty difficulty = new Difficulty();

        String target = "1147152896345386682952518188670047452875537662186691235300769792000";

        difficulty.difficulty(target);

    }
}
