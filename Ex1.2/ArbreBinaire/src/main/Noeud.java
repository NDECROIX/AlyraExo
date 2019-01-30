package main;

public class Noeud {

    public int valeur;
    public Noeud nGauche, nDroite;

    public void setnGauche(Noeud nGauche) {
        this.nGauche = nGauche;
    }

    public void setnDroite(Noeud nDroite) {
        this.nDroite = nDroite;
    }

    public Noeud(int valeur) {

        this.valeur = valeur;

    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public Noeud(int valeur, Noeud nGauche, Noeud nDroite){

        this.valeur = valeur;
        this.nGauche = nGauche;
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
