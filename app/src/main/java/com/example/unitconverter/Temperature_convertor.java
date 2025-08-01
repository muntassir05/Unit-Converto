package com.example.unitconverter;

import android.os.Bundle;
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

public class Temperature_convertor extends AppCompatActivity {

    Spinner fromUnit, toUnit;
    EditText inputValue;
    TextView resultView;
    Button convertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature_convertor);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Link UI
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        inputValue = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.resultView);
        convertBtn = findViewById(R.id.convertBtn);

        // Spinner data
        String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        // Button click logic
        convertBtn.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString().trim();
            if (inputStr.isEmpty()) {
                resultView.setText("Please enter a value.");
                return;
            }

            double input = Double.parseDouble(inputStr);
            String from = fromUnit.getSelectedItem().toString();
            String to = toUnit.getSelectedItem().toString();

            double result = convertTemperature(input, from, to);
            resultView.setText(String.format("%.2f %s", result, to));
        });
    }

    private double convertTemperature(double value, String from, String to) {
        // Convert from source to Celsius
        switch (from) {
            case "Fahrenheit":
                value = (value - 32) * 5 / 9;
                break;
            case "Kelvin":
                value = value - 273.15;
                break;
        }

        // Convert from Celsius to target
        switch (to) {
            case "Fahrenheit":
                return (value * 9 / 5) + 32;
            case "Kelvin":
                return value + 273.15;
            default: // Celsius
                return value;
        }
    }
}
