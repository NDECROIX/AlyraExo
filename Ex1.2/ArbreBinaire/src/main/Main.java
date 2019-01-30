package main;

import java.util.Scanner;

public class Main {

    static Arbre arbre;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){

    arbre = new Arbre();
    arbre.ajouter(80);
    arbre.ajouter(68);
    arbre.ajouter(18);
    arbre.ajouter(90);
    arbre.ajouter(99);
    arbre.ajouter(10);
    arbre.ajouter(8);
    arbre.ajouter(150);
    arbre.ajouter(125);
    arbre.ajouter(33);
    arbre.ajouter(24);
    arbre.ajouter(89);
    arbre.ajouter(42);
    arbre.ajouter(16);


    arbre.chercheValeur(10, arbre.getRacine());
    arbre.parcourInfixe(arbre.getRacine());
    arbre.suppressionValeur(19, arbre.getRacine());


    arbre.parcourInfixe(arbre.getRacine());



    }

}
