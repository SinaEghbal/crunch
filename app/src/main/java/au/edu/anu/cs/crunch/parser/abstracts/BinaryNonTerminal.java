package au.edu.anu.cs.crunch.parser.abstracts;

//import import cs.comp2100.edu.au.numbercruncher.calculator.cs.comp2100.edu.au.crunch.calculator.parser.abstracts.NonTerminal;

import java.util.Arrays;

/**
 * Created by sina on 4/29/16.
 */
public abstract class BinaryNonTerminal<T> extends NonTerminal {
    /* This class extends NonTerminal and will be extended by all the binary operators
    * we will implement. */
    protected final String[] OPERATORS;// = new String[]{"+", "-", "*", "/", "^"};
    protected NonTerminal left;
    protected NonTerminal right;
    protected T value;

    public BinaryNonTerminal(String expression, String[] operators) {
        super(expression);
        this.OPERATORS = operators;
    }

    /* This is an abstract method. This will be a factory method which creates a non-terminal
    * based on the right non-terminal of a binary operator in the grammar. */
    protected abstract NonTerminal createNonTerminalRight(String expression);
    /* Like the above, A factory method which creates non-terminal specified for each binary
     * non terminal on its left. */
    protected abstract NonTerminal createNonTerminalLeft(String expression);

    public void decompose() {
        /* This method decomposes a non-terminal.
        * This means that will first find the parentheses and consider them as a single expression
        * also given the operators of every b-non terminal, it will find them in the expression
        * and decomposes the non-terminal into two non-terminals. */
//        String expression = this.expression;
        int separator = -1;
        int index = 0;
        boolean found = false;
        for (String op : operators) {
            if (!found) {
                while (expression.indexOf(op, index) != -1) {
                    String part = expression.substring(0, expression.indexOf(op, index));
                    if ((countMatches(part , "(") == countMatches(part , ")"))
                            && (expression.indexOf(op, index)!=0 &&
                            !Arrays.asList(OPERATORS).contains(
                                    String.valueOf(expression.charAt(expression.indexOf(op, index)-1))))) {
                        //Set the separator
                        separator = expression.indexOf(op, index);
                        found = true;
                        break;
                    } else {
                        index = expression.indexOf(op, index)+1;
                    }
                }
            }
        }
        if (separator == -1) {
            this.left = createNonTerminalLeft(this.expression);
        } else {
            operator = String.valueOf(this.expression.charAt(separator));
            this.left = createNonTerminalLeft(this.expression.substring(0, separator));
            this.right = createNonTerminalRight(this.expression.substring(separator+1, this.expression.length()));

        }
        value = calculateValue();
    }

    /* This method will be called in the end of the decompose method to calculate the value of each non-terminal*/
    public abstract T calculateValue();

    //
    public int countMatches(String string, String subString) {
        /* Just count the matches of a substring in another string(1st string). */
        int lastIndex = 0;
        int count = 0;

        while (lastIndex >= 0) {
            lastIndex = string.indexOf(subString, lastIndex);
            if (lastIndex != -1){
                count++;
                lastIndex += subString.length();
            }
        }
        return count;
    }

//    @Override
//    public String toString() {
//        if (operator != null)
//            return left + " " + operator + " " + right;
//        else
//            return String.valueOf(right.value);
//    }
}
