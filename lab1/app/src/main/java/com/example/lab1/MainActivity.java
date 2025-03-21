package com.example.lab1;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextWatcher;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lab1.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final NumberFormat numberFormat =
            NumberFormat.getInstance();

    private float weight = 0.0f;
    private float height = 0.0f;
    private TextView weightTextView;
    private TextView heightTextView;
    private TextView bmiTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightTextView = (TextView) findViewById(R.id.weightTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        bmiTextView = (TextView) findViewById(R.id.bmiTextView);

        weightTextView.setText(numberFormat.format(0));
        heightTextView.setText(numberFormat.format(0));
        bmiTextView.setText(numberFormat.format(0));

        EditText weightEditText =
                (EditText) findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        EditText heightEditText =
                (EditText) findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);
    }

    private void calculate() {
        float bmi = weight / (height * height);

        bmiTextView.setText(numberFormat.format(bmi));
    }

    private final TextWatcher weightEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try {
                weight = Float.parseFloat(s.toString()) / 100.0f;
                weightTextView.setText(numberFormat.format(weight));
            }
            catch (NumberFormatException e) {
                weightTextView.setText("");
                weight = 0.0f;
            }

            calculate();
        }

    private final TextWatcher weightEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            try {
                weight = Float.parseFloat(s.toString()) / 100.0f;
                weightTextView.setText(numberFormat.format(weight));
            }
            catch (NumberFormatException e) {
                weightTextView.setText("");
                weight = 0.0f;
            }

            calculate();
        }

        private final TextWatcher weightEditTextWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                try {
                    weight = Float.parseFloat(s.toString()) / 100.0f;
                    weightTextView.setText(numberFormat.format(weight));
                }
                catch (NumberFormatException e) {
                    weightTextView.setText("");
                    weight = 0.0f;
                }

                calculate();
            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}