package Tun;

import java.util.LinkedList;

/**
 * @param <K> lieu du serveur
 * @param <V> ip du serveur
 *           classe qui crée un tableau de n cellule qui son des listes chaînées *
 */
public class Map<K, V> {

    private LinkedList<Noeud>[] niveauListe; // Creation du tableau de LinkedList

    private int nbrNiveau; // Taille du tableau

    /**
     * init le tableau avec une LinkedListe à chaque Cellule
     */
    public Map(){

        nbrNiveau = 8;
        niveauListe = new LinkedList[nbrNiveau];

        for(int i = 0 ; i < nbrNiveau; i++) {
            niveauListe[i] = new LinkedList<>();
        }

    }


    /**
     * @param key lieu du serveur
     * @return le niveau du tableau
     * fonction qui transforme la cle en nombre avec hashCode()
     * on récupère le reste de la / par 8 ( *-1 si la valeur est négative )
     */
    private int getNiveau(K key){

        int hashCode = key.hashCode();
        int index = hashCode % nbrNiveau;
        if (index < 0 ) index *= -1;
        return index;

    }

    /**
     * @param key lieu du serveur
     * @param val ip du serveur
     * on ajoute le noeud à la LinkedList
     */
    public void addValeur(K key, V val){

        int index = getNiveau(key);
        niveauListe[index].add(new Noeud<>(key, val));

    }

    /**
     * Affiche toute la liste
     */
    public void afficheListe(){
        for(int i = 0 ; i < nbrNiveau; i++) {

            for (int b = 0; b < niveauListe[i].size(); b++ ){

                System.out.println(niveauListe[i].get(b).getCle() + " : " + niveauListe[i].get(b).getValeur());
                System.out.println("----------------------------------");

            }

        }
    }

    /**
     * @param key lieu du serveur
     * parcours la LinkedList
     * retourne les adresses ip du lieu indiqué en paramètre
     */
    public void trouverServeur(K key){
        int index = getNiveau(key);
        for (Noeud n : niveauListe[index]){
            if (n.getCle() == key) System.out.println(n.getCle() + " : " + n.getValeur());
        }
    }

}
