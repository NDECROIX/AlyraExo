import java.text.DecimalFormat;

public class RewardByHeight {

    private static final int CHANGE_REWARD = 210000; // nombre de bloc avant de réduire la récompense
    private static final int REWARD_DIVISOR = 2; // nombre par lequel l'on divise la récompense tous les 210000 blocs
    private static final float FIRST_REWARD = 50; // Récompense de base pour la génération d'un bloc


    /**
     * @param blockHeight Hauteur du bloc
     * @return la récompense associée à ce bloc
     */
    public String rewardByHeight(int blockHeight){


        int x = (blockHeight / CHANGE_REWARD); // on divise le block par 210000

        int level = (int) Math.pow(REWARD_DIVISOR, x); // l'on fait 2 puissance x

        DecimalFormat df = new DecimalFormat("#0.#####");
        return df.format((FIRST_REWARD / level)); //  récompense de base divisé par level
    }

    public static void main(String[] args){

        RewardByHeight rBH = new RewardByHeight();


        System.out.println(rBH.rewardByHeight(0));
        System.out.println(rBH.rewardByHeight(210000));
        System.out.println(rBH.rewardByHeight(420000));
        System.out.println(rBH.rewardByHeight(420001));
        System.out.println(rBH.rewardByHeight(630000));
        System.out.println(rBH.rewardByHeight(840000));
        System.out.println(rBH.rewardByHeight(1050000));
        System.out.println(rBH.rewardByHeight(1260000));

    }

}
