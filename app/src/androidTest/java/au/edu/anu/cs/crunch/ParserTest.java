package au.edu.anu.cs.crunch;

import android.app.Application;
import android.test.ApplicationTestCase;

import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;
import au.edu.anu.cs.crunch.parser.arithmeticExps.Expression;

import org.junit.Test;

/**
 * Created by b on 2/05/16.
 */
public class ParserTest extends ApplicationTestCase<Application> {
    public ParserTest(){super(Application.class);}

    @Test
    public void expressionAdditionTest() throws  Exception{
        // Tests of addition arithmetic
        NonTerminal expressionOne = new Expression("10+5");
        NonTerminal expressionTwo = new Expression("0+10");
        NonTerminal expressionThree = new Expression("100000+100000");
        expressionOne.decompose();
        expressionTwo.decompose();
        expressionThree.decompose();
        Float valueOne = (Float) expressionOne.calculateValue();
        Float valueTwo = (Float)expressionTwo.calculateValue();
        Float valueThree = (Float)expressionThree.calculateValue();
        assertTrue("Expression should evaluate to 15 not "+valueOne.toString(), valueOne == 15.0);
        assertTrue("Expression should evaluate to 10 not "+valueTwo.toString(), valueTwo == 10.0);
        assertTrue("Expression should evaluate to 20,000 not "+valueThree.toString(), valueThree == 200000.0);
    }
}
