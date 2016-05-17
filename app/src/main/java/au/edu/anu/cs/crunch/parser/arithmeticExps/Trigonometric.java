package au.edu.anu.cs.crunch.parser.arithmeticExps;

import au.edu.anu.cs.crunch.MathFacade;
import au.edu.anu.cs.crunch.parser.abstracts.UnaryNonTerminal;

/**
 * Created by sina on 4/29/16.
 */

public class Trigonometric extends UnaryNonTerminal<Float> {
//    Expression
    /*A unary non terminal for the trigonometric functions, Sin, Cos, etc.*/
    boolean degree;
    public Trigonometric(String expression, boolean degree) {
        super(expression);
        this.degree = degree;
    }

    @Override
    public void decompose() {
        operand = new Expression(expression.substring(4, expression.length()-1), degree);
        super.decompose();
    }

    @Override
    public Float calculateValue() {
        float val;
//        if (degree)
//            val = (float) Math.toRadians((Float)operand.calculateValue());
//        else
//            val = (Float)operand.calculateValue();
        if (expression.startsWith("cos")) {
            return Float.valueOf((float) MathFacade.cos((float)operand.calculateValue(), degree));
        } else if (expression.startsWith("sin")) {
            return Float.valueOf((float) MathFacade.sin((float)operand.calculateValue(), degree));
        } else if (expression.startsWith("tan")) {
            return Float.valueOf((float) MathFacade.tan((float)operand.calculateValue(), degree));
        } else if (expression.startsWith("sqr")) {
            return Float.valueOf((float) Math.sqrt((Float)operand.calculateValue()));
        } else if (expression.startsWith("rec")) {
            return Float.valueOf((float) (1.0/(Float)operand.calculateValue()));
        } else if (expression.startsWith("lne")) {
            return Float.valueOf((float) Math.log((Float) operand.calculateValue()));
        } else if (expression.startsWith("log")) {
            return Float.valueOf((float) Math.log10((Float) operand.calculateValue()));
        } else if (expression.startsWith("exp")) {
            return Float.valueOf((float) Math.exp((Float) operand.calculateValue()));
        } else if (expression.startsWith("fac")) {
            float n = (Float) operand.calculateValue();
            return Float.valueOf((float) (Math.sqrt(2* Math.PI * n)
                                    * Math.pow((n/Math.exp(1.0)),n)));
        }
        return Float.valueOf((float)-1.0);
    }
}
