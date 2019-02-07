import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Calculatrice qui place les opérandes avant l’opération à effectuer
 * (12 - 4) * 2 donne en notation polonaise inversée 12 4 - 2 *. Son exécution sera :
 * 12 4 - 2 * -> 8 2 *   -> 16
 */
public class CalculatorPolish2 {

    private static final String AUTHORIZE = "0123456789+-*/=<>";
    /**
     * @param pile opération à effectuer
     * @return resultat de l'opération en String
     */
    private String calc(@NotNull Deque<String> pile){

        int sizePile = pile.size();
        Deque<Integer> operands = new ArrayDeque<>();
        int result = 0;
        String nextIndex;


        for (int i = 0; i < sizePile; i++){

            nextIndex = pile.pollFirst();
            if (nextIndex != null && AUTHORIZE.contains(nextIndex))
                switch (nextIndex){

                    case "+" :
                        result = addition(operands);
                        operands.clear();
                        operands.add(result);
                        break;
                    case "-" :
                        result = subtraction(operands);
                        operands.clear();
                        operands.add(result);

                        break;
                    case "*" :
                        result = multiplication(operands);
                        operands.clear();
                        operands.add(result);

                        break;
                    case "/" :
                        result = division(operands);
                        operands.clear();
                        operands.add(result);
                        break;
                    case "=" :
                        return operands.getFirst().equals(operands.getLast()) ? "True" : "False";

                    case ">" :
                        return operands.getFirst() > operands.getLast() ? "True" : "False";

                    case "<" :
                        return operands.getFirst() < operands.getLast() ? "True" : "False";

                        default:
                            operands.add(Integer.valueOf(nextIndex));
                            break;


                }

        }


        return String.valueOf(result);


    }


    /**
     * @param operands Pile de nombre à additionner
     * @return la somme des nombres additionés
     */
    private int addition(@NotNull Deque<Integer> operands){

        int result = 0;

        for (int c : operands)
            result += c;

        return result;
    }

    /**
     * @param operands Pile de nombre à soustraire
     * @return la somme
     */
    private int subtraction(@NotNull Deque<Integer> operands){

        int result = operands.pollFirst();

        for (int c : operands)
            result -= c;

        return result;
    }

    /**
     * @param operands Pile de nombre à multiplier
     * @return la somme
     */
    private int multiplication(@NotNull Deque<Integer> operands) {

        int result = operands.pollFirst();

        for (int c : operands)
            result *= c;

        return result;
    }

    /**
     * @param operands Pile de nombre à diviser
     * @return la somme
     */
    private int division(@NotNull Deque<Integer> operands) {

        int result = operands.pollFirst();

        for (int c : operands)
            result /= c;

        return result;
    }

    /**
     * @param str opération à effectuer
     * @return un ArrayDeque de l'operation
     */
    private  ArrayDeque<String> stringToPile(String str){

        ArrayDeque<String> operation = new ArrayDeque<>();
        StringBuilder op = new StringBuilder();

        for (char c : str.toCharArray()){

            if (c != ' ' && AUTHORIZE.contains(String.valueOf(c))) op.append(c);

            else if (!op.toString().equals("")){

                operation.add(op.toString());
                op = new StringBuilder();
            }
        }
        if (!op.toString().equals("")) operation.add(op.toString());

        return operation;

    }


    public static void main(String[] args){


        CalculatorPolish2 cP = new CalculatorPolish2();

        System.out.println("12 4 - 2 * 2 / 4 + = " + cP.calc(cP.stringToPile(" 12 4 - 2 * 2 / 4 + ")));

        System.out.println("12 4 < : " + cP.calc(cP.stringToPile("12 4 <")));

        System.out.println("12 4 > : " + cP.calc(cP.stringToPile("12 4 >")));

        System.out.println("12 4 = : " + cP.calc(cP.stringToPile("12 4 =")));

    }


}
