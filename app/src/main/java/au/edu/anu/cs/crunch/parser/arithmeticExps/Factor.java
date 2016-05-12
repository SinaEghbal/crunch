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

    public Factor(String expression, boolean neg, boolean degree) {
        super(expression);
        if (neg)
            scalar = -1;
        decompose();
        this.degree = degree;
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
            operand = new Factor(expression.substring(1, expression.length()), true);
            super.decompose();
        } else if (expression.startsWith("sin(") || expression.startsWith("cos(") ||
                expression.startsWith("tan(") || expression.startsWith("sqr(") ||
                expression.startsWith("rec(") || expression.startsWith("ln(")
                || expression.startsWith("log(") || expression.startsWith("exp(")
                || expression.startsWith("fac(")){
            operand = new Trigonometric(expression, degree);
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
