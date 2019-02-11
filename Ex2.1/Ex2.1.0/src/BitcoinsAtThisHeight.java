
import java.text.DecimalFormat;

public class BitcoinsAtThisHeight {


    private static final int CHANGE_REWARD = 210000;

    private static final float FIRST_REWARD = 50;



    public String bitcoinsNow(float blockHeight){

        float m = blockHeight / CHANGE_REWARD;
        float reward = FIRST_REWARD;
        float result = 0;

        while(m>0){

            if (m > 1)
                result += reward * CHANGE_REWARD;
            else
                result += m * reward * CHANGE_REWARD;

            reward /= 2;
            m--;

        }

        DecimalFormat df = new DecimalFormat("##.##");
        return df.format(result);

    }



    public static void main(String[] args){

        BitcoinsAtThisHeight bt = new BitcoinsAtThisHeight();

        System.out.println(bt.bitcoinsNow(205200));
        System.out.println(bt.bitcoinsNow(483408));
        System.out.println(bt.bitcoinsNow(156096));
        System.out.println(bt.bitcoinsNow(265248));

    }

}
