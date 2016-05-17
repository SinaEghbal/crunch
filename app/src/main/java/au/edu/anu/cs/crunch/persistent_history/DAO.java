package au.edu.anu.cs.crunch.persistent_history;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sina on 18/05/16.
 */
public class DAO implements IDAO {
    DBHelper dbHelper;

    public DAO(Context activity) {
        dbHelper = new DBHelper(activity);
    }
    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public void dropDB() {
        dbHelper.onUpgrade(getWritableDatabase(), 0, 1);
    }

    @Override
    public void insertExpression(String exp, String table) {
        dbHelper.insertExpression(exp, table);
    }

    @Override
    public Cursor getExpressions(String table) {
        return dbHelper.getExpressions(table);
    }

    @Override
    public void deleteExpression(String id, String table) {
        dbHelper.deleteExpression(id, table);
    }

}
