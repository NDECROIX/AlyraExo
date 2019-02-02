package main;

import java.util.Random;

/**
 * Classe Arbre
 *  Creation Suppression & affichage de noeuds
 */
public class Arbre {

    private Noeud racine;

    public Arbre() { }

    public Noeud getRacine() {
        return racine;
    }

    public void setRacine(Noeud racine) {
        this.racine = racine;
    }

    /**
     * @param valeur  ajoute une valeur à l'arbre binaire
     */
    public void addNoeud(int valeur){

        Noeud openNoeud = getRacine();
        boolean nonRange = true;

        if (getRacine() == null){
            setRacine(new Noeud(valeur));
            nonRange = false;
        }

        /*Parcours l'arbre en fonction de la valeur des noeuds à la recherche
        * d'un noeud avec une feuille libre */

        while (nonRange){
            if (valeur < openNoeud.getValeur()){
                if (openNoeud.getnGauche() != null){
                    openNoeud = openNoeud.getnGauche();
                }
                else{
                    openNoeud.setnGauche(new Noeud(valeur));
                    nonRange = false;
                }
            }
            else {
                if (openNoeud.getnDroite() != null){
                    openNoeud = openNoeud.getnDroite();
                }
                else{
                    openNoeud.setnDroite(new Noeud(valeur));
                    nonRange = false;
                }
            }
        }
    }

    /**
     * @param val Valeur à trouver dans l'arbre binaire
     * @return Le noeud qui contient la valeur passer en paramètre
     */
    public Noeud findVal(int val){

        Noeud findNoeud = getRacine();
        boolean notFind = true;

        while (notFind){

            if (findNoeud != null){
                if (val < findNoeud.getValeur()){
                    findNoeud = findNoeud.getnGauche();
                }
                else if (val > findNoeud.getValeur()){
                    findNoeud = findNoeud.getnDroite();
                }
                else {
                    notFind = false;
                }
            }
            else
            {
                notFind = false;
            }
        }

        return findNoeud;
    }

    /**
     * @param val Valeur du fils dont l'on veut retrouver le père
     * @return Retourne le père qui contient dans une de ses feuilles la valeur passée en param
     */
    public Noeud findVader(int val){

        Noeud findNoeud = getRacine();
        boolean notFind = true;

        while (notFind){

            if (findNoeud.getnGauche() != null || findNoeud.getnDroite() != null){

                if (val < findNoeud.getValeur() && findNoeud.getnGauche() != null){

                    if (val != findNoeud.getnGauche().getValeur()) {
                        findNoeud = findNoeud.getnGauche();
                    }
                    else if (val == findNoeud.getnGauche().getValeur()){
                        notFind = false;
                    }

                }
                else if (val > findNoeud.getValeur() && findNoeud.getnDroite() != null){

                    if (val != findNoeud.getnDroite().getValeur()) {
                        findNoeud = findNoeud.getnDroite();
                    }
                    else if (val == findNoeud.getnDroite().getValeur()){
                        notFind = false;
                    }
                }
                else{
                    notFind = false;
                }

            }
            else{
                notFind = false;
            }
        }
        return findNoeud;
    }


    /**
     * @param noeud
     * Parcours infixe de l'arbre binaire de façon recursive
     */
    public void parcourInfixe(Noeud noeud){
        if (noeud != null) {
            if (noeud.getnGauche() != null) parcourInfixe(noeud.getnGauche());
            System.out.println(noeud.getValeur());
            if (noeud.getnDroite() != null) parcourInfixe(noeud.getnDroite());
        }
    }

    /**
     * @param val Valeur que l'on veut supp de l'arbre binaire
     *            on supprime la valeur passée en paramètre
     *            en la remplaçant  par la valeur la plus proche
     */
    public void deleteVal(int val){

        Noeud noeudDel = findVal(val); // on récupère le noeud à partir de la valeur
        Noeud noeudTmp = noeudDel; // on garde le noeud en memoire
        int valeurARemonter = 0; // on sauvegarde la valeur que l'on remonte pour l'assigner apres la supression de son homologue
        Noeud noeudPr = findVader(val); // on stock le père du noeud

        if (noeudDel != null){
            boolean nonDelete = true;


            while(nonDelete){

                if (noeudDel.getnDroite() == null && noeudDel.getnGauche() == null) {       // si le noeud na pas de feuilles on le supp lui et sa référence

                    if (noeudPr.getnGauche() != null && noeudPr.getnGauche().getValeur() == noeudDel.getValeur()) {

                        noeudPr.setnGauche(null); // supp la feuille gauche du pére qui contien la ref au noeud
                        noeudDel = null; // on supp le noeud

                    } else if (noeudPr.getnDroite() != null && noeudPr.getnDroite().getValeur() == noeudDel.getValeur()) { // feuille droite

                        noeudPr.setnDroite(null);
                        noeudDel = null;

                    }

                    noeudTmp.setValeur(valeurARemonter);
                    nonDelete = false;

                } else if (noeudDel.getnDroite() != null && noeudDel.getnGauche() == null) {  // si seulement la feuille droite est présente on la remonte au père

                    if (noeudPr.getnDroite().getnDroite() == noeudDel.getnDroite()) {

                        noeudPr.setnDroite(noeudDel.getnDroite());

                    } else {

                        noeudPr.setnGauche(noeudDel.getnDroite());
                    }

                    noeudTmp.setValeur(valeurARemonter);
                    nonDelete = false;

                } else if (noeudDel.getnGauche() != null && noeudDel.getnDroite() == null) { // si seulement la feuille gauche est présente on la remonte au père

                    if (noeudPr.getnDroite().getnGauche() == noeudDel.getnGauche()) {
                        noeudPr.setnDroite(noeudDel.getnGauche());

                    } else {

                        noeudPr.setnGauche(noeudDel.getnGauche());
                    }

                    noeudTmp.setValeur(valeurARemonter);
                    nonDelete = false;

                } else if (noeudDel.getnDroite() != null && noeudDel.getnGauche() != null) {
                    /*
                     * si les deux feuilles son présentes l'on prend la feuille de gauche puis la feuille la plus à droite
                     * on assigne la valeur de la feuille au noeud que l'on supprime et on fait passer le noeud de la dernière
                     * feuille dans la boucle pour la supprimer et eviter un doublon.
                     * choisir toujours la feuille de gauche créer un déséquilibre dans l'arbre c'est pourquoi
                     * j'ai mis un facteur random*/

                    Random randomno = new Random();
                    boolean feuilleRand = randomno.nextBoolean();


                    if (feuilleRand) {

                        noeudDel = noeudDel.getnGauche();
                        valeurARemonter = noeudDel.getValeur();

                        if (noeudDel.getnDroite() != null) {

                            while (noeudDel.getnDroite().getnDroite() != null) {

                                noeudDel = noeudDel.getnDroite();
                            }

                            valeurARemonter = noeudDel.getnDroite().getValeur();
                            noeudPr = noeudDel;
                            noeudDel = noeudDel.getnDroite();
                        }

                    } else {

                        noeudDel = noeudDel.getnDroite();
                        valeurARemonter = noeudDel.getValeur();

                        if (noeudDel.getnGauche() != null) {

                            while (noeudDel.getnGauche().getnGauche() != null) {

                                noeudDel = noeudDel.getnGauche();
                            }

                            valeurARemonter = noeudDel.getnGauche().getValeur();
                            noeudPr = noeudDel;
                            noeudDel = noeudDel.getnGauche();
                        }
                    }
                }
            }
        }
    }
}
