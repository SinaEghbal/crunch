package au.edu.anu.cs.crunch.parser.arithmeticExps;

import au.edu.anu.cs.crunch.parser.abstracts.UnaryNonTerminal;

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
            return Float.valueOf((float) Math.cos((Float)operand.calculateValue()));
        } else if (expression.startsWith("sin")) {
            return Float.valueOf((float) Math.sin((Float)operand.calculateValue()));
        } else if (expression.startsWith("tan")) {
            return Float.valueOf((float) Math.tan((Float)operand.calculateValue()));
        } else if (expression.startsWith("sqr")) {
            return Float.valueOf((float) Math.sqrt((Float)operand.calculateValue()));
        } else if (expression.startsWith("rec")) {
            return Float.valueOf((float) (1.0/(Float)operand.calculateValue()));
        }
        return Float.valueOf((float)-1.0);
    }
}
