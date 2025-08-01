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

public class Length_converter extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    EditText inputLength;
    TextView resultText;
    Button convertButton;

    String[] units = {"Meter", "Kilometer", "Centimeter"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_length_converter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputLength = findViewById(R.id.inputLength);
        resultText = findViewById(R.id.resultText);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputLength.getText().toString().trim();
            if (inputStr.isEmpty()) {
                resultText.setText("Please enter a value.");
                return;
            }

            double input = Double.parseDouble(inputStr);
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();
            double output = convertLength(input, fromUnit, toUnit);
            resultText.setText("Result: " + output + " " + toUnit);
        });
    }

    private double convertLength(double value, String from, String to) {
        // Convert input to meters
        if (from.equals("Kilometer")) {
            value *= 1000;
        } else if (from.equals("Centimeter")) {
            value /= 100;
        }

        // Convert meters to target
        if (to.equals("Kilometer")) {
            value /= 1000;
        } else if (to.equals("Centimeter")) {
            value *= 100;
        }

        return value;
    }
}
