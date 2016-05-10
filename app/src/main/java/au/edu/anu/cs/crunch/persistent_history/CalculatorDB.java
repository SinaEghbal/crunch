package au.edu.anu.cs.crunch.persistent_history;

import android.provider.BaseColumns;

/**
 * Created by sina on 5/3/16.
 */
public final class CalculatorDB {
    /* This class contains the data using which we create our DB tables
    * We'll have two tables to store the expressions. One for the logical expressions and and
    * the other one for storing the logical expression. */
    public CalculatorDB() {}

    public static final String EXP_ID = "_id";
    public static final String EXPRESSION = "exp";

    public static abstract class Arithmetic implements BaseColumns {
        public static final String TABLE_NAME = "arithmetic";
    }

    public static abstract class Logic implements BaseColumns {
        public static final String TABLE_NAME = "logic";
    }

}
