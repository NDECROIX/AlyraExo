import java.util.InputMismatchException;
import java.util.Scanner;

public class CheckEnter {

    private static final String HEXADECIMAL = "0123456789abcdef";
    private static final String DECIMAL = "0123456789";

    private static Scanner sc = new Scanner(System.in);

    /**
     * teste la saisie d'un nombre
     * @param message message si la saisie est incorect
     * @param valMin valeur minimum autorise
     * @param valMax valeur maximum autorise
     * @return la valeur
     */
    public static int enterNumber(String message, int valMin, int valMax){

        int enterNumber = 0;

        boolean correct = false;

        do{
            try {
                sc = new Scanner(System.in);
                enterNumber = sc.nextInt();

                if (enterNumber < valMin || enterNumber > valMax){
                    System.out.println(message);
                }else{
                    correct = true;
                }

            }catch (InputMismatchException e){
                System.out.println(message);
                sc.nextLine();
            }
        } while (!correct);

        return enterNumber;

    }

    public static String enterHexadecimal(String msg){

        StringBuilder sb;
        String hexadecimal;
        boolean correct;

        do {
            correct = true;
            sb = new StringBuilder();

            System.out.println(msg);

            sc = new Scanner(System.in);
            hexadecimal = sc.nextLine();

            hexadecimal = hexadecimal.toLowerCase();

            if (hexadecimal.startsWith("0x")) hexadecimal = hexadecimal.substring(2);

            for (char c : hexadecimal.toCharArray()){
                if (c != ' ') sb.append(c);
            }

            for (char c : sb.toString().toCharArray()){
                if (!HEXADECIMAL.contains(String.valueOf(c))) correct = false;
            }

            if (!correct) System.out.println("Veuillez rentrer un nombre en base 16 --> " + HEXADECIMAL );


        } while (!correct);

        return sb.toString();
    }

    public static String enterDecimal(){

        String decimale;
        boolean correct;

        do {

            correct = true;

            System.out.println("Veuillez saisir votre nombre  : ");

            sc = new Scanner(System.in);
            decimale = sc.next();

            decimale = decimale.toLowerCase();

            for (char c : decimale.toCharArray()){
                if (!DECIMAL.contains(String.valueOf(c))) correct = false;
            }

            if (!correct) System.out.println("Saisie invalide! \nLes caractères authorisés --> " + DECIMAL );

        } while (!correct);

        return decimale;
    }

}
