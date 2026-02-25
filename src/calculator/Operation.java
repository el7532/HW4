package calculator;

public class Operation {

    /**
     *
     * @param op    operator
     * @param left  left operand
     * @param right right operand
     * @return result of computation
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

}