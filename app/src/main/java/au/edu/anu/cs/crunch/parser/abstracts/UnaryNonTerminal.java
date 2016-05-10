package au.edu.anu.cs.crunch.parser.abstracts;

public abstract class UnaryNonTerminal<T> extends NonTerminal {

    public NonTerminal operand;

    public UnaryNonTerminal(String expression) {
        super(expression);
    }

    @Override
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