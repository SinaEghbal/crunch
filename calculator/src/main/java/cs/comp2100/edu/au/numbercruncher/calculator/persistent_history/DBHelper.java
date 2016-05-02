package cs.comp2100.edu.au.numbercruncher.calculator.persistent_history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

/**
 * Created by sina on 5/3/16.
 */
public abstract class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Arithmetics.db";
    public static final Random idGenerator = new Random();

    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = "INTEGER";

    public static final String CREATE_TABLE = "CREATE TABLE " + calculatorDB.Arithmetic.TABLE_NAME +
            "(" + calculatorDB.Arithmetic.EXP_ID + " " + INT_TYPE +
            ", " + calculatorDB.Arithmetic.EXPRESSION + TEXT_TYPE + ");" +
            "(" + calculatorDB.Logic.EXP_ID + " " + INT_TYPE +
            ", " + calculatorDB.Logic.EXPRESSION + TEXT_TYPE + ");";

    public static final String DROP_ENTRIES =
            "DROP TABLE IF EXISTS " + calculatorDB.Arithmetic.TABLE_NAME +";" +
            "DROP TABLE IF EXISTS " + calculatorDB.Logic.TABLE_NAME + ";";

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

//    @Override
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }

    public Cursor getExpressions(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + tableName, null);
        return res;
    }

    public void insertExpression(String expression, String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(calculatorDB.Arithmetic.EXP_ID, idGenerator.nextInt(1000000));
        cv.put(calculatorDB.Arithmetic.EXPRESSION, expression);
        db.insert(tableName, null, cv);
    }
}
