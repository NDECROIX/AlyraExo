package main;

public class Arbre {

    public Noeud racine;
    public Noeud noeudTrouver;
    public Noeud noeudPere;

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

        if (noeud == null || noeud.getValeur() == 0){
            System.out.println("Cette valeur n'existe pas !");
        }
        else {
            if (noeud.getValeur() == valeur) {

                String gauche = (noeud.getnGauche() != null) ? String.valueOf(noeud.getnGauche().getValeur()) : "null";
                String droite = (noeud.getnDroite() != null) ? String.valueOf(noeud.getnDroite().getValeur()) : "null";

                System.out.println("Le noeud qui contien la valeur "
                        + valeur
                        + " à comme fils gauche "
                        + gauche
                        + " et comme fils droite "
                        + droite);
                noeudTrouver = noeud;

            } else if (valeur < noeud.getValeur()) {
                chercheValeur(valeur, noeud.getnGauche());
            } else if (valeur > noeud.getValeur()) {
                chercheValeur(valeur, noeud.getnDroite());
            }
        }


    }

    public void cherchePere(int valeur, Noeud noeud){

        noeudPere = null;
        if (noeud == null ){
            System.out.println("Cette valeur n'existe pas !");
        }
        else {
            if (noeud.getnGauche() != null && noeud.getnGauche().getValeur() == valeur){
                noeudPere = noeud;
            }
            if (noeud.getnDroite() != null && noeud.getnDroite().getValeur() == valeur){
                noeudPere = noeud;
            }
            else if (valeur < noeud.getValeur()) {
                chercheValeur(valeur, noeud.getnGauche());
            }
            else if (valeur > noeud.getValeur()) {
                chercheValeur(valeur, noeud.getnDroite());
            }
        }


    }

    public void parcourInfixe(Noeud noeud){
        if (noeud.getnGauche() != null) parcourInfixe(noeud.getnGauche());
        System.out.println(noeud.getValeur());
        if (noeud.getnDroite() != null) parcourInfixe(noeud.getnDroite());
    }

    public void suppressionValeur(int valeur, Noeud noeud){

        Noeud noeudA = noeud;

        if (noeud.getValeur() != valeur){
            chercheValeur(valeur, noeud);
            noeudA = noeudTrouver;
        }

        Noeud noeudB = noeudA;
        Noeud noeudC = null;

        if (noeudA != null){

            if (noeudA.getnGauche() != null){
                noeudA = noeudA.getnGauche();
                while (noeudA.getnDroite() != null){
                    noeudC = noeudA;
                    noeudA = noeudA.getnDroite();
                }

                noeudPere = noeudC;
                System.out.println(noeudC.getValeur());
                noeudB.setValeur(noeudA.getValeur());
                suppressionValeur(noeudA.getValeur(), noeudA);

            }
            else if (noeudA.getnDroite() != null){
                noeudA = noeudA.getnGauche();
                while (noeudA.getnGauche() != null){
                    noeudC = noeudA;
                    noeudA = noeudA.getnGauche();
                }

                noeudPere = noeudC;
                System.out.println(noeudC.getValeur());
                noeudB.setValeur(noeudA.getValeur());
                suppressionValeur(noeudA.getValeur(), noeudA);
            }
            else{
                System.out.println("Le noeud pere est : " + noeudPere.getValeur());
                if (noeudPere != null){
                    if (noeudPere.getnGauche() == noeudB){
                        noeudB = null;
                        noeudPere.setnGauche(null);
                    }
                    else if (noeudPere.getnDroite() == noeudB){
                        noeudB = null;
                        noeudPere.setnDroite(null);
                    }
                }

            }
        }

    }

}
