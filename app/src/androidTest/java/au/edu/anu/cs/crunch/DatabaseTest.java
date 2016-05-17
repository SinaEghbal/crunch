package au.edu.anu.cs.crunch;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import au.edu.anu.cs.crunch.persistent_history.CalculatorDB;
import au.edu.anu.cs.crunch.persistent_history.DAO;

/**
 * Created by u5844485 on 18/05/16.
 */
public class DatabaseTest extends AndroidTestCase {
    DAO dao;

    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext mockActivity = new RenamingDelegatingContext(getContext(), "dbTest");
        dao = new DAO(mockActivity);
        dao.dropDB();
    }

    public void tearDown() {
        dao.dropDB();
    }

    public void testInsertAndGetLogicalExpression() {
        //Logic
        int before = dao.getExpressions(CalculatorDB.Logic.TABLE_NAME).getCount();
        dao.insertExpression("a&b", CalculatorDB.Logic.TABLE_NAME);
        int after = dao.getExpressions(CalculatorDB.Logic.TABLE_NAME).getCount();
        assertEquals(after, before + 1);
        //test that the expression exists in the db
        Cursor cursor = dao.getExpressions(CalculatorDB.Logic.TABLE_NAME);
        boolean exists = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(CalculatorDB.EXPRESSION)).equals("a&b"))
                exists = true;
        }
        assertTrue(exists);
    }
    public void testInsertAndGetArithmeticExpression() {
        //Arithmetic
        int before = dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME).getCount();
        dao.insertExpression("1+2", CalculatorDB.Arithmetic.TABLE_NAME);
        dao.insertExpression("Sin(90)", CalculatorDB.Arithmetic.TABLE_NAME);
        dao.insertExpression("Exp(12)", CalculatorDB.Arithmetic.TABLE_NAME);
        dao.insertExpression("lne(fac(12))", CalculatorDB.Arithmetic.TABLE_NAME);
        dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME);
        int after = dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME).getCount();
        assertNotSame(after,before);

        //test that the expression exists in the db
        Cursor cursor = dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME);
        String[] tested = new String[] {"1+2", "Sin(90)", "Exp(12), lne(fac(12))"};
        boolean exists = false;
        for (String current: tested) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(CalculatorDB.EXPRESSION)).equals(current))
                    exists = true;
            }
            assertTrue(exists);
        }
    }

    public void testDeleteAndGetExpression() {
        //Logic
        Cursor cursor = dao.getExpressions(CalculatorDB.Logic.TABLE_NAME);
        int before = cursor.getCount();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(CalculatorDB.EXP_ID));
            String exp = cursor.getString(cursor.getColumnIndex(CalculatorDB.EXPRESSION));
            dao.deleteExpression(id, CalculatorDB.Logic.TABLE_NAME);
            cursor = dao.getExpressions(CalculatorDB.Logic.TABLE_NAME);
            int after = cursor.getCount();
            assertEquals(after, before - 1);
            // Makes sure the string does not exist in the db anymore
            boolean exists = false;
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(CalculatorDB.EXPRESSION)).equals(exp)) {
                    exists = true;
                }
                assertEquals(exists, false);
            }
        }
    }
    public void testDeleteAndGetArithmeticExpression() {
        //Arithmetic
        Cursor cursor = dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME);
        int before = cursor.getCount();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(CalculatorDB.Arithmetic._ID));
            String exp = cursor.getString(cursor.getColumnIndex(CalculatorDB.EXPRESSION));
            dao.deleteExpression(id, CalculatorDB.Arithmetic.TABLE_NAME);
            cursor = dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME);
            int after = cursor.getCount();
            assertEquals(after, before-1);
            // Again, makes sure the particular string that we deleted does not exist in the db
            boolean exists = false;
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(CalculatorDB.EXPRESSION)).equals(exp)) {
                    exists = true;
                }
                assertEquals(exists, false);
            }
        }
    }
}
