package au.edu.anu.cs.crunch.parser.abstracts;

public abstract class UnaryNonTerminal<T> extends NonTerminal {
    /* An abstract implementation of NonTerminal
    * This class will be extended for implementing unary operators. */

    public NonTerminal operand;

    public UnaryNonTerminal(String expression) {
        super(expression);
    }

    @Override
    /* This method is used to calculate the value of unary non terminals*/
    public T calculateValue() {
            return (T) operand.calculateValue();
    }

    @Override
    public void decompose() {
        operand.decompose();
    }

//    @Override
//    public String toString() {
//            return operand.toString();
//    }
}