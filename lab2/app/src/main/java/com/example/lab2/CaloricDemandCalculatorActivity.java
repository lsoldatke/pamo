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

    private double physicalActivityIndex = 1.6; // Współczynnik aktywności fizycznej - domyślnie 1.6 dla średniej aktywności
    private double ppm = 0.0; // Podstawowa przemiana materii
    private double cpm = 0.0; // Całkowita przemiana materii (z uwzględnieniem poziomu aktywności fizycznej)
    private int age;
    private double weight = 0.0; // Waga (w kg)
    private int height = 0; // Wzrost (w cm)
    private PhysicalActivityLevel physicalActivityLevel = PhysicalActivityLevel.MODERATE;
    private Gender gender = Gender.MALE;
    private TextView ageTextView;
    private TextView weightTextView;
    private TextView heightTextView;
    Spinner physicalActivityLevelSpinner;
    Spinner genderSpinner;
    private TextView caloriesTextView;
    private TextView recipesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloric_demand_calculator);

        ageTextView = findViewById(R.id.ageTextView);
        weightTextView = findViewById(R.id.weightTextView);
        heightTextView = findViewById(R.id.heightTextView);
        physicalActivityLevelSpinner = findViewById(R.id.physicalActivityLevelSpinner);
        genderSpinner = findViewById(R.id.genderSpinner);
        caloriesTextView = findViewById(R.id.caloriesTextView);
        recipesTextView = findViewById(R.id.recipesTextView);

        EditText ageEditText =
                findViewById(R.id.ageEditText);
        ageEditText.addTextChangedListener(ageEditTextWatcher);

        EditText weightEditText =
                findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        EditText heightEditText =
                findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);

        List<PhysicalActivityLevel> physicalActivityLevels = Arrays.asList(PhysicalActivityLevel.values());
        ArrayAdapter<PhysicalActivityLevel> adapterPhysicalActivityLevel = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, physicalActivityLevels);
        physicalActivityLevelSpinner.setAdapter(adapterPhysicalActivityLevel);
        physicalActivityLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                physicalActivityLevel = PhysicalActivityLevel.values()[i];
                calculateCaloricDemand();
                suggestRecipes(cpm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<Gender> genders = Arrays.asList(Gender.values());
        ArrayAdapter<Gender> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genders);
        genderSpinner.setAdapter(adapterGender);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = Gender.values()[i];
                calculateCaloricDemand();
                suggestRecipes(cpm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Oblicza dzienne zapotrzebowanie kaloryczne na podstawie wprowadzonych danych i wyświetla je w odpowiednim polu.
     */
    private void calculateCaloricDemand() {
        switch (gender) {
            case MALE:
                ppm = 66.473 + (13.752 * weight) + (5.003 * height) - (6.775 * age);
                break;
            case FEMALE:
                ppm = 655.1 + (9.563 * weight) + (1.85 * height) - (4.676 * age);
                break;
        }

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

        cpm = ppm * physicalActivityIndex;
        caloriesTextView.setText(numberFormat.format(cpm));
    }

    /**
     * Proponuje dwa przepisy - potrawę na śniadanie i obiad w zależności od obliczonego
     * zapotrzebowania kalorycznego.
     *
     * @param cpm Obliczona wartość całkowitej przemiany materii
     */
    private void suggestRecipes(double cpm) {
        String breakfastRecipe;
        String lunchRecipe;

        if (cpm < 1800) {
            breakfastRecipe = getString(R.string.breakfast_omelette);
            lunchRecipe = getString(R.string.lunch_salad);
        } else if (cpm <= 2200) {
            breakfastRecipe = getString(R.string.breakfast_oatmeal);
            lunchRecipe = getString(R.string.lunch_chicken_rice);
        } else {
            breakfastRecipe = getString(R.string.breakfast_scrambled_eggs);
            lunchRecipe = getString(R.string.lunch_pasta_salmon);
        }

        String suggestedRecipes = "Breakfast:\n" + breakfastRecipe + "\n\nLunch:\n" + lunchRecipe;
        recipesTextView.setText(suggestedRecipes);
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
            suggestRecipes(cpm);
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
            suggestRecipes(cpm);
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
                height = Integer.parseInt(s.toString());
                heightTextView.setText(numberFormat.format(height));
            } catch (NumberFormatException e) {
                height = 0;
                heightTextView.setText("");
            }

            calculateCaloricDemand();
            suggestRecipes(cpm);
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
