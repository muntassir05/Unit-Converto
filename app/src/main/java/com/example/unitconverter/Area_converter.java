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

public class Area_converter extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    EditText inputArea;
    TextView resultText;
    Button convertButton;

    String[] units = {"Square Meter", "Square Kilometer", "Square Foot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_area_converter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputArea = findViewById(R.id.inputArea);
        resultText = findViewById(R.id.resultText);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {
            String inputStr = inputArea.getText().toString().trim();
            if (inputStr.isEmpty()) {
                resultText.setText("Please enter a value.");
                return;
            }

            double input = Double.parseDouble(inputStr);
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();
            double output = convertArea(input, fromUnit, toUnit);
            resultText.setText("Result: " + output + " " + toUnit);
        });
    }

    private double convertArea(double value, String from, String to) {
        // Convert input to square meters first
        if (from.equals("Square Kilometer")) {
            value *= 1_000_000;
        } else if (from.equals("Square Foot")) {
            value *= 0.092903;
        }

        // Convert square meters to target unit
        if (to.equals("Square Kilometer")) {
            value /= 1_000_000;
        } else if (to.equals("Square Foot")) {
            value /= 0.092903;
        }

        return Math.round(value * 100.0) / 100.0; // Round to 2 decimals
    }
}
