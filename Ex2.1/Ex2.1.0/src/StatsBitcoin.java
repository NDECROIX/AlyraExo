import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class StatsBitcoin {

    private static final Timestamp BLOCK_GENESIS = Timestamp.valueOf("2009-01-03 18:15:05");
    private static final BigDecimal TEN_MINUTES = BigDecimal.valueOf(566.823); // 2019-02-11 16:49:25 - 2009-01-03 18:15:05 / 562604 bloc à la première date
    private static final BigDecimal MILLIS = BigDecimal.valueOf(1000);


    public void statsBitoin(String date){

        RewardByHeight rBH = new RewardByHeight();
        BitcoinsAtThisHeight bTH = new BitcoinsAtThisHeight();

        Timestamp ts = Timestamp.valueOf(date) ;
        BigDecimal blockBuild;


        blockBuild = new BigDecimal(ts.getTime() - BLOCK_GENESIS.getTime()) // date en paramètre - date du premier bloc
                .divide(MILLIS, RoundingMode.HALF_UP) // conversion en seconde
                .divide(TEN_MINUTES, RoundingMode.DOWN); // 1 Block toutes les dix minutes environ

        System.out.println("\nDate : " + date);

        System.out.println("Nombre de blocs : " + blockBuild );

        // https://github.com/NDECROIX/AlyraExo/blob/master/Ex2.1/Ex2.1.0/src/RewardByHeight.java
        System.out.println("Récompense de : " + rBH.rewardByHeight(blockBuild.intValue()) + " Bitcoins");

        // https://github.com/NDECROIX/AlyraExo/blob/master/Ex2.1/Ex2.1.0/src/BitcoinsAtThisHeight.java
        System.out.println("Nombre en circulation : " + bTH.bitcoinsAtThisHeight(blockBuild.intValue()) +" Bitcoins");


    }


    public static void main(String[] args){

        StatsBitcoin sB = new StatsBitcoin();

        sB.statsBitoin("2019-02-11 16:49:25");
        sB.statsBitoin("2100-01-01 00:00:00"); // 20999998 - 21000000


    }

}
