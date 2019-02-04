// Entrainement A B C
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vigenere {

    private String vigenere(String mc, String txt, boolean chiffre){

        char[] cL = txt.toCharArray();
        String txtChiffre = "";
        char[] mcL = mc.toCharArray();
        int nbrC = mcL.length;

        for (int i = 0, b = 0; i < cL.length ; i++, b++){

            if (b == nbrC)  b = 0;

            int l;

            if (chiffre)  l = ((int)cL[i]) + ((int) mcL[b]) ; // on chiffre
            else l = ((int)cL[i]) - ((int) mcL[b]) ; // on dèchiffre

            txtChiffre += (char) l; // on l'ajoute à la chaîne txtChiffre

        }

        return txtChiffre;

    }

    private List regroupe(String txt, int nbr){

        String[] tableGroup = new String[nbr];
        for (int i = 0; i < nbr ; i ++) tableGroup[i] = "";

        char[] text = txt.toCharArray();

        for (int i = 0, b = 0 ; i < text.length; i++){

            if (b == nbr) b = 0;
            if (text[i] != ' '){
                tableGroup[b] += text[i];
                b++;
            }

        }

        return new ArrayList(Arrays.asList(tableGroup).subList(0, nbr));

    }


    public static void main(String[] args){

        Vigenere v = new Vigenere();

        String txt = "You shall not pass ! ";
        String mc = "Gandalf";

        System.out.println(txt);

        txt = v.vigenere(mc, txt, true);
        System.out.println(txt);

        txt = v.vigenere(mc, txt, false);
        System.out.println(txt);

        System.out.println(v.regroupe(txt, 4));
        System.out.println(v.regroupe(txt, 2));
        System.out.println(v.regroupe(txt, 8));



    }




}
