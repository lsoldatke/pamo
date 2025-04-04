package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Aplikacja zawierająca kalkulator BMI oraz kalkulator dziennego zapotrzebowania kalorycznego
 *
 * @author Łukasz Soldatke (lsoldatke)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView foodImageView = findViewById(R.id.foodImageView);
        foodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaloricDemandCalculatorActivity.class);
                startActivity(intent);
            }
        });
    }
}
