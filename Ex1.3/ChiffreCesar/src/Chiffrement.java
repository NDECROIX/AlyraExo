// EXO 1.3.1

public class Chiffrement {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz"; // on ajoute ici les caratères souhaitée

    private Chiffrement(){ }

    /**
     * @param txt text qui subit un chiffrage par decale
     * @param decale la decale à effectuer
     * @return le text chiffré
     */
    public static String chiffreCesar(String txt, int decale){

        char[] text = txt.toLowerCase().toCharArray(); // on range les caractères dans un tableau
        char[] alphabet = ALPHABET.toCharArray();

        StringBuilder sb = new StringBuilder(); // chaîne qui va contenir le texte chiffré

        int alpL = alphabet.length;

        if (decale > alpL) decale = decale % alpL; // on met à niveau les décales
        if (decale < 0) decale = decale % alpL + alpL;

        boolean laisse;
        for (int c : text){ // pour chaque lettre dans le texte

            laisse = true;

                for (int i = 0 ; i < alpL ; i++){ // retrouver la lettre dans l'alphabet

                    if (c == alphabet[i] ){
                        sb.append((i+decale < alpL) ? alphabet[i+decale] : alphabet[i+decale-alpL] ); // assigner la lettre à la position i + décale
                        laisse = false;
                        break;
                    }

                }

            if (laisse) sb.append((char) c); // laisse les caractères non présent dans l'alpahbet

        }

        return sb.toString();

    }

    public static void main(String[] args){

        String txt = "You shall not pass!";

        System.out.println(chiffreCesar(txt, 1));
        System.out.println(chiffreCesar(txt, 268));
        System.out.println(chiffreCesar(txt, -5));
        System.out.println(chiffreCesar(txt, -268));

    }

}
