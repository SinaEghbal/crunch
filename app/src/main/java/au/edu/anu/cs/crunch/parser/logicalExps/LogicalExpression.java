package au.edu.anu.cs.crunch.parser.logicalExps;

import au.edu.anu.cs.crunch.parser.abstracts.Features;
import au.edu.anu.cs.crunch.parser.abstracts.BinaryNonTerminal;
import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;

/**
 * Created by sina on 4/29/16.
 */
public class LogicalExpression extends BinaryNonTerminal<Boolean> {
    /* Lowest priority, bidirectional implication. true <-> true = true
    * a <-> b means (~a or b) and (~b or a)*/
    public LogicalExpression(String expression) {
        super(expression, Features.LOGICALOPERATORS);
        this.operators = new String[] {"="};
        this.decompose();
    }

    @Override
    public NonTerminal createNonTerminalRight(String expression) {
        BinaryNonTerminal right = new LogicalExpression(expression);
        right.decompose();
        return right;
    }

    @Override
    public NonTerminal createNonTerminalLeft(String expression) {
        BinaryNonTerminal left = new LogicalImplication(expression);
        left.decompose();
        return left;
    }

    @Override
    public Boolean calculateValue() {
        if (right==null)
            return (Boolean) left.calculateValue();
        else
            return (!(Boolean)left.calculateValue() || (Boolean)right.calculateValue()) &&
        (!(Boolean)right.calculateValue() || (Boolean)left.calculateValue());
    }
}
