import java.text.DecimalFormat;

public class RewardByHeight {

    private static final int CHANGE_REWARD = 210000;
    private static final int REWARD_DIVISOR = 2;
    private static final float FIRST_REWARD = 50;


    public String rewardByHeight(int blockHeight){

        int x = (blockHeight / CHANGE_REWARD) + 50;

        int level = (int) Math.pow(REWARD_DIVISOR, x);

        DecimalFormat df = new DecimalFormat("##.00");
        return df.format((FIRST_REWARD / level));
    }

    public static void main(String[] args){

        RewardByHeight rBH = new RewardByHeight();


        System.out.println(rBH.rewardByHeight(1));
        System.out.println(rBH.rewardByHeight(210000));
        System.out.println(rBH.rewardByHeight(420000));
        System.out.println(rBH.rewardByHeight(630000));
        System.out.println(rBH.rewardByHeight(840000));
        System.out.println(rBH.rewardByHeight(1050000));
        System.out.println(rBH.rewardByHeight(1260000));

    }

}
