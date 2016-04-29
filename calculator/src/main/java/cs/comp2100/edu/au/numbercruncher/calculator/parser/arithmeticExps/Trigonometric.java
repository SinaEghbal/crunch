package cs.comp2100.edu.au.numbercruncher.calculator.parser.arithmeticExps;

import nonterminals.abstracts.UnaryNonTerminal;

/**
 * Created by sina on 4/29/16.
 */

public class Trigonometric extends UnaryNonTerminal<Float> {
//    Expression
//    String function;
    public Trigonometric(String expression) {
        super(expression);
    }

    @Override
    public void decompose() {
        operand = new Expression(expression.substring(4, expression.length()-1));
        super.decompose();
    }

    @Override
    public Float calculateValue() {
        if (expression.startsWith("cos")) {
            return (float)Math.cos((float)operand.calculateValue());
        } else if (expression.startsWith("sin")) {
            return (float)Math.sin((float)operand.calculateValue());
        } else if (expression.startsWith("tan")) {
            return (float)Math.tan((float)operand.calculateValue());
        } else if (expression.startsWith("sqr")) {
            return (float)Math.sqrt((float)operand.calculateValue());
        } else if (expression.startsWith("rec")) {
            return (float)1.0/(float)operand.calculateValue();
        }
        return (float)-1.0;
    }
}
