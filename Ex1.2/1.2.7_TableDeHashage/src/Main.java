

public class Main {

    public static void main(String[] args)
    {
        Map<String, String> map = new Map<>();

        map.addValeur("Amsterdam", "153.8.223.72");
        map.addValeur("Chennai", "169.38.84.49");
        map.addValeur("Dallas", "169.46.49.112");
        map.addValeur("Dallas, TX, USA", "184.173.213.155");
        map.addValeur("Frankfurt", "159.122.100.41");
        map.addValeur("Hong Kong", "119.81.134.212");
        map.addValeur("London", "5.10.5.200");
        map.addValeur("London", "158.176.81.249");
        map.addValeur("Melbourne", "168.1.168.251");
        map.addValeur("Mexico City", "169.57.7.230");
        map.addValeur("Milan", "159.122.142.111");
        map.addValeur("Paris", "159.8.78.42");
        map.addValeur("San Jose", "192.155.217.197");
        map.addValeur("São Paulo", "169.57.163.228");
        map.addValeur("Toronto", "169.56.184.72");
        map.addValeur("Washington DC", "50.87.60.166");

        map.afficheListe();

        map.trouverServeur("Paris");
        map.trouverServeur("London");
        map.trouverServeur("Amsterdam");

    }

}
