package au.edu.anu.cs.crunch.parser.arithmeticExps;

import au.edu.anu.cs.crunch.parser.abstracts.BinaryNonTerminal;
import au.edu.anu.cs.crunch.parser.abstracts.Features;
import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;

/**
 * Created by sina on 4/27/16.
 */
public class Term extends BinaryNonTerminal<Float> {
    /* Term has the second lowest priority in our grammar, it implements x, /, and %(modulus)
    * operators */
    boolean degree;

    public Term(String expression, boolean degree) {
        super(expression, Features.ARITHMETICOPERATORS);
        this.operators = new String[]{"×", "/", "%"};
        this.degree = degree;
    }

    @Override
    public NonTerminal createNonTerminalLeft(String expression) {
        ExponentialExp exp = new ExponentialExp(expression, degree);
        exp.decompose();
        return exp;
    }

    @Override
    public NonTerminal createNonTerminalRight(String expression) {
        Term term = new Term(expression, degree);
        term.decompose();
        return term;
    }

    @Override
    public Float calculateValue() {
        if (operator==null)
            return (Float)this.left.calculateValue();
        else if (operator.equals("×"))
            return (Float)this.left.calculateValue() * (Float)this.right.calculateValue();
        else if (operator.equals("/"))
            return (Float)this.left.calculateValue() / (Float)this.right.calculateValue();
        else if (operator.equals("%")){
            return (Float) this.left.calculateValue() % (Float)this.right.calculateValue();
        }
        return (float)-1;
    }
}
