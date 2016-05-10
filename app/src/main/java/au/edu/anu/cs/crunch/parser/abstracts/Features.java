package au.edu.anu.cs.crunch.parser.abstracts;

/**
 * Created by sina on 4/29/16.
 */
public class Features {
    /* Contains the operators for the different calculations.
    * Here arithmetic operators and logical operators.*/
    public static String[] ARITHMETICOPERATORS = new String[]{"+", "-", "*", "/", "^", "%", "Cos", "Sin"};
    public static String[] LOGICALOPERATORS = new String[] {"&", "|", "~", "=", ">", "^"};

//    This class was used to separate expressions from floating numbers. Is not used anymore

//    public static boolean isExpression(String expression, String[] operators){
//        for (String op: operators) {
//            if (expression.contains(op) && (!String.valueOf(expression.charAt(0)).equals(op)
//                    && !String.valueOf(expression.charAt(expression.length()-1)).equals(op))) {
//                return true;
//            }
//        }
//        return false;
//    }

}
