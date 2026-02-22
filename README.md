# Assignment - Text Calculator

For this assignment, you will be implementing a text-based calculator which takes input in postfix notation and displays the results of the computation. As always, read through the entire handout first before you start working on the assignment. **This is an individual assignment.**

## Learning Goals

* Write your own program from scratch
* Think about different ways of implementing stacks
* Practice dealing with error handling

## Postfix Notation

When performing mathematical operations (additions, subtractions, etc), the normal mathematical notation that we use is called "infix" notation: the mathematical operator is **in** between the operands (e.g., `3 - 5`). There is a different notation known as postfix notation where the operator comes after the operands. For example,

| Infix notation     | Postfix notation |
| ------------------ | ---------------- |
| `3 - 5`            | `3 5 -`          |
| `47 - (5 + 7) * 4` | `47 5 7 + 4 * -` |
| `3 + 5 * 7`        | `3 5 7 * +`      |

One of the key advantages of postfix notation is that we can do the calculation as we go. Compare this to infix notation where we have to wait until we see the entire expression before we can start doing any calculations (a higher precedence component might come later in the expression).

The main idea is to use a stack to keep track of the values we've seen so far but have not used. When data is added to a stack, it is added to the "top" and when we remove data, the first thing we remove is the item on the top. This is also known as "last in first out" (or LIFO), i.e., the most recent thing that was added will be the first thing to get removed.

Using a stack, we can do postfix computations as follows:

* If the next thing we read is a number, push it on the stack.
* If the next thing we read is a binary operator,
  1. remove the top two numbers from the stack,
  2. apply the operator, and then
  3. push the answer back on the stack.

Here is the computation of `47 5 7 + 4 * -` (where the top of the stack is on the right):

| Input | Resulting stack | Explanation                                   |
| ----- | --------------- | --------------------------------------------- |
| 47    | 47              | push 47                                       |
| 5     | 47, 5           | push 5                                        |
| 7     | 47, 5, 7        | push 7                                        |
| +     | 47, 12          | pop 7, pop 5, compute 5 + 7 = 12, push 12     |
| 4     | 47, 12, 4       | push 4                                        |
| *     | 47, 48          | pop 4, pop 12, compute 12 * 4 = 48, push 48   |
| -     | -1              | pop 48, pop 47, compute 47 - 48 = -1, push -1 |

Be careful about the ordering of the operands, i.e., when applying an operator the first thing off the stack is the right operand (in infix terminology) and the second thing off the stack is the left operand. This is why we get -1 and not 1 at the end.

## Text Postfix Calculator

Your program will prompt the user for input (either a number or an operator) and will either add the number to the stack directly or apply the operator and then add the result back to the stack. Here is an example execution of the program following the example above:

```text
Enter a number or operator: 47

Memory contents:
47
---

Enter a number or operator: 5

Memory contents:
5
47
---

Enter a number or operator: 7

Memory contents:
7
5
47
---

Enter a number or operator: +
Answer: 12

Memory contents:
12
47
---

Enter a number or operator: 4

Memory contents:
4
12
47
---

Enter a number or operator: *
Answer: 48

Memory contents:
48
47
---

Enter a number or operator: -
Answer: -1

Memory contents:
-1
---

Enter a number or operator:
```

## Requirements

For this assignment, we're giving you almost no starter code. However, we are giving you a *specification* for the classes that you need to write. All classes should be inside the `calculator` package. Don't forget to match the name of the file with the name of the class.

### High-level requirements

Your interactive calculator must meet the following requirements:

* Follow *exactly* the output shown in the example above.

* Keep going until the user enters `quit`

* Support +, -, *, and /, as well as the following two input strings:
  * `pop`: the answer shown should be the top element of the stack and the top item should be removed from the stack
  * `clear`: remove all items from the stack; no answer should be displayed

* Handle *all* errors. When an error occurs, the stack should not be changed. Your code should handle the following errors:

  * If trying to pass an invalid input (i.e. not a number or one of the operators described above), your code should catch an `IllegalArgumentException` and print the message *"Error: expected number or valid operator"*. The stack should remain unchanged.
  * If trying to divide by zero, your code should catch an `ArithmeticException` and print the message *"Error: divide by zero"*. The stack should remain unchanged.
  * If pop is called on an empty stack, you should print the message: *"Error: pop requires one argument"*.
  * If an operator is applied but not enough arguments are passed, you should print *"Error: operator requires two arguments"*. The stack should remain unchanged.

Here is an example showing the errors:

```text
Enter a number or operator: pop
Error: pop requires one argument

Memory contents:
---

Enter a number or operator: 3

Memory contents:
3
---

Enter a number or operator: pop
Answer: 3

Memory contents:
---

Enter a number or operator: 1

Memory contents:
1
---

Enter a number or operator: +
Error: operator requires two arguments

Memory contents:
1
---

Enter a number or operator: 0

Memory contents:
0
1
---

Enter a number or operator: /
Error: divide by zero

Memory contents:
0
1
---

Enter a number or operator: asdf
Error: expected number or valid operator

Memory contents:
0
1
---

Enter a number or operator: -
Answer: 1

Memory contents:
1
---

Enter a number or operator:
```

### Implementation requirements

#### `Operation`

This class represents a mathematical operation. It should have just a single static method: `performOperation(char op, int left, int right)`

The static method should perform the operation on the given operands and return the result. Valid operations are +, -, * and /. If the input is not one of these four operations, it should throw an `IllegalArgumentException`. This method should throw an `ArithmeticException` *if* we try and divide by zero.

#### `CalculatorMemory`

This class represents the memory of the calculator.

We recommend you use the built-in linked list, i.e., `java.util.LinkedList`, to represent memory.

This class should have the following methods:

* `public void push(int number)`: Add the number to memory. **Must run in O(1).**
* `public int pop()`: Return and remove the most recently pushed number. **Must run in O(1).**
* `public boolean isEmpty()`: Return whether the memory is empty.
* `public int size()`: Return how many numbers are in the memory.
* `public void clear()`: Remove everything from the memory.
* `public LinkedList<Integer> getMemory()`: Return the memory object.
* `public String toString()`: Return a `String` version of the numbers stored. The `String` should look like the memory contents output below.

For example, pushing 1, 2, 3 (in this order) would result to

```text
3
2
1
---
```

You can use `String` concatenation as always, but you might also want to look at the [`StringBuilder`](https://docs.oracle.com/javase/9/docs/api/java/lang/StringBuilder.html) class which is recommended when concatenating strings in a loop.

#### `Calculator`

This is the class where we actually interact with the user and run everything. It needs to use an instance of the `CalculatorMemory` class and have the two public methods below and *at least* one private helper method (you pick the name and signature).

* A zero parameter constructor
* `public void run()`: This runs the calculator, which will prompt the user for numbers, do the calculations, etc.

## One Path to Implementation

We strongly encourage you to work incrementally. Here's one path:

1. Write the `Operation` class.
2. Test the `Operation` class by writing a `main` method in the `Operation` class.
3. Write the `CalculatorMemory` class.
4. Test the `CalculatorMemory` class by writing a `main`.
5. Get a basic version of the `Calculator` running without error checking and with just the math operators.
6. Add the pop and clear operations.
7. Add in all of the error checking.

## Grading

| Criterion                                                                                                                  | Points |
| :------------------------------------------------------------------------------------------------------------------------- | :----- |
| `performOperation` supports +,-,*,/                                                                                        | 2      |
| `performOperation` handles illegal arguments and arithmetic exception                                                      | 1      |
| `CalculatorMemory` `push`, `pop`, `isEmpty`, `clear`, `size` work properly                                                 | 5      |
| `CalculatorMemory` `toString` matches specification                                                                        | 1      |
| `Calculator` output matches specification                                                                                  | 1      |
| Appropriately handle errors and show message                                                                               | 3      |
| Appropriate comments + JavaDoc + [Style and formatting](https://github.com/pomonacs622026sp/code/blob/main/style_guide.md) | 1      |
| Total                                                                                                                      | 14     |

## Submitting your work

Double-check that your work is indeed pushed in GitHub and that it appears on Gradescope! It is your responsibility to ensure that you do so before the deadline.
