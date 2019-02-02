package Tun;

/**
 * @param <K> Le lieu du serveur
 * @param <V> L'ip du serveur
 *           classe qui definit un serveur et son adresse ip
 */
class Noeud<K, V> {

    private K cle; // lieu du serveur

    private V valeur; // ip du serveur

    Noeud(K cle, V valeur){

        this.cle = cle;
        this.valeur = valeur;

    }

    K getCle() {
        return cle;
    }

    V getValeur() {
        return valeur;
    }

}
