// 1.3.2

import java.util.HashMap;
import java.util.Map;

public class FrequenceLettre {

    /**
     * @param txt texte
     * @return hash map d'occurrence par caractère
     */
    private Occure[] occurrences(String txt){

        Map<Character, Integer> fC = new HashMap<>(); // un hashmap qui va contenir un caractère et son nombre d'occurences

        char[] cL = txt.toCharArray(); // on range les caractères dans un tableau

        for (char c : cL){

            if (c != ' '){
                fC.merge(c, 1, (a, b) -> a + b);
                //si le caractère exist pas dans fC on l'ajoute sinon on MAJ sa valeur à +1
            }
        }


        return trieListe(fC);
    }

    /**
     * @param fC Map<Character, Integer>
     * @return tableau trié
     */
    private Occure[] trieListe(Map<Character, Integer> fC)
    {
        int sizeL = fC.size();
        Occure[] tblOcc = new Occure[sizeL];

        int t = 0;
        for (HashMap.Entry<Character, Integer> entry : fC.entrySet())
        {
            tblOcc[t] = new Occure(entry.getKey(), entry.getValue()); //
            t++;
        }

        Occure transpose;

        for (int i = 0; i < tblOcc.length; i++){

            for (int v = i; v < tblOcc.length; v++){

                if (tblOcc[i].i < tblOcc[v].i){
                    transpose = tblOcc[i];
                    tblOcc[i] = tblOcc[v];
                    tblOcc[v] = transpose;
                }
            }
        }

        return tblOcc;

    }
    
    class Occure {

        char a;
        int i;

        public Occure(char a, int i) {
            this.a = a;
            this.i = i;
        }

    }


    public static void main(String[] args){

        FrequenceLettre fl = new FrequenceLettre();

        for (Occure oc : fl.occurrences("You shall not passsss !")){
            System.out.print(oc.i + " " + oc.a + "     " );
        }

    }

}





