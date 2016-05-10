package au.edu.anu.cs.crunch.parser.abstracts;

/**
 * Created by sina on 4/27/16.
 */

public abstract class NonTerminal<T> {
    /* This is an abstract class which non-terminals extend.*/
    protected String expression;
    protected String[] operators;
    protected String operator;
    protected T value;

    public abstract <T> T calculateValue();

    public NonTerminal(String expression) {
        expression = expression.replace(" ", "");
        this.expression = expression;
//        this.decompose();
    }

    public abstract void decompose();
//    public abstract String toString();
}
