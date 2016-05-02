package cs.comp2100.edu.au.numbercruncher.calculator;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

import cs.comp2100.edu.au.numbercruncher.calculator.parser.arithmeticExps.Expression;

/**
 * Created by b on 2/05/16.
 */
public class ParserTest extends ApplicationTestCase<Application> {
    public ParserTest(){super(Application.class);}

    @Test
    public void expressionTests() throws  Exception{
        Expression expression = new Expression("10 + 5");
        Float value = expression.calculateValue();
        assertTrue("Expression should evaluate to 15 not "+value.toString(), value == 15.0);
    }
}
