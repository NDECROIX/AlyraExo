package main;

public class Main {

    public static void main(String[] args){

        Arbre arbre = new Arbre();
        arbre.addNoeud(80);
        arbre.addNoeud(68);
        arbre.addNoeud(18);
        arbre.addNoeud(90);
        arbre.addNoeud(99);
        arbre.addNoeud(10);
        arbre.addNoeud(8);
        arbre.addNoeud(150);
        arbre.addNoeud(125);
        arbre.addNoeud(33);
        arbre.addNoeud(24);
        arbre.addNoeud(89);
        arbre.addNoeud(42);
        arbre.addNoeud(16);

        arbre.parcourInfixe(arbre.getRacine());

        arbre.deleteVal(89);
        arbre.deleteVal(8);
        arbre.deleteVal(16);

        System.out.println("======================================");

        arbre.parcourInfixe(arbre.getRacine());

        arbre.deleteVal(42);
        arbre.deleteVal(90);
        arbre.deleteVal(125);

        System.out.println("======================================");

        arbre.parcourInfixe(arbre.getRacine());

        arbre.addNoeud(89);
        arbre.addNoeud(8);
        arbre.addNoeud(16);

        System.out.println("======================================");

        arbre.parcourInfixe(arbre.getRacine());

        arbre.addNoeud(125);
        arbre.addNoeud(90);
        arbre.addNoeud(42);

        System.out.println("======================================");

        arbre.parcourInfixe(arbre.getRacine());

        System.out.println("======================================");
        System.out.println(arbre.findVal(8).getValeur());
        System.out.println(arbre.findVal(10).getValeur());
        System.out.println(arbre.findVal(24).getValeur());
        System.out.println(arbre.findVal(33).getValeur());
        System.out.println(arbre.findVal(42).getValeur());
        System.out.println(arbre.findVal(80).getValeur());
        System.out.println(arbre.findVal(10).getValeur());
        System.out.println(arbre.findVal(125).getValeur());
        System.out.println("======================================");


    }

}
