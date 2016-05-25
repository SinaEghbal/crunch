package au.edu.anu.cs.crunch.parser.logicalExps;

import au.edu.anu.cs.crunch.parser.abstracts.BinaryNonTerminal;
import au.edu.anu.cs.crunch.parser.abstracts.Features;
import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;

/**
 * Created by sina on 4/29/16.
 */
public class LogicalConjunction extends BinaryNonTerminal<Boolean> {
    /* Implements logical conjunction. i.e. true and false = false */

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
            return (Boolean)left.calculateValue() && (Boolean) right.calculateValue();
    }
}
