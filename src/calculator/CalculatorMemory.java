package calculator;

import java.util.LinkedList;

/**
* Represents the memory of the postfix calculator.

* This class uses a LinkedList to implement a stack. 
* 
*/

public class CalculatorMemory {
    
    /** Underlying stack structure storing integer values */
    private LinkedList<Integer> memory = new LinkedList<Integer>();

    /**
     * Pushes a number onto the top of the stack.
     * 
     * @param number the integer value to add to memory
     */
    public void push(int number) {
        memory.push(number); // O(1)
    }

    /**
     * Removes and returns the top value from the stack.
     * 
     * @return the most recently pushed integer
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public int pop(){
        return memory.pop(); // O(1)
    }
    
    /**
     * Checks whether the stack is empty.
     * 
     * @return true if the stack contains no elements, false otherwise
     */ 
    public boolean isEmpty(){
        return memory.isEmpty();
    }

    /**
     * Returns the number of elements currently stored in memory.
     * 
     * @return the size of the stack
     */
    public int size() {
        return memory.size();
    }

    /**
     * Removes all elements from the stack.
     */
    public void clear(){
        memory.clear();
    }
    
    /**
     * Returns the memory object of the LinkedList.
     * 
     * @return the LinkedList storing the stack values
     */
    public LinkedList<Integer> getMemory() {
        return memory;
    }

    /**
     * Returns a formatted String representation of the stack contents.
     * 
     * The top of the stack is printed first, followed by each element
     * on a new line. The output ends with "---" to match the instructions.
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); 

        for (Integer num : memory) {
            sb.append(num); 
            sb.append("\n"); 
        }

        sb.append("---");
        sb.append("\n");
        return sb.toString(); 
    }
}
