package cs.comp2100.edu.au.numbercruncher.calculator.parser.arithmeticExps;

import nonterminals.abstracts.BinaryNonTerminal;
import nonterminals.abstracts.Features;
import nonterminals.abstracts.NonTerminal;

/**
 * Created by sina on 4/27/16.
 */
public class Term extends BinaryNonTerminal<Float> {

    public Term(String expression) {
        super(expression, Features.ARITHMETICOPERATORS);
        this.operators = new String[]{"*", "/", "%"};
    }

    @Override
    public NonTerminal createNonTerminalLeft(String expression) {
        ExponentialExp exp = new ExponentialExp(expression);
        exp.decompose();
        return exp;
    }

    @Override
    public NonTerminal createNonTerminalRight(String expression) {
        Term term = new Term(expression);
        term.decompose();
        return term;
    }

    @Override
    public Float calculateValue() {
        if (operator== null)
            return (float)this.left.calculateValue();
        switch (operator) {
            case "*":
                return (float)this.left.calculateValue() * (float)this.right.calculateValue();
            case "/":
                return (float)this.left.calculateValue() / (float)this.right.calculateValue();
            case "%":
                return (float) this.left.calculateValue() % (float)this.right.calculateValue();
        }
        return (float)0;
    }
}
