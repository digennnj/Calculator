package edu.miamioh.digennnj.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class Main extends AppCompatActivity implements View.OnClickListener {

    // Creates an array of buttons to store each button in the calculator UI
    private List<Button> buttons;
    // Creates an integer array of each button id that exists in the UI
    private static final int[] BUTTON_IDS = {
            R.id.btnZero,
            R.id.btnOne,
            R.id.btnTwo,
            R.id.btnThree,
            R.id.btnFour,
            R.id.btnFive,
            R.id.btnSix,
            R.id.btnSeven,
            R.id.btnEight,
            R.id.btnNine,
            R.id.btnAddition,
            R.id.btnSubtraction,
            R.id.btnDivisor,
            R.id.btnMultiplyer,
            R.id.btnEquals,
            R.id.btnDecimal,
            R.id.btnBackspace,
            R.id.btnModulo,
            R.id.btnExponent,
            R.id.btnClear,
            R.id.btnClosedParenthesis,
            R.id.btnOpenParenthesis,
            R.id.btnNegativeSign
    };

    private TextView expression;
    private int openParenCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes the buttons array using the length of the integer array
        buttons = new ArrayList<Button>(BUTTON_IDS.length);
        for(int id : BUTTON_IDS) {
            Button btn = (Button) findViewById(id);
            btn.setOnClickListener(this);
            buttons.add(btn);
        }

        expression = findViewById(R.id.txtExpression);
    }

    @Override
    public void onClick(View view) {
        try {
            if (expression.getText().equals("Enter an expression...")) expression.setText("");

            if (view.getId() == R.id.btnEquals) {
                InfixExpression infix = new InfixExpression(expression.getText().toString());
                double answer = 0;
                answer = infix.evaluate();
                expression.setText(String.valueOf(answer));
            } else if (view.getId() == R.id.btnBackspace) {
                String current = expression.getText().toString();
                // Remove the last character in the expression
                expression.setText(current.substring(0, current.length() - 1));
            } else if (view.getId() == R.id.btnClear) {
                expression.setText("");
            } else if (view.getId() == R.id.btnOpenParenthesis) {
                String current = expression.getText().toString();
                // If the last char is a number then append a mulitplication sign before the open parenthesis
                if (isNumber(current.charAt(current.length() - 1))) {
                    expression.append(view.getContentDescription());
                } else {
                    // Appends the content description of the button which holds the proper string to append to the expression
                    // according to the button that was pressed.
                    expression.append(view.getContentDescription());
                }
            } else {
                expression.append(view.getContentDescription());
            }

        } catch(Exception e){
            println("\n This is the error\n" + e.getMessage() + "\n\n");
        }

        }

    private boolean isOperator(char c) {

        if(c == '−' || c == '*' || c == '+' || c == '/' || c == '^' || c == '%') {
            return true;
        }
        return false;
    }

    private boolean isNumber(char c) {
        if(c > 47 && c < 58) {
            return true;
        }
        return false;
    }

}
