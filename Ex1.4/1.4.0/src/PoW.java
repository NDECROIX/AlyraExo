

/**
 *         Mesurer la durée de chaque recherche de solution en fonction de la longueur de la chaîne et de n
 *         P(A) =   a^(n-L)/a^(n) = 1/a^L = a^(-L) pour a le nombre de lettre dans l'alphabet, n la longueur de la chaîne aléatoire et L la longueuer de la chaine à trouver
 *
 *         D =  T(a^(n)/a^(n-L)) = Ta^L // D pour durée
 *         D =  Ta^(L)  pour T le temps de chaque passage dans la boucle while de rechercheDebut()
 *         D =  a^(L)/H  pour H le nombre de passage dans la fonction chaineAlea par seconde
 *
 *         pour poW.rechercheDebut("BITCOIN", 8); a = 26 et T = 2*10^(−7)sec "0.0000002"
 *         D =  (2*10^(−7))*26^(7)  = 1606,3620352sec
 *
 *         pour poW.rechercheDebut("BITCOIN", 8); a = 26 et H = 5 000 000
 *         D =  26^(7)/5000000  = 1606,3620352sec
 *
 */
public class PoW {

        private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final int CHARS_SIZE = CHARS.length();

        private String chaineAlea(int sizeString){

                StringBuilder chaineAlea = new StringBuilder();

                for (int i = 0; i < CHARS_SIZE ; i++){

                    int c = (int)Math.floor(Math.random() * CHARS_SIZE);

                    chaineAlea.append(CHARS.charAt(c));
                }

            return chaineAlea.toString();

        }

        private void rechercheDebut(String debut, int sizeWord){

            boolean find = false;
            while(!find){
                find = chaineAlea(sizeWord).startsWith(debut);
            }

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
            poW.rechercheDebut("BITCOINS", 8);




        }


}
