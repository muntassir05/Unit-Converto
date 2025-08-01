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

public class Weight_converter extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    EditText inputWeight;
    TextView resultText;
    Button convertButton;

    String[] units = {"Kilogram", "Gram", "Pound"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_converter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputWeight = findViewById(R.id.inputWeight);
        resultText = findViewById(R.id.resultText);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputWeight.getText().toString().trim();
            if (inputStr.isEmpty()) {
                resultText.setText("Please enter a value.");
                return;
            }

            double input = Double.parseDouble(inputStr);
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();
            double output = convertWeight(input, fromUnit, toUnit);
            resultText.setText("Result: " + output + " " + toUnit);
        });
    }

    private double convertWeight(double value, String from, String to) {
        // Convert to kilograms first
        if (from.equals("Gram")) {
            value /= 1000;
        } else if (from.equals("Pound")) {
            value /= 2.20462;
        }

        // Convert from kilograms to target
        if (to.equals("Gram")) {
            value *= 1000;
        } else if (to.equals("Pound")) {
            value *= 2.20462;
        }

        return value;
    }
}
