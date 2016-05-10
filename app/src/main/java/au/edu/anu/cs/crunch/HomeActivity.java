package au.edu.anu.cs.crunch;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import au.edu.anu.cs.crunch.parser.abstracts.Features;
import au.edu.anu.cs.crunch.parser.arithmeticExps.Expression;
import au.edu.anu.cs.crunch.persistent_history.CalculatorDB;
import au.edu.anu.cs.crunch.persistent_history.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    TextView screen;
    DBHelper calculatorHelper;
    Spinner history;
    String tableName;
    boolean answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        screen = (TextView)findViewById(R.id.txtViewScreen);
        calculatorHelper = new DBHelper(this);
        history = (Spinner) findViewById(R.id.spinner_history);
        //OnClick Listener for the spinner
        history.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String expression = history.getSelectedItem().toString();
                screen.setText(expression);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
        //For arithmetic expression. Should be changed for the logical expressions.
        tableName = CalculatorDB.Arithmetic.TABLE_NAME;
        loadSpinner();
        answer = false;
    }

    protected void loadSpinner() {
        Cursor previousExpressions = calculatorHelper.getExpressions(tableName);
        List<String> expressionList = new ArrayList<String>();
        expressionList.add("");
        while (previousExpressions.moveToNext()) {
            expressionList.add(previousExpressions.getString(1));
        }
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expressionList);
        historyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        history.setAdapter(historyAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void buttonOnClick(View view) {
        String no = "";
        switch (view.getId()) {
            case R.id.btn_0:
                no = "0";
                break;
            case R.id.btn_1:
                no = "1";
                break;
            case R.id.btn_2:
                no = "2";
                break;
            case R.id.btn_3:
                no = "3";
                break;
            case R.id.btn_4:
                no = "4";
                break;
            case R.id.btn_5:
                no = "5";
                break;
            case R.id.btn_6:
                no = "6";
                break;
            case R.id.btn_7:
                no = "7";
                break;
            case R.id.btn_8:
                no = "8";
                break;
            case R.id.btn_9:
                no = "9";
                break;
            case R.id.btn_point:
                no = ".";
                break;
            case R.id.btn_minus:
                no = "-";
                break;
            case R.id.btn_modulus:
                no = "%";
                break;
            case R.id.btn_plus:
                no = "+";
                break;
            case R.id.btn_times:
                no = "*";
                break;
            case R.id.btn_division:
                no = "/";
                break;
        }
        screen.setText(screen.getText().toString()+no);
    }

    public void buttonOperation(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                screen.setText("");
                break;
            case R.id.btn_delete:
                if (screen.getText().length() > 0) {
                    screen.setText(screen.getText().toString().substring(0,
                            screen.getText().toString().length()-1));
                }
                break;
            case R.id.btn_sign:
                if (screen.getText().toString().length() > 0) {
                    if (screen.getText().toString().charAt(0) == '-') {
                        screen.setText(screen.getText().toString().substring(1));
                    } else
                        screen.setText("-" + screen.getText().toString());
                } else {
                    screen.setText("-");
                }
                break;
            case R.id.btn_history:
                //TODO -> implement a db for the history
                break;
            case R.id.btn_result:
                try {
                    String expString = screen.getText().toString();
                    Expression exp = new Expression(expString);
                    exp.decompose();
                    if (Features.isExpression(expString, Features.ARITHMETICOPERATORS))
                        calculatorHelper.insertExpression(expString, tableName);
                    loadSpinner();
                    screen.setText(String.valueOf(exp.calculateValue()));
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Unparsable Expression", Toast.LENGTH_LONG).show();
                }
        }
    }
}
