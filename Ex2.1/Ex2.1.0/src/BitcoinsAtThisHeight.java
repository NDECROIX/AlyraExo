
import java.text.DecimalFormat;

public class BitcoinsAtThisHeight {


    private static final int CHANGE_REWARD = 210000;

    private static final float FIRST_REWARD = 50;


    /**
     * @param blockHeight
     * @return le nombre de bitcoins à une hauteur de bloc
     */
    public String bitcoinsAtThisHeight(float blockHeight){

        float m = blockHeight / CHANGE_REWARD; // définit un nombre de paliers
        float reward = FIRST_REWARD;
        float result = 0;

        while(m>0){ // à chaque palier l'on multiple la récompense par 210000

            if (m > 1)
                result += reward * CHANGE_REWARD;
            else
                result += m * CHANGE_REWARD * reward ; // si le palier est inférieur à 1 on le multiplie par 210000 pour prendre seulement les blocs restant

            reward /= 2; // récompense divisé par deux à chaque palier
            m--;

        }

        DecimalFormat df = new DecimalFormat("##.##");
        return df.format(result);

    }



    public static void main(String[] args){

        BitcoinsAtThisHeight bt = new BitcoinsAtThisHeight();

        System.out.println(bt.bitcoinsAtThisHeight(547488));
        System.out.println(bt.bitcoinsAtThisHeight(1333548));
        System.out.println(bt.bitcoinsAtThisHeight(346752));
        System.out.println(bt.bitcoinsAtThisHeight(59328));

    }

}
