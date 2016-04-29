package cs.comp2100.edu.au.numbercruncher.calculator.parser.arithmeticExps;

import nonterminals.abstracts.UnaryNonTerminal;

/**
 * Created by sina on 4/28/16.
 */
public class Factor extends UnaryNonTerminal<Float> {
    float scalar = 1;
    public Factor(String expression) {
        super(expression);
        decompose();
    }

    public Factor(String expression, boolean neg) {
        super(expression);
        if (neg)
            scalar = -1;
        decompose();
    }

    @Override
    public void decompose() {
        if (expression.startsWith("(")) {
            String new_expression = expression.substring(1, expression.length()-1);
            operand = new Expression(new_expression);
            super.decompose();
        }
        else if (expression.startsWith("-")){
            operand = new Factor(expression.substring(1, expression.length()), true);
            super.decompose();
        } else if (expression.startsWith("sin(") || expression.startsWith("cos(") ||
                expression.startsWith("tan(") || expression.startsWith("sqr(") ||
                expression.startsWith("rec(")) {
            operand = new Trigonometric(expression);
            super.decompose();
        }
        else {
//            operand = new Expression(expression);
            value = Float.valueOf(expression);
        }
    }

    @Override
    public Float calculateValue() {
        if (operand == null)
            return (Float)value * scalar;
        else
            return super.calculateValue() * scalar;
    }
}
