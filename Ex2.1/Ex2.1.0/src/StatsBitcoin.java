import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class StatsBitcoin {

    private static final Timestamp BLOCK1 = Timestamp.valueOf("2009-01-09 02:54:25");
    private static final BigDecimal TEN_MINUTES = BigDecimal.valueOf(600);
    private static final BigDecimal MILLIS = BigDecimal.valueOf(1000);


    public void statsBitoin(String date){

        RewardByHeight rBH = new RewardByHeight();
        BitcoinsAtThisHeight bTH = new BitcoinsAtThisHeight();

        Timestamp ts = Timestamp.valueOf(date) ;
        int blockBuild;

        BigDecimal dater = new BigDecimal(ts.getTime() - BLOCK1.getTime()).divide(MILLIS);

        blockBuild = dater.divide(TEN_MINUTES).intValue();

        System.out.println("\nIl y a : " + blockBuild + " Block");

        System.out.println("La récompense est de : " + rBH.rewardByHeight(blockBuild) + " Bitcoins");

        System.out.println("Il y a : " + bTH.bitcoinsNow(blockBuild) +" Bitcoins en circulation");


    }


    public static void main(String[] args){

        StatsBitcoin sB = new StatsBitcoin();

        sB.statsBitoin("2018-01-09 02:54:25");
        sB.statsBitoin("2100-01-01 00:00:01");


        BigDecimal dater = new BigDecimal(4294967295L*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.FRANCE);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(dater);
        System.out.println(formattedDate);



    }

}
