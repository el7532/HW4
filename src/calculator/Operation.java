package calculator;

public class Operation {

    /**
     *
     * @param op    operator
     * @param left  left operand
     * @param right right operand
     * @return result of ((left operand) operator (right operand)) computation
     * @throws IllegalArgumentException if operator is not one of +,-,*,/.
     * @throws ArithmeticException      if operator is / and right operand is 0.
     */
    public static int performOperation(char op, int left, int right) {

        
        if (op != '+' && op != '-' && op != '*' && op != '/') {
                throw new IllegalArgumentException();
            } 
        else if (op == '/' && right == 0) { 
                throw new ArithmeticException("Error: divide by zero");
        } else {
            if (op == '+') {
                int sum = left + right;
                return sum; 
            } else if (op == '-') { 
                int diff = left - right; 
                return diff;
            } else if (op == '*') {
                int product = left * right;
                return product;
            } else {
                int quotient = left / right;
                return quotient;
            } 
        }
    }

    public static void main(String[] args) {
        int left = 1; 
        int right = 10; 
        System.out.println(performOperation('+', left, right));
        int left3 = 20; 
        int right3 = 10;
        System.out.println(performOperation('/', left3, right3));
        int left4 = 45; 
        int right4 = 23; 
        System.out.println(performOperation('-', left4, right4));
    }
}
