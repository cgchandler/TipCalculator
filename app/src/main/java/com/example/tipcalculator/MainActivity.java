package com.example.tipcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.InputFilter;
import android.text.Spanned;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Each Tip Calculation Button
        initButtonTip15();
        initButtonTip18();
        initButtonTip20();

        // Limit data entry to 5 digits before and 2 digits after decimal
        EditText editBillAmount = findViewById(R.id.editTextBillAmount);
        editBillAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});

        // Clear the initial text for the calculation results
        TextView textTotalDue = findViewById(R.id.textTotalDue);
        textTotalDue.setText("");
    }

    private void initButtonTip15() {
        Button displayTipButton = findViewById(R.id.buttonTip15);
        displayTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double tipPercent = 0.15;
                calculateAndDisplayTip(tipPercent);
            }
        });
    }

    private void initButtonTip18() {
        Button displayTipButton = findViewById(R.id.buttonTip18);
        displayTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double tipPercent = 0.18;
                calculateAndDisplayTip(tipPercent);
            }
        });
    }

    private void initButtonTip20() {
        Button displayTipButton = findViewById(R.id.buttonTip20);
        displayTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double tipPercent = 0.20;
                calculateAndDisplayTip(tipPercent);
            }
        });
    }

    private void calculateAndDisplayTip(Double tipPercent) {
        // Get the customer's bill amount (default to zero if an empty string)
        EditText editBillAmount = findViewById(R.id.editTextBillAmount);
        Double billAmount = editBillAmount.getText().toString().isEmpty() ? 0: Double.parseDouble(editBillAmount.getText().toString());

        // Calculate the tip and total amounts
        TextView textTotalDue = findViewById(R.id.textTotalDue);
        Double tipAmount = billAmount * tipPercent;
        Double totalAmountDue = billAmount + tipAmount;

        // Display the results
        Resources res = getResources();
        String displayString = res.getString(R.string.total_due, tipAmount, totalAmountDue);
        textTotalDue.setText(displayString);
    }

    // Limit decimal input from: https://www.tutorialspoint.com/how-to-limit-decimal-places-in-android-edittext
    class DecimalDigitsInputFilter implements InputFilter {
        private Pattern mPattern;
        DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }
    }
}