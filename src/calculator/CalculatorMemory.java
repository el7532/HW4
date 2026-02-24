package calculator;

import java.util.LinkedList;

public class CalculatorMemory {

    LinkedList<Integer> list = new LinkedList<Integer>();
    /*
    *
    * Add the number to memory
    * 
    */
    public void push(int number) {

        list.push(number);

    }

    public int pop(){

        return list.pop();

    }
    
    public boolean isEmpty(){

        return list.isEmpty();
    }

    public void clear(){

            list.clear();
    }
    

    public LinkedList<Integer> getMemory() {

        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); 

        for (Integer num : list) {
            sb.append(num); 
            sb.append("\n"); 
        }

        sb.append("---");
        return sb.toString(); 
    }

    public static void main(String[] args) {
        CalculatorMemory mem = new CalculatorMemory();

        mem.push(1); 
        mem.push(2);
        mem.push(3); 

        System.out.println("Empty? " + mem.isEmpty());


        System.out.println("\nAfter pushing 1, 2, 3:");

        System.out.println(mem.toString());

        int popped = mem.pop();
        System.out.println("\nPopped: " + popped);

        System.out.println("\nAfter one pop:");
        System.out.println(mem.toString());

        mem.clear(); 

        System.out.println("\nAfter clear():");
        System.out.println(mem.toString());
        System.out.println("Empty now? " + mem.isEmpty());

    }
}
