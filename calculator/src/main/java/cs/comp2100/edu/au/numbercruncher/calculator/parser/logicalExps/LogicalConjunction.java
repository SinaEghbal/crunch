package cs.comp2100.edu.au.numbercruncher.calculator.parser.logicalExps;

import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.Features;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.BinaryNonTerminal;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.NonTerminal;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.abstracts.UnaryNonTerminal;

/**
 * Created by sina on 4/29/16.
 */
public class LogicalConjunction extends BinaryNonTerminal<Boolean> {

    public LogicalConjunction(String expression) {
        super(expression, Features.LOGICALOPERATORS);
        this.operators = new String[] {"&"};
    }

    @Override
    public NonTerminal createNonTerminalRight(String expression) {
        BinaryNonTerminal right = new LogicalConjunction(expression);
        right.decompose();
        return right;
    }

    @Override
    public NonTerminal createNonTerminalLeft(String expression) {
        BinaryNonTerminal left = new LogicalExclusiveDisjunction(expression);
        left.decompose();
        return left;
    }

    @Override
    public Boolean calculateValue() {
        if (right==null)
            return (Boolean) left.calculateValue();
        else
            return (boolean)left.calculateValue() && (boolean)right.calculateValue();
    }
}
