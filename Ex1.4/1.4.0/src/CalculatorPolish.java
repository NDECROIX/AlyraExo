import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Calculatrice qui place les opérandes avant l’opération à effectuer
 * (12 - 4) * 2 donne en notation polonaise inversée 12 4 - 2 *. Son exécution sera :
 * 12 4 - 2 * -> 8 2 *   -> 16
 */
public class CalculatorPolish {

    private static final String AUTHORIZE = "0123456789+-*/";


    /**
     * @param pile opération à effectuer
     * @return resultat de l'opération
     */
    private Deque<Integer> calc(@NotNull Deque<String> pile){

        int sizePile = pile.size();
        Deque<Integer> operands = new ArrayDeque<>();

        String nextIndex;

        for (int i = 0; i < sizePile; i++){

            nextIndex = pile.pollFirst();

            if (nextIndex != null && AUTHORIZE.contains(nextIndex) )
                switch (nextIndex){

                    case "+" :
                        if (operands.size() > 1) operands = addition(operands);
                        break;
                    case "-" :
                        if (operands.size() > 1) operands = subtraction(operands);
                        break;
                    case "*" :
                        if (operands.size() > 1) operands = multiplication(operands);
                        break;
                    case "/" :
                        if (operands.size() > 1) operands = division(operands);
                        break;
                        default:
                            operands.add(Integer.valueOf(nextIndex));
                            break;


                }

        }

        return operands;


    }


    /**
     * @param operands Pile de nombre à additionner
     * @return la somme des nombres additionés
     */
    private Deque<Integer> addition(@NotNull Deque<Integer> operands){

       operands.add(operands.pollLast() + operands.pollLast());

        return operands;
    }

    /**
     * @param operands Pile de nombre à soustraire
     * @return la somme
     */
    private Deque<Integer> subtraction(@NotNull Deque<Integer> operands){

        int a = operands.pollLast();
        int b = operands.pollLast();
       operands.add( b-a );

        return operands;
    }

    /**
     * @param operands Pile de nombre à multiplier
     * @return la somme
     */
    private Deque<Integer> multiplication(@NotNull Deque<Integer> operands) {

        operands.add(operands.pollLast() * operands.pollLast());

        return operands;
    }

    /**
     * @param operands Pile de nombre à diviser
     * @return la somme
     */
    private @NotNull Deque<Integer> division(@NotNull Deque<Integer> operands) {

        int a = operands.pollLast();
        int b = operands.pollLast();
        operands.add(b / a);

        return operands;
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

        CalculatorPolish cP = new CalculatorPolish();

        System.out.println("12 5 4 + - 2 * : " + cP.calc(cP.stringToPile("12 5 4 + - 2 *")).pollLast());

    }


}
