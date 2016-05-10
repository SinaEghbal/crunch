package au.edu.anu.cs.crunch.parser.arithmeticExps;

import au.edu.anu.cs.crunch.parser.abstracts.BinaryNonTerminal;
import au.edu.anu.cs.crunch.parser.abstracts.Features;
import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;

/**
 * Created by sina on 4/27/16.
 */
public class Expression extends BinaryNonTerminal {
    /* This is the class we instantiate to evaluate the arithmetic expressions.
    * It starts with decomposing the expressions by the lowest priorority operators
    * (Here -,+)*/
    public Expression(String expression) {
        super(expression, Features.ARITHMETICOPERATORS);
        this.operators = new String[]{"+", "-"};
    }

    @Override
    public NonTerminal createNonTerminalLeft(String expression) {
        Term term = new Term(expression);
        term.decompose();
        return term;
    }

    @Override
    public NonTerminal createNonTerminalRight(String expression) {
        Expression exp = new Expression(expression);
        exp.decompose();
        return exp;
    }

    @Override
    public Float calculateValue() {
        if (operator== null)
            return (Float)this.left.calculateValue();
        else if (operator.equals("+"))
            return (Float)this.left.calculateValue() + (Float)this.right.calculateValue();
        else if (operator.equals("-"))
            return (Float)this.left.calculateValue() - (Float)this.right.calculateValue();
//        switch (operator) {
//            case "+":
//                return (Float)this.left.calculateValue() + (Float)this.right.calculateValue();
//            case "-":
//                return (Float)this.left.calculateValue() - (Float)this.right.calculateValue();
//        }
        return (float)0;
    }
}
