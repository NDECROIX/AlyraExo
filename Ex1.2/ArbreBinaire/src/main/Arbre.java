package main;

public class Arbre {

    public Noeud racine;
    public Noeud noeudTrouver;

    public Arbre() { }

    public void ajouter(int valeur){
        if (racine == null){
            racine = new Noeud(valeur);
        }
        else{
            ajouterNoeud(valeur, racine);
        }
    }

    public Noeud getRacine() {
        return racine;
    }

    public void ajouterNoeud(int valeur, Noeud noeud) {
        if (valeur < noeud.getValeur()){
            if (noeud.getnGauche() != null){
                ajouterNoeud(valeur, noeud.getnGauche());
            }
            else noeud.setnGauche(new Noeud(valeur));
        }
        else{
            if (noeud.getnDroite() != null){
                ajouterNoeud(valeur, noeud.getnDroite());
            }
            else{
                noeud.setnDroite(new Noeud(valeur));
            }
        }
    }

    public void chercheValeur(int valeur, Noeud noeud){

        if (noeud == null){
            System.out.println("Cette valeur n'existe pas !");
        }
        if (noeud.getValeur() == valeur){

            String gauche =  (noeud.getnGauche() != null) ? String.valueOf(noeud.getnGauche().getValeur()) : "null";
            String droite =  (noeud.getnDroite() != null) ? String.valueOf(noeud.getnDroite().getValeur()) : "null";

            System.out.println("Le noeud qui contien la valeur "
                    + valeur
                    + " à comme fils gauche "
                    + gauche
                    + " et comme fils droite "
                    + droite);
            noeudTrouver = noeud;

        }
        else if (valeur < noeud.getValeur()){
            chercheValeur(valeur, noeud.getnGauche());
        }
        else if (valeur > noeud.getValeur()){
            chercheValeur(valeur, noeud.getnDroite());
        }


    }

    public void parcourInfixe(Noeud noeud){
        if (noeud.getnGauche() != null) parcourInfixe(noeud.getnGauche());
        System.out.println(noeud.getValeur());
        if (noeud.getnDroite() != null) parcourInfixe(noeud.getnDroite());
    }

    public void suppressionValeur(int valeur, Noeud noeud){
        chercheValeur(valeur, noeud);
        noeud = noeudTrouver;

        if (noeud.getnGauche().getnDroite() != null){
            Noeud noeudD = noeud.getnGauche();

            while (noeudD.getnDroite() != null){
                noeudD = noeudD.getnDroite();

            }

            noeud = noeudD;
            noeudD = null;

        }
        else if (noeud.getnDroite().getnGauche() != null){
            Noeud noeudG = noeud.getnDroite();

            while(noeudG.getnGauche() != null){
                noeudG = noeudG.getnGauche();
            }
            noeud = noeudG;
            noeudG = null;
        }
        else{
            noeud = null;
        }
    }

}
