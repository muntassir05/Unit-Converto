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

public class Time_converter extends AppCompatActivity {

    Spinner fromUnit, toUnit;
    EditText inputValue;
    Button convertButton;
    TextView resultText;

    String[] units = {"Seconds", "Minutes", "Hours", "Days"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_converter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputValue.getText().toString();
                if (input.isEmpty()) {
                    resultText.setText("Please enter a value");
                    return;
                }

                double value = Double.parseDouble(input);
                String from = fromUnit.getSelectedItem().toString();
                String to = toUnit.getSelectedItem().toString();

                double result = convertTime(value, from, to);
                resultText.setText(String.format("%.4f %s", result, to));
            }
        });
    }

    private double convertTime(double value, String from, String to) {
        double seconds = 0;

        // Convert from source unit to seconds
        switch (from) {
            case "Seconds":
                seconds = value;
                break;
            case "Minutes":
                seconds = value * 60;
                break;
            case "Hours":
                seconds = value * 3600;
                break;
            case "Days":
                seconds = value * 86400;
                break;
        }

        // Convert from seconds to target unit
        switch (to) {
            case "Seconds":
                return seconds;
            case "Minutes":
                return seconds / 60;
            case "Hours":
                return seconds / 3600;
            case "Days":
                return seconds / 86400;
        }

        return 0;
    }
}
