package au.edu.anu.cs.crunch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import au.edu.anu.cs.crunch.parser.arithmeticExps.Expression;
import au.edu.anu.cs.crunch.persistent_history.CalculatorDB;
import au.edu.anu.cs.crunch.persistent_history.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sina on 5/10/16.
 * Boris Repasky, Sina Eghbal
 * This code is published under GNU Public License 2.0
 */

public class HomeActivity extends Activity {

    public static final int RECOG = 1;

    TextView screen;
    DBHelper calculatorHelper;
    Spinner history;
    String tableName;
    boolean answer;
    Bundle savedInstance;
    int viewID = R.layout.activity_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstance = savedInstanceState;
        // reload image on orientation change
        if (savedInstanceState != null) {
            viewID = savedInstanceState.getInt("VIEW");
            setContentView(viewID);
            TextView textBox = (TextView) findViewById(R.id.txtViewScreen);
            textBox.setText((String) savedInstanceState.get("TEXT"));
            viewID = savedInstanceState.getInt("VIEW");
        } else {
            setContentView(viewID);
        }
        screen = (TextView)findViewById(R.id.txtViewScreen);
        calculatorHelper = new DBHelper(this);
        history = (Spinner) findViewById(R.id.spinner_history);
        tableName = CalculatorDB.Arithmetic.TABLE_NAME;
        loadSpinner();

        // set backgrounds of non number buttons

        Button b = (Button) findViewById(R.id.btn_left_parens);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY); //FF6A6A6A
        b = (Button) findViewById(R.id.btn_right_parens);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_modulus);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_sign);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_division);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_times);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_minus);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_plus);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_result);
        b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_clear);
        b.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        b = (Button) findViewById(R.id.btn_delete);
        b.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

        //OnClick Listener for the spinner
        history.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* When an item is selected in the spinner, print it on the screen. */
                if (position != 0) {
                    /* Not when you load the spinner though. */
                    String expression = history.getSelectedItem().toString();
                    screen.setText(expression);
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
                }
                onCreate(savedInstance);
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
    }

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
                no = "x";
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
        }
        screen.setText(screen.getText().toString()+no);
    }

    public void operationOnClick(View view) {
        /*Implementation for the operation buttons such as =, c, <-.*/
        switch (view.getId()) {
            /*switches the button id and runs the functionality of the key accordingly*/
            case R.id.btn_clear:
                screen.setText("");
                break;
            case R.id.btn_delete:
                if (screen.getText().length() > 0) {
                    if (!answer)
                        screen.setText(screen.getText().toString().substring(0,
                            screen.getText().toString().length()-1));
                    else
                        screen.setText("");
                    answer = false;
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
                answer = false;
                break;
            case R.id.btn_result:
                try {
                    /*We'll evaluate the string on our screen and calculate the result if it is
                    * parsable*/
                    String expString = screen.getText().toString();
                    Expression exp = new Expression(expString);
                    if (!answer) {
                        /* if it's not an answer write it to the db. */
                        calculatorHelper.insertExpression(expString, tableName);
                    }
                    loadSpinner();
                    screen.setText(trimInteger(String.valueOf(exp.calculateValue())));
                    answer = true;
                } catch (Exception e) {
                    /*If the string is not parsable, show a toast*/
                    Toast.makeText(getBaseContext(), "Non-parsable expression", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public boolean voiceRecog(View view){
        //User wants to use voice to text
        // Approach taken from Lauren Darcey & Shane Conder
        // http://www.developer.com/ws/android/development-tools/
        // add-text-to-speech-and-speech-recognition-to-your-android-applications.html
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
            try{
                Expression expression = new Expression(text.get(0).toString());
                TextView txtViewScreen = (TextView) findViewById(R.id.txtViewScreen);
                txtViewScreen.setText(text.get(0).toString());
            }catch (Exception e){
                Toast.makeText(getBaseContext(), "Non-parsable expression", Toast.LENGTH_LONG).show();
            }
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
