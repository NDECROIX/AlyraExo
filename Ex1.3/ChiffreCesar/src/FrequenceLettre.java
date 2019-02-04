// 1.3.2

import java.util.HashMap;
import java.util.Map;

public class FrequenceLettre {

    /**
     * @param txt texte
     * @return hash map d'occurrence par caractère
     */
    private Map occurrences(String txt){

        Map<Character, Integer> fC = new HashMap<>(); // un hashmap qui va contenir un caractère et son nombre d'occurences

        char[] cL = txt.toCharArray(); // on range les caractères dans un tableau

        for (char c : cL){

            if (c != ' '){
                fC.merge(c, 1, (a, b) -> a + b);
                //si le caractère exist pas dans fC on l'ajoute sinon on MAJ sa valeur
            }
        }
        return fC;
    }



    public static void main(String[] args){

        FrequenceLettre fl = new FrequenceLettre();

        System.out.println(fl.occurrences("You shall not passsss !"));


    }

}


