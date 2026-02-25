package calculator;

import java.util.Scanner;

public class Calculator {

    private CalculatorMemory memory;
    private Scanner scanner;

    public char op; 
    public int left;
    public int right;

    public Calculator() {

        this.memory = new CalculatorMemory();
        scanner = new Scanner(System.in);

    }

    private int evaluation(String input) {

        String i = input.strip();
        
        if (isInteger(i)) {
            int value = Integer.parseInt(i); 
            memory.push(value);
            return value;
        } else if (isOperator(i)) {
            if (memory.isEmpty() || memory.getMemory().size() < 2) {
                throw new IllegalArgumentException("Error: operator requires two arguments");
            }
            int right = memory.pop();
            int left = memory.pop(); 

            char op = input.charAt(0);
            int result = Operation.performOperation(op, left, right);

            memory.push(result);
            return result;
        } else {
            throw new IllegalArgumentException("Error: expected number or valid operator");
        } 

    }

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isOperator(String input) {
        return input.length() == 1 && (input.equals("+") || input.equals("-") || 
        input.equals("*") || input.equals("/"));
    }


    public void run() {

        while (true) {

            System.out.println("Enter a number or operator: ");
            System.out.println();

            String input = scanner.nextLine();

            if (input.strip().equals("quit")) {
                break;
            }

            if (isOperator(input.strip())) {
                int answer = evaluation(input);
                System.out.println("Answer: " + answer);
                System.out.println();
                System.out.println("Memory contents: ");
                System.out.println(memory.toString());
            } else if (isInteger(input.strip())) {
                evaluation(input);
                System.out.println();
                System.out.println("Memory contents: ");
                System.out.println(memory.toString());
            } else if (input.strip().equals("pop")) {
                if (!memory.isEmpty()) {
                    System.out.println("Answer: " + memory.pop());
                    System.out.println();
                    System.out.println("Memory contents: ");
                    System.out.println(memory.toString());
                } else {
                    System.out.print("Error: pop requires one argument");
                }
            } else if (input.strip().equals("clear")) {
                memory.clear();
                System.out.println();
                System.out.println("Memory contents: ");
            } else {
                throw new IllegalArgumentException("Error: expected number or valid operator");
            }
        }
    }


    public static void main(String[] args) {

        Calculator calc = new Calculator();
        calc.run();

    }

}