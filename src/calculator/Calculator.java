package calculator;

import java.util.Scanner;

/**
 * Represents an interactive postfix calculator.
 *
 * This class handles user interaction, parses input, performs operations,
 * and maintains the calculator using a CalculatorMemory instance.
 *
 * Supported operations:
 *  +, -, *, /
 *  pop
 *  clear
 *  quit
 *
 * The calculator runs continuously until the user enters "quit".
 */

public class Calculator {

    /** Stack-based memory used to store operands and results */
    private CalculatorMemory memory;
    /** Scanner used to read user input from the console */
    private Scanner scanner;

    /**
     * Constructs a Calculator with an empty memory and initializes input scanner.
     */
    public Calculator() {
        this.memory = new CalculatorMemory();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Helper method that evaluates a single user input.
     *
     * If the input is:
     *  - An integer → pushes it onto memory stack.
     *  - An operator → pops two operands, computes result, and pushes result.
     *
     * @param input the user input string
     * @return the computed result if an operator was applied, or the value if a number was pushed
     * @throws IllegalArgumentException if input is invalid or not enough operands exist
     * @throws ArithmeticException if dividing by zero 
     */
    private int evaluation(String input) {

        String i = input.strip();
        
        // Case 1: Input is a number
        if (isInteger(i)) {
            int value = Integer.parseInt(i); 
            memory.push(value);
            return value;
        
        // Case 2: Input is an operator
        } else if (isOperator(i)) {
            // Ensure at least two operands exist
            if (memory.size() < 2) {
                throw new IllegalArgumentException("Error: operator requires two arguments");
            }
            int right = memory.pop(); // first = right 
            int left = memory.pop(); // second = left
            char op = input.charAt(0);

            // Ensure that dividing by zero doesn't change the stack

            try {
                int result = Operation.performOperation(op, left, right);
                memory.push(result);
                return result;
            } catch (ArithmeticException e) {
                memory.push(left);
                memory.push(right);
                throw e;
            }
        } 
        
        // Case 3: Invalid input
        else {
            throw new IllegalArgumentException("Error: expected number or valid operator");
        } 

    }

    /**
     * Determines whether a given string represents a valid integer.
     *
     * @param input string to test
     * @return true if input can be parsed as an integer, false otherwise
     */
    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Determines whether a given string represents a valid operator (+, -, *, /).
     *
     * @param input string to test
     * @return true if input is a valid operator, false otherwise
     */
    private boolean isOperator(String input) {
        return input.length() == 1 && 
        (input.equals("+") || 
        input.equals("-") || 
        input.equals("*") || 
        input.equals("/"));
    }

    /**
     * Runs the calculator.
     *
     * Will continuosly prompt the user for input until "quit".
     * Handles numbers, operators, pop, clear, and error cases.
     */
    public void run() {

        while (true) {

            System.out.println("Enter a number or operator: ");
            System.out.println();

            String input = scanner.nextLine();

            // Exit condition
            if (input.strip().equals("quit")) {
                break;
            }
            
            try {

                // Handle operator
                if (isOperator(input.strip())) {
                    int answer = evaluation(input);
                    System.out.println("Answer: " + answer);
                } 
                
                // Handle integer
                else if (isInteger(input.strip())) {
                    evaluation(input);
                } 

                // Handle pop
                else if (input.strip().equals("pop")) {
                    if (!memory.isEmpty()) {
                        System.out.println("Answer: " + memory.pop());
                    } else {
                        System.out.print("Error: pop requires one argument");
                    }
                } 
                
                // Handle clear
                else if (input.strip().equals("clear")) {
                    memory.clear();
                }

                // Invalid input
                else {
                    throw new IllegalArgumentException("Error: expected number or valid operator");
                }
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            // Print memory after processing input
            System.out.println();
            System.out.println("Memory contents:");
            System.out.println(memory.toString());
        }
    }

}

