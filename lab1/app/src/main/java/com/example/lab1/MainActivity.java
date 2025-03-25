package com.example.lab1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1.enums.BmiLevel;

import java.text.NumberFormat;

/**
 * Prosta aplikacja kalkulator BMI - Pobiera od użytkownika wzrost (w centymetrach) oraz wagę
 * (w kilogramach) i oblicza na ich podstawie wskaźnik BMI (Body Mass Index) oraz wyświetla
 * informację o kategorii w zależności od zakresu w jakim znajduje się wynik BMI.
 *
 * @author Łukasz Soldatke (lsoldatke)
 */
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

        weightTextView = findViewById(R.id.weightTextView);
        heightTextView = findViewById(R.id.heightTextView);
        bmiTextView = findViewById(R.id.bmiTextView);
        bmiLevelTextView = findViewById(R.id.bmiLevelTextView);

        EditText weightEditText =
                findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        EditText heightEditText =
                findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);
    }

    /**
     * Oblicza wskaźnik BMI na podstawie wprowadzonych danych i wyświetla go w odpowiednim polu.
     */
    private void calculateBMI() {
        double heightMeters = height / 100;
        double bmi = weight / (heightMeters * heightMeters);

        bmiTextView.setText(numberFormat.format(bmi));
        interpretBMI(bmi);
    }

    /**
     * Interpretuje wartość wskaźnika BMI - przypisuje odpowiednią kategorię i wyświetla ją w
     * odpowiednim polu.
     *
     * @param bmi Wartość BMI
     */
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

    /**
     * Reaguje na zmiany tekstu w polu tekstowym dla wagi. Konwertuje wprowadzany tekst na liczbę i
     * wyświetla ją w odpowiednim formacie, a w przypadku błędu konwersji przywraca wartość wagi i
     * pola jej odpowiadającego do wartości domyślnej.
     */
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

    /**
     * Reaguje na zmiany tekstu w polu tekstowym dla wzrostu. Konwertuje wprowadzany tekst na
     * liczbę i wyświetla ją w odpowiednim formacie, a w przypadku błędu konwersji przywraca
     * wartość wzrostu i pola jemu odpowiadającego do wartości domyślnej.
     */
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
