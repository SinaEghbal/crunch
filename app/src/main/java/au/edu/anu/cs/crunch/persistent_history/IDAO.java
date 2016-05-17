package au.edu.anu.cs.crunch.persistent_history;

import android.database.Cursor;

/**
 * Created by sina on 18/05/16.
 */
public interface IDAO {
    public void insertExpression(String exp, String table);
    public Cursor getExpressions(String table);
    public void deleteExpression(String id, String table);
}
