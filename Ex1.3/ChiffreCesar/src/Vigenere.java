// Entrainement A B C
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vigenere {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz"; // on ajoute ici les caratères souhaités

    /**
     * @param mc mot-clé
     * @param txt texte à chiffrer ou déchiffrer
     * @param chiffre boolean qui prend True pour chiffrer le texte et false pour déchiffrer
     * @return return le texte chiffré ou déchiffré
     */
    private String vigenere(String mc, String txt, boolean chiffre){

        char[] textToCharArray = txt.toLowerCase().toCharArray();  // tableau des caractères du text
        char[] keyWordToCharArray = mc.toLowerCase().toCharArray(); // mot-clé en tableau de char
        char[] alphabetToCharArray = ALPHABET.toCharArray(); // alphabet

        int alphabetLength = alphabetToCharArray.length; // taille de l'alphabet
        int keyWordLength = keyWordToCharArray.length; // taille du mot-clé

        int[] keyWordToIntArray = new int[keyWordLength]; // tableau qui contiendra la version int du mot-clef

        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < keyWordLength; i++){ // convertie le mots cle en chiffre;

            for (int c = 0; c < alphabetLength; c++){

                if (keyWordToCharArray[i] == alphabetToCharArray[c]){
                    keyWordToIntArray[i] = ++c;
                    break;
                }
            }
        }

        int b =0;
        boolean let;
        for (char c : textToCharArray){ // pour chaque caractère du texte

            let = true;
            if (b == keyWordLength) b = 0;

            for (int i = 0 ; i < alphabetLength; i++){ // pour chaque lettre de l'alphabet

                if (c == alphabetToCharArray[i]){ // si la lettre du texte est égale à celle de l'alphabet on chiffre ou déchiffre

                    if (chiffre) sb.append((i+keyWordToIntArray[b] <= alphabetLength )
                            ? alphabetToCharArray[i + keyWordToIntArray[b]]
                            : alphabetToCharArray[i + keyWordToIntArray[b] - alphabetLength]); // on chiffre

                    else sb.append((i - keyWordToIntArray[b] >= 0 )
                            ? alphabetToCharArray[i - keyWordToIntArray[b]]
                            : alphabetToCharArray[i - keyWordToIntArray[b] + alphabetLength]); // on dèchiffre

                    let = false;
                    break;
                }
            }
            if (let) sb.append(c);
            b++;

        }
        return sb.toString();

    }

    /**
     * @param txt texte à grouper
     * @param nbr nombre de groupe
     * @return une liste de groupe de caractère
     * fonction qui groupe les caratères d'un texte suivant le nombre passé en peramètre
     */
    private List groupTheCharacters(String txt, int nbr){

        String[] tableGroup = new String[nbr]; // tableau qui va grouper les caractères

        for (int i = 0; i < nbr ; i ++) tableGroup[i] = ""; // init du tableau

        char[] text = txt.toUpperCase().toCharArray(); // on insère les caractères du texte dans un tableau

        for (int i = 0, b = 0 ; i < text.length; i++){ // pour chaque lettre

            if (b == nbr) b = 0; // dès que l'on arrive au dernier groupe l'on reset pour repartir de la première colonne

            if (text[i] != ' '){
                tableGroup[b] += text[i]; // on insère la lettre dans le tableau à la colonne b
                b++;  // on passe à la colonne suivante
            }

        }

        return new ArrayList(Arrays.asList(tableGroup).subList(0, nbr)); // l'on renvoie une liste de chaque case du tableau

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

        System.out.println(v.groupTheCharacters(txt, 4));
        System.out.println(v.groupTheCharacters(txt, 2));
        System.out.println(v.groupTheCharacters(txt, 8));



    }




}
