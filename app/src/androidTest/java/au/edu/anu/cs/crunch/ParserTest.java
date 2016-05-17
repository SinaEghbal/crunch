package au.edu.anu.cs.crunch;

import android.app.Application;
import android.test.ApplicationTestCase;

import au.edu.anu.cs.crunch.parser.abstracts.BinaryNonTerminal;
import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;
import au.edu.anu.cs.crunch.parser.arithmeticExps.Expression;
import au.edu.anu.cs.crunch.parser.logicalExps.LogicalExpression;
import au.edu.anu.cs.crunch.MathFacade;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Boris on 2/05/16.
 */
public class ParserTest extends ApplicationTestCase<Application>{
    public ParserTest(){super(Application.class);}

    public void testParserDecompose(){
        BinaryNonTerminal expressionOne = new Expression("10×5+3/sin(1)", false);
        String test = expressionOne.getExpression();
        assertTrue("1"+test, test.equals("10×5+3/sin(1)"));
        test = expressionOne.getLeft().toString();
        assertTrue("2"+test, test.equals("10×5"));
        test = expressionOne.getRight().toString();
        assertTrue("3"+test, test.equals("3/sin(1)"));
        test = ((BinaryNonTerminal) expressionOne.getLeft().getValue()).getLeft().toString();
        assertTrue("4"+test, test.equals("10"));
        test = ((BinaryNonTerminal) expressionOne.getLeft().getValue()).getRight().toString();
        assertTrue("5"+test, test.equals("5"));
        test = ((BinaryNonTerminal) expressionOne.getRight().getValue()).getLeft().toString();
        assertTrue("6"+test, test.equals("3/sin(1)"));
        test = ((BinaryNonTerminal) expressionOne.getRight().getValue()).getRight().toString();
        assertFalse("7"+test, test.equals("3/sin(1)"));
    }
}
