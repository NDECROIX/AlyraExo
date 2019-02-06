import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conversion {

    private static final char[] HEX_CHAR = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; // Base 16

    /**
     * @param decimal prend un int
         *   Affiche le little et big endian d'un nombre décimale
     */
    public void intToHex(int decimal){


        List<StringBuilder> hexDecimal = new ArrayList<>();

        int dec = decimal;
        int positionHex;
        int lastPosition = -1;
        hexDecimal.add(new StringBuilder().append("0").append("x"));

        while(decimal > 0){

            positionHex = decimal%16; // recupère la position du char dans en base 16

            if (lastPosition > 0){ // prend les caractères hex par deux
                hexDecimal.add(new StringBuilder().append(HEX_CHAR[positionHex]).append(HEX_CHAR[lastPosition]));
                lastPosition = -1;

            }else lastPosition = positionHex;

            decimal = decimal / 16; // on divise le nombre à convertir par 16 pour recommencer l'opération

        }

        if (lastPosition != -1){ // l'on prend le dernier caractère
            hexDecimal.add(new StringBuilder().append(HEX_CHAR[0]).append(HEX_CHAR[lastPosition]));
        }

        //Affichage du resultat en big et little endian
        System.out.println("Conversion de : " + dec);

        for (StringBuilder s : hexDecimal){
            System.out.print(s + " ");
        }
        System.out.println(" (little endian)");
        Collections.reverse(hexDecimal.subList(1, hexDecimal.size())); // retourne la liste pour passer en big endian

        for (StringBuilder s : hexDecimal){
            System.out.print(s + " ");
        }
        System.out.println(" (big endian)");
    }

    public static void main(String[] args){

        Conversion conversion = new Conversion();
        conversion.intToHex(466321);

    }



}
