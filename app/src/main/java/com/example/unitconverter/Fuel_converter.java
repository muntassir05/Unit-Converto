package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Fuel_converter extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    EditText inputFuel;
    TextView resultText;
    Button convertButton;

    String[] units = {"km/L", "mpg", "L/100km"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fuel_converter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputFuel = findViewById(R.id.inputFuel);
        resultText = findViewById(R.id.resultText);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputFuel.getText().toString().trim();
            if (inputStr.isEmpty()) {
                resultText.setText("Please enter a value.");
                return;
            }

            double input = Double.parseDouble(inputStr);
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();
            double output = convertFuel(input, fromUnit, toUnit);
            resultText.setText("Result: " + output + " " + toUnit);
        });
    }

    private double convertFuel(double value, String from, String to) {
        // Convert input to km/L first
        if (from.equals("mpg")) {
            value *= 0.4251; // 1 mpg ≈ 0.4251 km/L
        } else if (from.equals("L/100km")) {
            value = 100 / value; // inverse
        }

        // Convert from km/L to target
        if (to.equals("mpg")) {
            value /= 0.4251;
        } else if (to.equals("L/100km")) {
            value = 100 / value;
        }

        return Math.round(value * 100.0) / 100.0; // Round to 2 decimals
    }
}
