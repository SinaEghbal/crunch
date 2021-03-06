package au.edu.anu.cs.crunch.parser.arithmeticExps;

import au.edu.anu.cs.crunch.parser.abstracts.UnaryNonTerminal;

/**
 * Created by sina on 4/28/16.
 */
public class Factor extends UnaryNonTerminal<Float> {
    /* Only unary non-terminal for arithmetic expressions.
    * Factor can be in form of
    * (exp), Number, -exp */
    float scalar = 1;
    boolean degree;

    public Factor(String expression, boolean neg, boolean degree) {
        super(expression);
        if (neg)
            scalar = -1;
        this.degree = degree;
        decompose();
    }

    @Override
    public void decompose() {
        /* Decomposes the factor, if decomposable.*/
        if (expression.startsWith("(")) {
            String new_expression = expression.substring(1, expression.length()-1);
            operand = new Expression(new_expression, degree);
            super.decompose();
        }
        else if (expression.startsWith("-")){
            operand = new Factor(expression.substring(1, expression.length()), true, degree);
            super.decompose();
        } else if (expression.startsWith("sin(") || expression.startsWith("cos(") ||
                expression.startsWith("tan(") || expression.startsWith("sqr(") ||
                expression.startsWith("rec(") || expression.startsWith("lne(")
                || expression.startsWith("log(") || expression.startsWith("exp(")
                || expression.startsWith("fac(")){
            operand = new Trigonometric(expression, degree);
            super.decompose();
        }
        else {
            try {
                value = Float.valueOf(expression);
            } catch (Exception e) {
                Expression exp = new Expression(expression, degree);
                value = Float.valueOf(exp.calculateValue());
            }
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
