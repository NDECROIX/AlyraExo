package main;

public class Arbre {

    public Noeud racine;

    public void setRacine(Noeud racine) {
        this.racine = racine;
    }


    public Arbre() { }

    public Noeud getRacine() {
        return racine;
    }

    public void addNoeud(int valeur){

        Noeud openNoeud = getRacine();
        boolean nonRange = true;

        if (getRacine() == null){
            setRacine(new Noeud(valeur));
            nonRange = false;
        }

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
                    System.out.println("Le noeud qui contient la valeur " + findNoeud.getValeur() + " vous est retourné");
                    notFind = false;
                }
            }
            else
            {
                System.out.println("la valeur est inexistante ");
                notFind = false;
            }
        }

        return findNoeud;
    }

    public Noeud findVador(int val){

        Noeud findNoeud = getRacine();
        boolean notFind = true;

        while (notFind){

            if (findNoeud.getnGauche() != null || findNoeud.getnDroite() != null){

                if (val < findNoeud.getValeur() && findNoeud.getnGauche() != null){

                    if (val != findNoeud.getnGauche().getValeur()) {
                        findNoeud = findNoeud.getnGauche();
                    }
                    else if (val == findNoeud.getnGauche().getValeur()){
                        System.out.println("Le neoud qui contient sur ça feuille gauche " + val + " est  "  + findNoeud.getValeur());
                        notFind = false;
                    }

                }
                else if (val > findNoeud.getValeur() && findNoeud.getnDroite() != null){

                    if (val != findNoeud.getnDroite().getValeur()) {
                        findNoeud = findNoeud.getnDroite();
                    }
                    else if (val == findNoeud.getnDroite().getValeur()){
                        System.out.println("Le neoud qui contient sur ça feuille droite " + val + " est  "  + findNoeud.getValeur());
                        notFind = false;
                    }
                }
                else{
                    System.out.println("la valeur est inexistante ");
                    notFind = false;
                }

            }
            else{
                System.out.println("la valeur est inexistante ");
                notFind = false;
            }
        }
        return findNoeud;
    }


    public void parcourInfixe(Noeud noeud){
        if (noeud.getnGauche() != null) parcourInfixe(noeud.getnGauche());
        System.out.println(noeud.getValeur());
        if (noeud.getnDroite() != null) parcourInfixe(noeud.getnDroite());
    }

    public void deleteVal(int val){

        Noeud noeudDel = findVal(val);
        Noeud noeudTmp = noeudDel;
        Noeud noeudPr = findVador(val);

        if (noeudDel != null){
            boolean nonDelete = true;


            while(nonDelete){

                if (noeudDel.getnDroite() == null && noeudDel.getnGauche() == null){
                        if (noeudPr.getnGauche().getValeur() == noeudDel.getValeur()){
                              noeudPr.setnGauche(null);
                              noeudDel = null;
                        }
                         else if(noeudPr.getnDroite().getValeur() == noeudDel.getValeur()){
                            noeudPr.setnDroite(null);
                            noeudDel = null;
                         }
                         nonDelete = false;
                }
                else if (noeudDel.getnDroite() != null && noeudDel.getnGauche() == null){
                        if (noeudPr.getnDroite().getnDroite() == noeudDel.getnDroite()){
                            noeudPr.setnDroite(noeudDel.getnDroite());
                        }
                        else {
                            noeudPr.setnGauche(noeudDel.getnDroite());
                        }
                        nonDelete = false;
                }
                else if (noeudDel.getnGauche() != null && noeudDel.getnDroite() == null){
                        if (noeudPr.getnDroite().getnGauche() == noeudDel.getnGauche()){
                            noeudPr.setnDroite(noeudDel.getnGauche());
                        }
                        else{
                            noeudPr.setnGauche(noeudDel.getnGauche());
                        }
                        nonDelete = false;
                }
                else if (noeudDel.getnDroite() != null && noeudDel.getnGauche() != null){

                        noeudDel = noeudDel.getnGauche();

                        if (noeudDel.getnDroite() != null){

                            while(noeudDel.getnDroite().getnDroite() != null){

                                noeudDel = noeudDel.getnDroite();
                            }
                            noeudTmp.setValeur(noeudDel.getnDroite().getValeur());
                            noeudPr = noeudDel;
                            noeudDel = noeudDel.getnDroite();
                        }
                        else
                        {
                            noeudTmp.setValeur(noeudDel.getValeur());
                        }
                }
            }
        }
        else {
            System.out.println("la Val est inexistante");
        }
    }



}
