package edu.miamioh.digennnj.calculator;

import java.util.Scanner;

public class InfixExpression {

    private String infix;

    /**
     * Constructs an InfixExpression object, that accepts a an infix expression as a String,
     * cleans the expression, and decides if the expression is valid. Throws an IllegalArguementException
     * if the String is invalid.
     *
     * @param infix
     * @throws Exception
     */
    public InfixExpression(String infix) /*throws Exception*/ {
        this.infix = infix;
        clean();

        if(!isValid()){

            throw new IllegalArgumentException("Expression isn't valid");

//			IllegalArgumentException e = new IllegalArgumentException();
//			System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the infix expression as a String, after the expression has been cleaned and validated
     */
    public String toString() {
        return infix;
    }

    /**
     * Helper method to isValid() that decides if there's an equal number of open and
     * closed parenthesis in Infix
     *
     * @return boolean
     */
    private boolean isBalanced() {

        ArrayStack<Character> stack = new ArrayStack<>();
        for(char c : infix.toCharArray()) {
            if(c == '(') {
                stack.push(c);
            } else if(c == ')') {
                if(stack.isEmpty()) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();

//		int balanced = 0;
//		for(char c : infix.toCharArray()) {
//			if(balanced < 0) {
//				return false;
//			}
//			if(c == '(') {
//				balanced++;
//			} else if(c == ')') {
//				balanced--;
//			}
//		}
//		return balanced == 0;
    }

    /**
     * Decides if the Infix expression is valid to be computed, depends on isBalance() and validChars()
     *
     * @return boolean
     */
    private boolean isValid() {
        String checkInfix = infix;
        checkInfix = checkInfix.replace("(", "");
        checkInfix = checkInfix.replace(")", "");
        checkInfix = checkInfix.trim();
        Scanner expression = new Scanner(checkInfix);
        boolean intCheck = false, operatorCheck = false;

        if(!(checkInfix.charAt(0) > 47 && checkInfix.charAt(0) < 58)) {
            expression.close();
            return false;
        }

        if(validChars() && isBalanced()) {
            while(expression.hasNext()) {
                if(expression.hasNextInt()) {
                    if(!intCheck) {
                        intCheck = true;
                    } else {
                        expression.close();
                        return false;
                    }
                    expression.next();
                    operatorCheck = false;
                } else {
                    if(!operatorCheck) {
                        operatorCheck = true;
                    } else {
                        expression.close();
                        return false;
                    }
                    intCheck = false;
                    expression.next();
                }
            }
        } else {
            expression.close();
            return false;
        }
        expression.close();
        return true;
    }

    /**
     * "Cleans" the infix expression by adding spaces where non whitespace chars are next to each other,
     * excluding parenthesis, and removes extra spacing between characters.
     */
    private void clean() {
        infix = infix.trim();
        Scanner expression = new Scanner(infix);

        for(char c : infix.toCharArray()) {
            switch(c) {
                case '^':
                    infix = infix.replace(""+c, " " + c + " ");
                    break;
                case '*':
                    infix = infix.replace(""+c, " " + c + " ");
                    break;
                case '/':
                    infix = infix.replace(""+c, " " + c + " ");
                    break;
                case '%':
                    infix = infix.replace(""+c, " " + c + " ");
                    break;
                case '+':
                    infix = infix.replace(""+c, " " + c + " ");
                    break;
                case '-':
                    infix = infix.replace(""+c, " " + c + " ");
                    break;
                default: break;
            }


        }

        while(!infix.equals(infix.replace("  ", " "))) {
            infix = infix.replace("  ", " ");
        }

        expression.close();

    }

    /**
     * Returns a String of the infix, in postfix form
     *
     * @return String postfix
     */
    public String getPostfixExpression() {
        //Works how I want, we just need to skip of the part that's in parenthesis that's already been put in post fix.
        ArrayStack<Character> stack = new ArrayStack<>();
        String postfix = "";


        //Run through each char in infix
        for(char c : infix.toCharArray()) {

            //If char is a number, add it to postfix
            if((c > 47 && c < 58) || c == ' ') {
                postfix += c;
                //If it is not a number or a whitespace, its an operator
            } else if(c == '('){
                stack.push(c);
            } else if(c == ')'){
                while(!(stack.peek() == '(')) {
                    postfix += " " + stack.pop();
                }
                stack.pop();
            } else {
                //Check for empty stack, if empty push operator
                if(stack.isEmpty()) {
                    stack.push(c);
                    //If not empty, check priority of operator to top operator on stack
                } else if(getPriority(c) > getPriority(stack.peek()) || getPriority(c) == 3) {
                    //Push if operator has higher priority than operator on stack
                    stack.push(c);
                    //If operator has lower priority
                } else {
                    //Pop operators to postfix until, current operator has a higher priority, or stack is empty
                    while(!stack.isEmpty() && (getPriority(c) <= getPriority(stack.peek()))) {
                        postfix += stack.pop() + " ";
                    }
                    //Push operator to stack, no matter outcome of loop
                    stack.push(c);
                }
            }
        }
        //At end of infix, pop remaining operators to postfix
        while(!stack.isEmpty()) {
            postfix += " " + stack.pop();
        }
        while(!postfix.equals(postfix.replace("  ", " "))){
            postfix = postfix.replace("  ", " ");
        }
        return postfix.trim();


    }

    /**
     * Evaluates a valid expression in postfix form and returns the answer. Will truncate any result while computing.
     *
     * @return int
     */
    public int evaluate() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        Scanner postfix = new Scanner(getPostfixExpression());
        int operandOne, operandTwo, result = 0;
        String operator;
        while(postfix.hasNext()) {
            if(postfix.hasNextInt()) {
                stack.push(postfix.nextInt());
            } else {
                operandTwo = stack.pop();
                operandOne = stack.pop();
                operator = postfix.next();
                switch(operator) {
                    case "+":
                        result = operandOne + operandTwo;
                        break;
                    case "-":
                        result = operandOne - operandTwo;
                        break;
                    case "*":
                        result = operandOne * operandTwo;
                        break;
                    case "/":
                        result = operandOne / operandTwo;
                        break;
                    case "%":
                        result = operandOne % operandTwo;
                        break;
                    case "^":
                        result = (int) Math.pow(operandOne, operandTwo);
                        break;

                    default: break;
                }
                stack.push(result);
            }
        }
        postfix.close();
        return stack.peek();
    }

    /**
     * Helper method for getPostfixExpression to determine priority of operators
     *
     * @param c
     * @return int
     */
    private int getPriority(char c) {
        if(c == '+' || c == '-') {
            return 1;
        } else if(c == '*' || c == '/' || c == '%') {
            return 2;
        } else if(c == '^') {
            return 3;
        }
        //if the operator is a parenthesis
        return 0;
    }

    /**
     * Helper method for isValid() checking for any char that isn't an operator, number or whitespace
     *
     * @return boolean
     */
    private boolean validChars() {
        for(char c : infix.toCharArray()) {
            //A little long but checks if the char is equal to any number, operator, or a whitespace
            //and returns the opposite boolean.
            if(!((c == ' ') || (c > 47 && c < 58) || (c == '(' ) || (c == ')') || (c == '*') || (c == '/') || (c == '+') || (c == '-') || (c == '^') || (c =='%'))) {
                return false;
            }
        }
        return true;
    }

}

