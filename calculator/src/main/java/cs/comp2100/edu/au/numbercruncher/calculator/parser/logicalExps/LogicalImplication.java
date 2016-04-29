package cs.comp2100.edu.au.numbercruncher.calculator.parser.logicalExps;

import nonterminals.abstracts.Features;
import nonterminals.abstracts.BinaryNonTerminal;
import nonterminals.abstracts.NonTerminal;

/**
 * Created by sina on 4/29/16.
 */
public class LogicalImplication extends BinaryNonTerminal<Boolean> {
    public LogicalImplication(String expression) {
        super(expression, Features.LOGICALOPERATORS);
        this.operators = new String[] {">"};
    }

    @Override
    public NonTerminal createNonTerminalRight(String expression) {
        BinaryNonTerminal right = new LogicalImplication(expression);
        right.decompose();
        return right;
    }

    @Override
    public NonTerminal createNonTerminalLeft(String expression) {
        BinaryNonTerminal left = new LogicalDisjunction(expression);
        left.decompose();
        return left;
    }

    @Override
    public Boolean calculateValue() {
        if (right==null)
            return (Boolean) left.calculateValue();
        else
            return (!(boolean)left.calculateValue() || (boolean)right.calculateValue());
    }
}
