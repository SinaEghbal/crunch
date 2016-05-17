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
    }

    public void testDeleteAndGetExpression() {
        //Logic
        Cursor cursor = dao.getExpressions(CalculatorDB.Logic.TABLE_NAME);
        int before = cursor.getCount();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(CalculatorDB.Logic._ID));
            dao.deleteExpression(id, CalculatorDB.Logic.TABLE_NAME);
            cursor = dao.getExpressions(CalculatorDB.Logic.TABLE_NAME);
            int after = cursor.getCount();
            assertEquals(after, before - 1);
        }
    }
    public void testDeleteAndGetArithmeticExpression() {
        //Arithmetic
        Cursor cursor = dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME);
        int before = cursor.getCount();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(CalculatorDB.Arithmetic._ID));
            dao.deleteExpression(id, CalculatorDB.Arithmetic.TABLE_NAME);
            cursor = dao.getExpressions(CalculatorDB.Arithmetic.TABLE_NAME);
            int after = cursor.getCount();
            assertEquals(after, before-1);
        }
    }
}
