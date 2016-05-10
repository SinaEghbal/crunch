package au.edu.anu.cs.crunch;

import android.app.Application;
import android.test.ApplicationTestCase;

import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;
import au.edu.anu.cs.crunch.parser.arithmeticExps.Expression;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by b on 2/05/16.
 */
public class ParserTest extends ApplicationTestCase<Application>{
    public ParserTest(){super(Application.class);}

    public void testParserDecomposeTest(){
        
    }

    public void testExpressionAdditionTest(){
        // Tests of addition arithmetic
        NonTerminal expressionOne = new Expression("10+5");
        NonTerminal expressionTwo = new Expression("0+10");
        NonTerminal expressionThree = new Expression("100000+100000");
        expressionOne.decompose();
        expressionTwo.decompose();
        expressionThree.decompose();
        Float valueOne = (Float) expressionOne.calculateValue();
        Float valueTwo = (Float) expressionTwo.calculateValue();
        Float valueThree = (Float) expressionThree.calculateValue();
        Assert.assertTrue("Expression should evaluate to 15 not " + valueOne.toString(), valueOne == 15.0);
        Assert.assertTrue("Expression should evaluate to 10 not " + valueTwo.toString(), valueTwo == 10.0);
        Assert.assertTrue("Expression should evaluate to 200,000 not " + valueThree.toString(), valueThree == 200000.0);
    }

    public void testExpressionSubtractionTest(){
        // Tests of subtraction arithmetic
        NonTerminal expressionOne = new Expression("10-5");
        NonTerminal expressionTwo = new Expression("0-10");
        NonTerminal expressionThree = new Expression("100000-100000");
        expressionOne.decompose();
        expressionTwo.decompose();
        expressionThree.decompose();
        Float valueOne = (Float) expressionOne.calculateValue();
        Float valueTwo = (Float) expressionTwo.calculateValue();
        Float valueThree = (Float) expressionThree.calculateValue();
        Assert.assertTrue("Expression should evaluate to 5 not " + valueOne.toString(), valueOne == 5.0);
        Assert.assertTrue("Expression should evaluate to -10 not " + valueTwo.toString(), valueTwo == -10.0);
        Assert.assertTrue("Expression should evaluate to 0 not " + valueThree.toString(), valueThree == 0.0);
    }

    public void testExpressionUnaryTest(){
        // Tests of unary parsing
        NonTerminal expressionOne = new Expression("-5");
        NonTerminal expressionTwo = new Expression("-10");
        NonTerminal expressionThree = new Expression("-100000");
        NonTerminal expressionFour = new Expression("-1 + 2");
        expressionOne.decompose();
        expressionTwo.decompose();
        expressionThree.decompose();
        expressionFour.decompose();
        Float valueOne = (Float) expressionOne.calculateValue();
        Float valueTwo = (Float) expressionTwo.calculateValue();
        Float valueThree = (Float) expressionThree.calculateValue();
        Float valueFour = (Float) expressionFour.calculateValue();
        Assert.assertTrue("Expression should evaluate to -5 not " + valueOne.toString(), valueOne == -5.0);
        Assert.assertTrue("Expression should evaluate to -10 not " + valueTwo.toString(), valueTwo == -10.0);
        Assert.assertTrue("Expression should evaluate to -100,000 not " + valueThree.toString(), valueThree == -100000.0);
        Assert.assertTrue("Expression should evaluate to 1 not " + valueFour.toString(), valueFour == 1.0);
    }
}
