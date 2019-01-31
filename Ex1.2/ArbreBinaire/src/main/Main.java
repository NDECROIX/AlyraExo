package main;

import java.util.Scanner;

public class Main {

    static Arbre arbre;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){

    arbre = new Arbre();
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



    arbre.findVal(10);
    arbre.parcourInfixe(arbre.getRacine());
    //System.gc();
    //arbre.suppressionValeur(18, arbre.getRacine());
    //System.gc();
    //arbre.parcourInfixe(arbre.getRacine());
    //arbre.findVal(33);
    //arbre.findVador(89);
    arbre.deleteVal(8);
    arbre.parcourInfixe(arbre.findVal(150));

    }

}
