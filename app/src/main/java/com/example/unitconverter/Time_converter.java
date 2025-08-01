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

public class Time_converter extends AppCompatActivity {

    Spinner fromUnit, toUnit;
    EditText inputValue;
    TextView resultView;
    Button convertBtn;

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
        resultView = findViewById(R.id.resultView);
        convertBtn = findViewById(R.id.convertBtn);

        String[] units = {"Seconds", "Minutes", "Hours", "Days"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        convertBtn.setOnClickListener(v -> {
            String inputStr = inputValue.getText().toString().trim();
            if (inputStr.isEmpty()) {
                resultView.setText("Please enter a value.");
                return;
            }

            double input = Double.parseDouble(inputStr);
            String from = fromUnit.getSelectedItem().toString();
            String to = toUnit.getSelectedItem().toString();

            double result = convertTime(input, from, to);
            resultView.setText(String.format("%.2f %s", result, to));
        });
    }

    private double convertTime(double value, String from, String to) {
        // Convert from any unit to seconds
        switch (from) {
            case "Minutes": value *= 60; break;
            case "Hours": value *= 3600; break;
            case "Days": value *= 86400; break;
        }

        // Convert from seconds to target unit
        switch (to) {
            case "Minutes": return value / 60;
            case "Hours": return value / 3600;
            case "Days": return value / 86400;
            default: return value; // Seconds
        }
    }
}
