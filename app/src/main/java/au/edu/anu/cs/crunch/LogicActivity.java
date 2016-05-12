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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import au.edu.anu.cs.crunch.parser.abstracts.NonTerminal;
import au.edu.anu.cs.crunch.parser.logicalExps.LogicalExpression;
import au.edu.anu.cs.crunch.persistent_history.CalculatorDB;
import au.edu.anu.cs.crunch.persistent_history.DBHelper;

/**
 * Created by u5844485 on 12/05/16.
 */
public class LogicActivity extends Activity{

    public static final int RECOG = 1;

    TextView expTextView;
    DBHelper calculatorHelper;
    Spinner history;
    String tableName;
    boolean answer;
    Bundle savedInstance;
    int viewID = R.layout.activity_logic;

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


        expTextView = (TextView)findViewById(R.id.txtViewScreen);
        calculatorHelper = new DBHelper(this);
        history = (Spinner) findViewById(R.id.spinner_history);
        tableName = CalculatorDB.Arithmetic.TABLE_NAME;
        loadSpinner();

        // set backgrounds of non number buttons
        Button[] colouredButtons = {(Button) findViewById(R.id.btn_conjunct),
                (Button) findViewById(R.id.btn_disjunct), (Button) findViewById(R.id.btn_negation),
                (Button) findViewById(R.id.btn_implies), (Button) findViewById(R.id.btn_xor),
                (Button) findViewById(R.id.btn_result), (Button) findViewById(R.id.btn_left_parens),
                (Button) findViewById(R.id.btn_right_parens), (Button) findViewById(R.id.btn_clear),
                (Button) findViewById(R.id.btn_delete)};

        for (Button b :colouredButtons) {
            if(b.getId() == R.id.btn_clear || b.getId() == R.id.btn_delete){
                b.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
            } else{
                b.getBackground().setColorFilter(0xFF7A7A7A, PorterDuff.Mode.MULTIPLY);
            }
        }

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
        //For logic expression. Should be changed for the logical expressions.
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

    //Saves state so that it can be reconstructed on screen orientation change
    @Override
    public void onSaveInstanceState(Bundle state) {
        TextView textBox = (TextView) findViewById(R.id.txtViewScreen);
        state.putString("TEXT", textBox.getText().toString());
        state.putInt("VIEW", viewID);
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
                expStr = expStr.replaceAll("and", "∧");
                expStr = expStr.replaceAll("or", "∨");
                NonTerminal exp = new LogicalExpression(expStr);
                TextView txtViewScreen = (TextView) findViewById(R.id.txtViewScreen);
                txtViewScreen.setText(expStr);
            }catch (Exception e){
                Toast.makeText(getBaseContext(), expStr + " Not parsable", Toast.LENGTH_LONG).show();
            }
        }
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
            case R.id.btn_result:
                try {
                    /*We'll evaluate the string on our expTextView and calculate the result if it is
                    * parsable*/
                    String expString = expTextView.getText().toString();
                    NonTerminal exp = new LogicalExpression(unicodeConverter(expString));
                    if (!answer) {
                        /* if it's not an answer write it to the db. */
                        calculatorHelper.insertExpression(expString, tableName);
                    }
                    loadSpinner();
                    expTextView.setText(String.valueOf(exp.calculateValue()));
                    answer = true;
                } catch (Exception e) {
                    /*If the string is not parsable, show a toast*/
                    Toast.makeText(getBaseContext(), "Non-parsable expression", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void keypadOnClick(View view) {
        /*Implementation for the keypad buttons.
        * implements the functionality of the keypad keys based on the key id.*/
        answer = false;
        String no = "";
        switch (view.getId()) {
            case R.id.btn_true:
                no = "true";
                break;
            case R.id.btn_false:
                no = "false";
                break;
            case R.id.btn_conjunct:
                no = "\u2227";
                break;
            case R.id.btn_disjunct:
                no = "\u2228";
                break;
            case R.id.btn_negation:
                no = "\u00AC";
                break;
            case R.id.btn_implies:
                no = "\u2192";
                break;
            case R.id.btn_xor:
                no = "\u2295";
                break;
            case R.id.btn_left_parens:
                no = "(";
                break;
            case R.id.btn_right_parens:
                no = ")";
                break;
        }
        expTextView.setText(expTextView.getText().toString()+no);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trig:
                // User wants to switch to trigonometric panel
                Intent intentTrig = new Intent(getApplicationContext(), HomeActivity.class);
                int viewIDTrig = R.layout.activity_trigonometric;
                intentTrig.putExtra("VIEW", viewIDTrig);
                startActivity(intentTrig);
                return true;

            case R.id.basic:
                // User wants to switch to basic panel
                Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                int viewIDHome = R.layout.activity_home;
                intentHome.putExtra("VIEW", viewIDHome);
                startActivity(intentHome);
                return true;

            case R.id.logic:
                // User wants to switch to logic panel
                return true;
            default:
                //error unknown action
                return super.onOptionsItemSelected(item);
        }
    }

    public String unicodeConverter(String str){
        str = str.replaceAll("∧", "&");
        str = str.replaceAll("∨", "|");
        str = str.replaceAll("→", ">");
        str = str.replaceAll("⊕", "^");
        return str;
    }
}
