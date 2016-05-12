package au.edu.anu.cs.crunch.parser.logicalExps;

import au.edu.anu.cs.crunch.parser.abstracts.UnaryNonTerminal;

/**
 * Created by sina on 4/29/16.
 */
public class LogicalFactor extends UnaryNonTerminal {
    /* The non terminal with the highest priority.
    * Can be negation of a formula or a single atom */
    Boolean neg = false;

    public LogicalFactor(String expression) {
        super(expression);
        this.operators = new String[] {"~"};
    }

    public LogicalFactor(String expression, Boolean neg) {
        super(expression);
        this.neg = neg;
        this.operators = new String[] {"~"};
    }

    @Override
    public void decompose() {
        if (expression.startsWith("(")) {
            String new_expression = expression.substring(1, expression.length()-1);
            operand = new LogicalDisjunction(new_expression);
            super.decompose();
        }
        else if (expression.startsWith("~")){
            operand = new LogicalFactor(expression.substring(1, expression.length()), true);
            super.decompose();
        }
        else {
//            value = Boolean.valueOf(expression);
            if (expression.equals("true") || expression.equals("false"))
                value = Boolean.valueOf(expression);
            else {
                Exception e = new Exception("unparsable expression");
                try {
                    throw e;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public Boolean calculateValue() {
        if (!neg) {
            if (operand == null)
                return (Boolean)value;
            else
                return (Boolean) super.calculateValue();
        } else {
            if (operand == null)
                return !(Boolean)value;
            else
                return !(Boolean)super.calculateValue();
        }
    }
}
