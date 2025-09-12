package edu.iastate.netid.pocketcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    /**
     * The instance of the calculator model for use by this controller.
     */
    private final CalculationStream mCalculationStream = new CalculationStream();

    /*
     * The instance of the calculator display TextView. You can use this to update the calculator display.
     */
    private TextView mCalculatorDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalculatorDisplay = findViewById(R.id.CalculatorDisplay);
    }


    public void onEqualClicked(View view) {
        try {
            mCalculationStream.calculateResult();
        } finally {
            updateCalculatorDisplay();
        }
    }

    public void onNumberClicked(View view) {
        if (view instanceof Button) {
            Button button = (Button) view;
            String buttonText = button.getText().toString();

            switch (buttonText) {
                case "0":
                    mCalculationStream.inputDigit(CalculationStream.Digit.ZERO);
                    break;
                case "1":
                    mCalculationStream.inputDigit(CalculationStream.Digit.ONE);
                    break;
                case "2":
                    mCalculationStream.inputDigit(CalculationStream.Digit.TWO);
                    break;
                case "3":
                    mCalculationStream.inputDigit(CalculationStream.Digit.THREE);
                    break;
                case "4":
                    mCalculationStream.inputDigit(CalculationStream.Digit.FOUR);
                    break;
                case "5":
                    mCalculationStream.inputDigit(CalculationStream.Digit.FIVE);
                    break;
                case "6":
                    mCalculationStream.inputDigit(CalculationStream.Digit.SIX);
                    break;
                case "7":
                    mCalculationStream.inputDigit(CalculationStream.Digit.SEVEN);
                    break;
                case "8":
                    mCalculationStream.inputDigit(CalculationStream.Digit.EIGHT);
                    break;
                case "9":
                    mCalculationStream.inputDigit(CalculationStream.Digit.NINE);
                    break;
                case ".":
                    mCalculationStream.inputDigit(CalculationStream.Digit.DECIMAL);
                    break;
            }
        }
        updateCalculatorDisplay();
    }

    public void onOperationClicked(View view) {
        if (view instanceof Button) {
            Button button = (Button) view;
            String buttonText = button.getText().toString();
            switch (buttonText) {
                case "+":
                    mCalculationStream.inputOperation(CalculationStream.Operation.ADD);
                    break;
                case "-":
                    mCalculationStream.inputOperation(CalculationStream.Operation.SUBTRACT);
                    break;
                case "*":
                    mCalculationStream.inputOperation(CalculationStream.Operation.MULTIPLY);
                    break;
                case "/":
                    mCalculationStream.inputOperation(CalculationStream.Operation.DIVIDE);
                    break;
                case "^":
                    mCalculationStream.inputOperation(CalculationStream.Operation.EXPONENT);
                    break;
                case "sqrt":
                    mCalculationStream.inputOperation(CalculationStream.Operation.SQRT);
                    mCalculationStream.calculateResult();
                    break;
                case "%":
                    mCalculationStream.inputOperation(CalculationStream.Operation.MOD);
                    break;
                case "Clear":
                    mCalculationStream.clear();
                    break;
            }
        }
        updateCalculatorDisplay();
    }
    /**
     * Call this method after every button press to update the text display of your calculator.
     */
    public void updateCalculatorDisplay() {
        String value = getString(R.string.empty);
        try {
            value = Double.toString(mCalculationStream.getCurrentOperand());
        } catch(NumberFormatException e) {
            value = getString(R.string.error);
        } finally {
            DecimalFormat scientificFormat = new DecimalFormat("0.#########E0");
            DecimalFormat generalFormat = new DecimalFormat("0.#########");
            if (value.length() > 10){
                String formattedValue = scientificFormat.format(Double.parseDouble(value));
                mCalculatorDisplay.setText(formattedValue);
            }else{
                String formattedValue = generalFormat.format(Double.parseDouble(value));
                mCalculatorDisplay.setText(formattedValue);
            }
        }
    }
}
