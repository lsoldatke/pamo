package com.example.lab1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1.enums.BmiLevel;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final NumberFormat numberFormat =
            NumberFormat.getNumberInstance();

    private double weight = 0.0;
    private double height = 0.0;
    private TextView weightTextView;
    private TextView heightTextView;
    private TextView bmiTextView;
    private TextView bmiLevelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightTextView = (TextView) findViewById(R.id.weightTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        bmiLevelTextView = (TextView) findViewById(R.id.bmiLevelTextView);

        EditText weightEditText =
                (EditText) findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        EditText heightEditText =
                (EditText) findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);
    }

    private void calculateBMI() {
        double heightMeters = height / 100;
        double bmi = weight / (heightMeters * heightMeters);

        bmiTextView.setText(numberFormat.format(bmi));
        interpretBMI(bmi);
    }

    private void interpretBMI(double bmi) {
        if (bmi < 18.5) {
            bmiLevelTextView.setText(BmiLevel.UNDERWEIGHT.toString());
        } else if (bmi >= 18.5 && bmi < 25) {
            bmiLevelTextView.setText(BmiLevel.NORMAL.toString());
        } else if (bmi >= 25 && bmi < 30) {
            bmiLevelTextView.setText(BmiLevel.OVERWEIGHT.toString());
        } else {
            bmiLevelTextView.setText(BmiLevel.OBESITY.toString());
        }
    }

    private final TextWatcher weightEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            try {
                weight = Double.parseDouble(s.toString());
                weightTextView.setText(numberFormat.format(weight));
            } catch (NumberFormatException e) {
                weight = 0.0;
                weightTextView.setText("");
            }

            calculateBMI();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };

    private final TextWatcher heightEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            try {
                height = Double.parseDouble(s.toString());
                heightTextView.setText(numberFormat.format(height));
            } catch (NumberFormatException e) {
                height = 0.0;
                heightTextView.setText("");
            }

            calculateBMI();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };
}
