package com.example.lab2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.enums.Gender;
import com.example.lab2.enums.PhysicalActivityLevel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Prosty kalkulator zapotrzebowania kalorycznego - Pobiera od użytkownika wiek, wzrost (w centymetrach), wagę
 * (w kilogramach) oraz poziom aktywności fizycznej i oblicza na ich podstawie zapotrzebowanie kaloryczne według wzoru Benedicta-Harrisa.
 *
 * @author Łukasz Soldatke (lsoldatke)
 */
public class CaloricDemandCalculatorActivity extends AppCompatActivity {
    private static final NumberFormat numberFormat =
            NumberFormat.getNumberInstance();

    private int age;
    private double weight = 0.0;
    private double height = 0.0;
    private PhysicalActivityLevel physicalActivityLevel = PhysicalActivityLevel.MODERATE;
    private Gender gender;
    private TextView ageTextView;
    private TextView weightTextView;
    private TextView heightTextView;
    private TextView caloriesTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloric_demand_calculator);

        ageTextView = findViewById(R.id.ageTextView);
        weightTextView = findViewById(R.id.weightTextView);
        heightTextView = findViewById(R.id.heightTextView);
        caloriesTextView = findViewById(R.id.caloriesTextView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        EditText ageEditText =
                findViewById(R.id.ageEditText);
        ageEditText.addTextChangedListener(ageEditTextWatcher);

        EditText weightEditText =
                findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        EditText heightEditText =
                findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);

        Spinner physicalActivityLevelSpinner = findViewById(R.id.physicalActivityLevelSpinner);

        List<PhysicalActivityLevel> physicalActivityLevels = Arrays.asList(PhysicalActivityLevel.values());
        ArrayAdapter<PhysicalActivityLevel> adapterPhysicalActivityLevel = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, physicalActivityLevels);
        physicalActivityLevelSpinner.setAdapter(adapterPhysicalActivityLevel);
        physicalActivityLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                physicalActivityLevel = PhysicalActivityLevel.values()[i];
                calculateCaloricDemand();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner genderSpinner = findViewById(R.id.genderSpinner);

        List<Gender> genders = Arrays.asList(Gender.values());
        ArrayAdapter<Gender> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genders);
        genderSpinner.setAdapter(adapterGender);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = Gender.values()[i];
                calculateCaloricDemand();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Oblicza zapotrzebowanie kaloryczne na podstawie wprowadzonych danych i wyświetla je w odpowiednim polu.
     */
    private void calculateCaloricDemand() {
        double heightMeters = height / 100;
        double physicalActivityIndex = 1.6;
        double ppm = 0.0;
        double cpm = 0.0;

        switch (physicalActivityLevel) {
            case LACK_OF_ACTIVITY:
                physicalActivityIndex = 1.2;
                break;
            case LITTLE:
                physicalActivityIndex = 1.4;
                break;
            case MODERATE:
                physicalActivityIndex = 1.6;
                break;
            case HIGH:
                physicalActivityIndex = 1.75;
                break;
            case VERY_HIGH:
                physicalActivityIndex = 2.0;
                break;
            case PROFESSIONAL:
                physicalActivityIndex = 2.4;
                break;
        }

        if (gender == Gender.FEMALE) {
            ppm = 655.1 + (9.563 * weight) + (1.85 * heightMeters) - (4.676 * age);
        } else {
            ppm = 66.473 + (13.752 * weight) + (5.003 * heightMeters) - (6.775 * age);
        }

        cpm = ppm * physicalActivityIndex;
        caloriesTextView.setText(numberFormat.format(ppm));
    }

    /**
     * Reaguje na zmiany tekstu w polu tekstowym dla wieku. Konwertuje wprowadzany tekst na liczbę i
     * wyświetla ją w odpowiednim formacie, a w przypadku błędu konwersji przywraca wartość wieku i
     * pola jemu odpowiadającego do wartości domyślnej.
     */
    private final TextWatcher ageEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            try {
                age = Integer.parseInt(s.toString());
                ageTextView.setText(numberFormat.format(age));
            } catch (NumberFormatException e) {
                age = 0;
                ageTextView.setText("");
            }

            calculateCaloricDemand();
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

            calculateCaloricDemand();
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

            calculateCaloricDemand();
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
