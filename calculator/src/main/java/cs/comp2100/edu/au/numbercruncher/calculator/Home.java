package cs.comp2100.edu.au.numbercruncher.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import cs.comp2100.edu.au.numbercruncher.calculator.parser.arithmeticExps.Expression;

public class Home extends Activity {
    TextView screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        screen = (TextView)findViewById(R.id.txtViewScreen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

                Expression exp = new Expression(screen.getText().toString());
                exp.decompose();
                screen.setText(String.valueOf(exp.calculateValue()));
        }
    }
}
