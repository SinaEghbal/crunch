package au.edu.anu.cs.crunch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import au.edu.anu.cs.crunch.parser.arithmeticExps.Expression;
import au.edu.anu.cs.crunch.persistent_history.CalculatorDB;
import au.edu.anu.cs.crunch.persistent_history.DAO;
import au.edu.anu.cs.crunch.persistent_history.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TooManyListenersException;

/**
 * Created by sina on 5/10/16.
 * Boris Repasky, Sina Eghbal
 * This code is published under GNU Public License 2.0
 */

public class HomeActivity extends Activity {

    public static final int RECOG = 1;
    private static final int ACTIVITY = 0;

    TextView expTextView;
    DAO calculatorHelper;
    Spinner history;
    String tableName;
    boolean answer;
    Bundle savedInstance;
    boolean degrees = false;
    int viewID = R.layout.activity_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstance = savedInstanceState;

        int viewTmp = getIntent().getIntExtra("VIEW", 0);
        if (viewTmp != 0){
            viewID=viewTmp;
        }
        getIntent().putExtra("VIEW", 0);

        // reload image on orientation change
        if (savedInstanceState != null) {
            viewID = savedInstanceState.getInt("VIEW");
            setContentView(viewID);
            TextView textBox = (TextView) findViewById(R.id.txtViewScreen);
            textBox.setText((String) savedInstanceState.get("TEXT"));
            viewID = savedInstanceState.getInt("VIEW");
            degrees = savedInstanceState.getBoolean("DEGREES");
        } else {
            setContentView(viewID);
        }

        if(degrees && viewID==R.layout.activity_trigonometric)
            ((Button)findViewById(R.id.btn_rad_or_deg)).setText("deg");
        if(expTextView != null) {
            TextView textBox = (TextView) findViewById(R.id.txtViewScreen);
            textBox.setText(expTextView.getText());
        }

        expTextView = (TextView)findViewById(R.id.txtViewScreen);
        calculatorHelper = new DAO(this);
        history = (Spinner) findViewById(R.id.spinner_history);
        tableName = CalculatorDB.Logic.TABLE_NAME;
        loadSpinner();

        // set backgrounds of non number buttons
        Button[] colouredButtons = {(Button) findViewById(R.id.btn_left_parens),
                (Button) findViewById(R.id.btn_right_parens), (Button) findViewById(R.id.btn_modulus),
                (Button) findViewById(R.id.btn_sign), (Button) findViewById(R.id.btn_division),
                (Button) findViewById(R.id.btn_times), (Button) findViewById(R.id.btn_minus),
                (Button) findViewById(R.id.btn_plus), (Button) findViewById(R.id.btn_result),
                (Button) findViewById(R.id.btn_clear), (Button) findViewById(R.id.btn_delete)};

        for (Button b :colouredButtons) {
            if(b.getId() == R.id.btn_clear || b.getId() == R.id.btn_delete){
                b.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
            } else{
                b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
            }
        }


        /* For deleting a particular record from the expression history
         * db query tested and works, however due to limitation of spinner does not function and
         * therefore it was commented out. */
//        history.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), (int) id, Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });
//
        //OnClick Listener for the spinner
        history.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* When an item is selected in the spinner, print it on the screen. */
                if (position != 0) {
                    /* Not when you load the spinner though. */
                    String expression = history.getSelectedItem().toString();
                    expTextView.setText(expression);
                    answer = true;
                }
            }

            @Override
            /* When nothing is selected. I think this cannot happen unless the adapter is empty. ;)*/
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        //For arithmetic expression. Should be changed for the logical expressions.
        answer = false;

    }

    protected void loadSpinner() {
        /* loads the history spinner.
        * adds an empty item which will be the default selected item. */
        Cursor previousExpressions = calculatorHelper.getExpressions(tableName);
        List<String> expressionList = new ArrayList<String>();
        /* Basically a placeholder when we populate the spinner. */
        expressionList.add("");
        while (previousExpressions.moveToNext()) {
            expressionList.add(previousExpressions.getString(1));
        }
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expressionList);
        historyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        history.setAdapter(historyAdapter);
    }

    //Create the top menu, where you can switch panels
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trig:
                // User wants to switch to trigonometric panel
                viewID = R.layout.activity_trigonometric;
                if(savedInstance != null){
                    savedInstance.putInt("VIEW", viewID);
                    savedInstance.putBoolean("DEGREES", degrees);
                }
                onCreate(savedInstance);
                return true;

            case R.id.basic:
                // User wants to switch to basic panel
                viewID = R.layout.activity_home;
                if(savedInstance != null){
                    savedInstance.putInt("VIEW", viewID);
                    savedInstance.putBoolean("DEGREES", degrees);
                }
                onCreate(savedInstance);
                return true;

            case R.id.logic:
                // User wants to switch to logic panel
                Intent intent = new Intent(getApplicationContext(), LogicActivity.class);
                startActivityForResult(intent, ACTIVITY);
                finish();
                return true;
            default:
                //error unknown action
                return super.onOptionsItemSelected(item);
        }
    }

    //Saves state so that it can be reconstructed on screen orientation change
    @Override
    public void onSaveInstanceState(Bundle state) {
        TextView textBox = (TextView) findViewById(R.id.txtViewScreen);
        state.putString("TEXT", textBox.getText().toString());
        state.putInt("VIEW", viewID);
        state.putBoolean("DEGREES", degrees);
    }

    public void keypadOnClick(View view) {
        /*Implementation for the keypad buttons.
        * implements the functionality of the keypad keys based on the key id.*/
        answer = false;
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
                no = "×";
                break;
            case R.id.btn_division:
                no = "/";
                break;
            case R.id.btn_left_parens:
                no = "(";
                break;
            case R.id.btn_right_parens:
                no = ")";
                break;
            case R.id.btn_sin:
                no = "sin(";
                break;
            case R.id.btn_cos:
                no = "cos(";
                break;
            case R.id.btn_tan:
                no = "tan(";
                break;
            case R.id.btn_ln:
                no = "lne(";
                break;
            case R.id.btn_log:
                no = "log(";
                break;
            case R.id.btn_exp:
                no = "exp(";
                break;
            case R.id.btn_sqr:
                no = "sqr(";
                break;
            case R.id.btn_fac:
                no = "fac(";
                break;
            case R.id.btn_rec:
                no = "rec(";
                break;
            case R.id.btn_rad_or_deg:
                Button b = (Button) findViewById(R.id.btn_rad_or_deg);
                if (degrees){
                    b.setText("rad");
                    degrees = false;
                } else{
                    b.setText("deg");
                    degrees = true;
                }
                break;
        }
        expTextView.setText(expTextView.getText().toString()+no);
    }

    public void operationOnClick(View view) {
        /*Implementation for the operation buttons such as =, c, <-.*/
        switch (view.getId()) {
            /*switches the button id and runs the functionality of the key accordingly*/
            case R.id.btn_clear:
                expTextView.setText("");
                break;
            case R.id.btn_delete:
                if (expTextView.getText().length() > 0) {
                    if (!answer)
                        expTextView.setText(expTextView.getText().toString().substring(0,
                            expTextView.getText().toString().length()-1));
                    else
                        expTextView.setText("");
                    answer = false;
                }
                break;
            case R.id.btn_sign:
                if (expTextView.getText().toString().length() > 0) {
                    if (expTextView.getText().toString().charAt(0) == '-') {
                        expTextView.setText(expTextView.getText().toString().substring(1));
                    } else
                        expTextView.setText("-" + expTextView.getText().toString());
                } else {
                    expTextView.setText("-");
                }
                answer = false;
                break;
            case R.id.btn_result:
                try {
                    /*We'll evaluate the string on our expTextView and calculate the result if it is
                    * parsable*/
                    String expString = expTextView.getText().toString();
                    Expression exp = new Expression(expString, degrees);
                    if (!answer) {
                        /* if it's not an answer write it to the db. */
                        calculatorHelper.insertExpression(expString, tableName);
                    }
                    loadSpinner();
                    expTextView.setText(trimInteger(String.valueOf(exp.calculateValue())));
                    answer = true;
                } catch (Exception e) {
                    /*If the string is not parsable, show a toast*/
                    Toast.makeText(getBaseContext(), "Non-parsable expression", Toast.LENGTH_LONG).show();
                    // For debugging purposes
//                     throw e;
                }
                break;
        }
    }

    public boolean voiceRecog(View view){
        //User wants to use voice to text
        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(voiceIntent, RECOG);
        return true;
    }

    //Listener for voice recognition intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOG && resultCode == RESULT_OK){
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            // catch expression if not parsable
            String expStr="";
            try{
                expStr = text.get(0).toString();
                expStr = expStr.replaceAll("divided by", "/");
                expStr = expStr.replaceAll("mod[a-z]*", "%");
                Expression expression = new Expression(expStr, degrees);
                TextView txtViewScreen = (TextView) findViewById(R.id.txtViewScreen);
                txtViewScreen.setText(expStr);
            }catch (Exception e){
//                throw e;
                Toast.makeText(getBaseContext(),expStr + " Not parsable", Toast.LENGTH_LONG).show();
            }
        } else if(requestCode == ACTIVITY) {
            this.finish();
        }
    }

    public String trimInteger(String expression) {
        /*Since we are using float, Strings will always end with .0
        We'll trim out string if it can be shown as an integer*/
        if (expression.endsWith(".0"))
            return expression.substring(0,expression.length()-2);
        return expression;
    }
}
