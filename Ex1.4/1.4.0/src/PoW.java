
import static java.lang.System.currentTimeMillis;

public class PoW {

        private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // 26^L+(n-L)

        private String chaineAlea(int sizeString){

                StringBuilder chaineAlea = new StringBuilder();
                int t = CHARS.length();
                for (int i = 0; i < sizeString ; i++){

                    int c = (int)Math.floor(Math.random() * t );

                    chaineAlea.append(CHARS.charAt(c));
                }

            return chaineAlea.toString();

        }

        private void rechercheDebut(String debut, int sizeWord){

            boolean find = false;
            int cpt = 0;
            while(!find){
                cpt++;
                find = chaineAlea(sizeWord).startsWith(debut);

            }

            System.out.println("FIN " + cpt + " Tour de boucle");

        }


        public static void main(String[] args){

            PoW poW = new PoW();

            poW.rechercheDebut("B", 8);
            poW.rechercheDebut("BI", 8);
            poW.rechercheDebut("BIT", 8);
            poW.rechercheDebut("BITC", 8);
            poW.rechercheDebut("BITCO", 8);
            poW.rechercheDebut("BITCOI", 8);
            poW.rechercheDebut("BITCOIN", 8);




        }


}
