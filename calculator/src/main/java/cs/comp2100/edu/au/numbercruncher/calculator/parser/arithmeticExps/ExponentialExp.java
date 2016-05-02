package cs.comp2100.edu.au.numbercruncher.calculator.parser.arithmeticExps;

import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.BinaryNonTerminal;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.Features;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.NonTerminal;

/**
 * Created by sina on 4/28/16.
 */
public class ExponentialExp extends BinaryNonTerminal<Float> {
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
