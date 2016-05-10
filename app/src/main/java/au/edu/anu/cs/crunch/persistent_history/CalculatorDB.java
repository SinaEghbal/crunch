package au.edu.anu.cs.crunch.persistent_history;

import android.provider.BaseColumns;

/**
 * Created by sina on 5/3/16.
 */
public final class CalculatorDB {
    public CalculatorDB() {}

    public static final String EXP_ID = "_id";
    public static final String EXPRESSION = "exp";

    public static abstract class Arithmetic implements BaseColumns {
        public static final String TABLE_NAME = "arithmetics";
    }

    public static abstract class Logic implements BaseColumns {
        public static final String TABLE_NAME = "logic";
//        public static final String EXP_ID = "_id";
//        public static final String EXPRESSION = "exp";
    }

}
