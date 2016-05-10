package au.edu.anu.cs.crunch.parser.arithmeticExps;

import au.edu.anu.cs.crunch.parser.abstracts.BinaryNonTerminal;
import au.edu.anu.cs.crunch.parser.abstracts.Features;
import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;

/**
 * Created by sina on 4/28/16.
 */
public class ExponentialExp extends BinaryNonTerminal<Float> {
    /* Non terminal with second highest priority.
    * For exponential expressions. */
    public ExponentialExp(String expression) {
        super(expression, Features.ARITHMETICOPERATORS);
        this.operators = new String[]{"^"};
    }

    @Override
    public NonTerminal createNonTerminalLeft(String expression) {
        Factor factor = new Factor(expression);
        factor.decompose();
        return factor;
    }

    @Override
    public NonTerminal createNonTerminalRight(String expression) {
        ExponentialExp exp = new ExponentialExp(expression);
        exp.decompose();
        return exp;
    }

    @Override
    public Float calculateValue() {
        if (operator== null)
            return (Float) this.left.calculateValue();
        else if (operator.equals("^"))
            return (float) Math.pow((Float)this.left.calculateValue(), (Float)this.right.calculateValue());
//        switch (operator) {
//            case "^":
//                return (float) Math.pow((Float)this.left.calculateValue(), (Float)this.right.calculateValue());
//        }
        return null;
    }
}
