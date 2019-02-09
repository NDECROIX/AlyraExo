

public class ReadjustedBlock {

    private static final int REASSESSED = 2016;

    /**
     * @param blockHeight hauteur du block en int
     * @return vrais s’il s’agit d’un bloc pendant lequel la difficulté est réajustée ou non.
     * l'on divise la hauteur du block par 2016 si le reste égale 0 il en fait parti
     */
    private boolean readjustedBlock(int blockHeight){

        return (blockHeight % REASSESSED) == 0;

    }

    public static void main(String[] args){

        ReadjustedBlock rB = new ReadjustedBlock();

        System.out.println(rB.readjustedBlock(556416));
        System.out.println(rB.readjustedBlock(556415));
        System.out.println(rB.readjustedBlock(556416 + 2016));

    }

}
