
import java.util.ArrayList;
import java.util.List;

public class TargetReached {

    private static final String HEX_CHAR = "0123456789abcdef"; // Base 16

    public boolean targetReached(String coefficient, String exposant, String hash){

        List<String> target2 = new ArrayList<>();
        StringBuilder sb = new StringBuilder();



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

        tR.targetReached("18", "699211", "");

    }


}
