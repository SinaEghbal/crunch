package cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts;

import nonterminals.abstracts.NonTerminal;

import java.util.Arrays;

/**
 * Created by sina on 4/29/16.
 */
public abstract class BinaryNonTerminal<T> extends NonTerminal {
    protected final String[] OPERATORS;// = new String[]{"+", "-", "*", "/", "^"};
    protected NonTerminal left;
    protected NonTerminal right;
    protected T value;

    public BinaryNonTerminal(String expression, String[] operators) {
        super(expression);
        this.OPERATORS = operators;
    }

    protected abstract NonTerminal createNonTerminalRight(String expression);
    protected abstract NonTerminal createNonTerminalLeft(String expression);

    public abstract T calculateValue();

    public void decompose() {
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
//
    public int countMatches(String string, String subString) {
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
