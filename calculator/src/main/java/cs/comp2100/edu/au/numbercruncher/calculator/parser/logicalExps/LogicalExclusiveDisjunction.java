package cs.comp2100.edu.au.numbercruncher.calculator.parser.logicalExps;

import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.BinaryNonTerminal;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.Features;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.NonTerminal;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.UnaryNonTerminal;

/**
 * Created by sina on 4/29/16.
 */
public class LogicalExclusiveDisjunction extends BinaryNonTerminal<Boolean> {
    public LogicalExclusiveDisjunction(String expression) {
        super(expression, Features.LOGICALOPERATORS);
        this.operators = new String[] {"^"};
    }

    @Override
    protected NonTerminal createNonTerminalRight(String expression) {
        BinaryNonTerminal right = new LogicalExclusiveDisjunction(expression);
        right.decompose();
        return right;
    }

    @Override
    protected NonTerminal createNonTerminalLeft(String expression) {
        UnaryNonTerminal left = new LogicalFactor(expression);
        left.decompose();
        return left;
    }

    @Override
    public Boolean calculateValue() {
        if (right==null)
            return (Boolean)left.calculateValue();
        else
            return (((Boolean)left.calculateValue()&&!(Boolean)right.calculateValue()) ||
                    (Boolean)right.calculateValue()&&!(Boolean)left.calculateValue());
    }
}
