package main;

/**
 * Classe Noeud qui definit un noeud dans un arbre binaire
 *  avec une Valeur un noeud Gauche et Droite
 */
public class Noeud {

    private int valeur;
    private Noeud nGauche, nDroite;

    public Noeud(int valeur) {

        this.valeur = valeur;

    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public void setnGauche(Noeud nGauche) {
        this.nGauche = nGauche;
    }

    public void setnDroite(Noeud nDroite) {
        this.nDroite = nDroite;
    }

    public int getValeur() {
        return valeur;
    }

    public Noeud getnGauche() {
        return nGauche;
    }

    public Noeud getnDroite() {
        return nDroite;
    }
}
