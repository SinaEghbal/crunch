package au.edu.anu.cs.crunch.persistent_history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

/**
 * Created by sina on 5/3/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Calculator";
    public static final Random idGenerator = new Random();

    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = "INTEGER";

    public static final String CREATE_TABLE = "CREATE TABLE " + CalculatorDB.Arithmetic.TABLE_NAME +
            "(" + CalculatorDB.EXP_ID + " " + INT_TYPE +
            ", " + CalculatorDB.EXPRESSION + TEXT_TYPE + ");" +
            " CREATE TABLE "+ CalculatorDB.Logic.TABLE_NAME + "(" + CalculatorDB.EXP_ID + " " + INT_TYPE +
            ", " + CalculatorDB.EXPRESSION + TEXT_TYPE + ");";

    public static final String DROP_ENTRIES =
            "DROP TABLE IF EXISTS " + CalculatorDB.Arithmetic.TABLE_NAME +";" +
            "DROP TABLE IF EXISTS " + CalculatorDB.Logic.TABLE_NAME + ";";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_ENTRIES);
        onCreate(db);
    }

    public Cursor getExpressions(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + tableName, null);
        return res;
    }

    public void insertExpression(String expression, String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CalculatorDB.EXP_ID, idGenerator.nextInt(1000000));
        cv.put(CalculatorDB.EXPRESSION, expression);
        db.insert(tableName, null, cv);
    }
}
