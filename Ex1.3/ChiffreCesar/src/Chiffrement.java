// EXO 1.3.1

public class Chiffrement {


    private Chiffrement(){ }

    /**
     * @param txt text qui subit un chiffrage par decal
     * @param decal la decal à effectuer
     * @return le text decalé
     * Gère tous les caractères
     */
    public static String chiffreCesar(String txt, int decal){

        char[] text = txt.toLowerCase().toCharArray(); // on range les caractères dans un tableau
        String txtChiffre = "";


        for (int i = 0; i < text.length; i++){

            int l = ((int)text[i]) + decal; // on décale le caractère

            txtChiffre += (char) l; // on l'ajoute à la chaîne txtRevers
        }

        return txtChiffre;

    }

    public static void main(String[] args){

        String txt = "You shall not pass !";

        System.out.println(chiffreCesar(txt, 1));
        System.out.println(chiffreCesar(txt, -1));
        System.out.println(chiffreCesar(txt, 2));
        System.out.println(chiffreCesar(txt, -2));
        System.out.println(chiffreCesar(txt, 999999999));
        System.out.println(chiffreCesar(txt, -999999999));

    }

}
