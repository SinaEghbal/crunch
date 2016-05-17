package au.edu.anu.cs.crunch;

import android.app.Application;
import android.test.ApplicationTestCase;

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
        NonTerminal expressionOne = new Expression("10×5+3/sin(1)", false);
        int a;
    }
}
