
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConversionVarInt {

    private static final char[] HEX_CHAR = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; // Base 16

    /**
     * @param decimal prend un int
     *   Affiche le little et big endian d'un nombre décimale
     */
    public void intToHex(long decimal){


        List<StringBuilder> hexDecimal = new ArrayList<>();

        long dec = decimal;
        int positionHex;
        int lastPosition = -1;

        while(decimal > 0){

            positionHex = Math.toIntExact(decimal % 16); // recupère la position du char dans en base 16

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

        Collections.reverse(hexDecimal); // retourne la liste pour passer en big endian

        StringBuilder sbBE = new StringBuilder();
        sbBE.append("0x ");
        for (StringBuilder s : hexDecimal){
            sbBE.append(s + " ");
        }
        System.out.println(sbBE.toString() + " (big endian)"); // affichage en Big endian



        Collections.reverse(hexDecimal);
        StringBuilder sbLE = new StringBuilder();

        sbLE.append("0x ");
        for (StringBuilder s : hexDecimal){
            sbLE.append(s + " ");
        }
        System.out.println(sbLE.toString() + " (little endian)"); // affichage en little endian


        if (dec < 253){
            System.out.println(sbLE.toString() + " (notation variable)");
        }
        else{

            int sizeHexDe = hexDecimal.size(); // taille d'octets
            String sizeVarInt; // notation nbr octet

            if (sizeHexDe%2 != 0 ){ // si impaire add 1 octet
                sbLE.append("00 ");
                sizeHexDe++;
            }
            if (sizeHexDe == 6 ){ // si égale 6 add 2 octets
                sbLE.append("00 00 ");
                sizeHexDe += 2;
            }

            if (sizeHexDe == 2){ // notation FD pour deux octets
                sizeVarInt = "fd ";
            }
            else if (sizeHexDe == 4){ // notation FE pour quatre octets
                sizeVarInt = "fe ";
            }
            else sizeVarInt = "ff "; // notation FF pour 8 octets

            sbLE.insert(3, sizeVarInt);
            System.out.println(sbLE.toString() + " (notation variable)");

        }
    }

    public static void main(String[] args){


        ConversionVarInt conversion = new ConversionVarInt();
        conversion.intToHex(466321);
        conversion.intToHex(252);
        conversion.intToHex(997002999);
        conversion.intToHex(11111111111111L);

    }




}
