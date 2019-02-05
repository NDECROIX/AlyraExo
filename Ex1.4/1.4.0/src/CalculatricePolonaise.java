import java.util.*;

public class CalculatricePolonaise {


    public void calcul(Deque<String> pile){

        int sizePile = pile.size();
        List<Integer> operands = new ArrayList();
        int result;


        for (int i = 0; i < sizePile; i++){

            switch (pile.getFirst()){

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
                    result = division(operands);
                    operands.clear();
                    operands.add(result);

                    break;
                case "/" :
                    result = multiplication(operands);
                    operands.clear();
                    operands.add(result);
                    break;
                    default:
                        operands.add(Integer.valueOf(pile.pollFirst()));
                        break;


            }

        }


    }


    public int addition(List<Integer> operands){

        int result = 0;

        for (int c : operands)
            result += c;

        return result;
    }

    public int subtraction(List<Integer> operands){


        return 0;
    }

    private int multiplication(List<Integer> operands) {
        return 0;
    }

    private int division(List<Integer> operands) {
        return 0;
    }




    public static void main(String[] args){

        Deque<String> deque = new ArrayDeque();

        deque.add("4");
        deque.add("5");
        deque.add("+");
        deque.add("2");
        deque.add("*");

    }


}
