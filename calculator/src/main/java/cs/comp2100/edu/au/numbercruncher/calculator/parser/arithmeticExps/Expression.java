package cs.comp2100.edu.au.numbercruncher.calculator.parser.arithmeticExps;

import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.BinaryNonTerminal;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.Features;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.NonTerminal;

/**
 * Created by sina on 4/27/16.
 */
public class Expression extends BinaryNonTerminal {
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
        switch (operator) {
            case "+":
                return (Float)this.left.calculateValue() + (Float)this.right.calculateValue();
            case "-":
                return (Float)this.left.calculateValue() - (Float)this.right.calculateValue();
        }
        return (float)0;
    }
}
