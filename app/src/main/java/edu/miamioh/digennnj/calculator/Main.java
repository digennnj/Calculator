package edu.miamioh.digennnj.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private List<Button> buttons;
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
            R.id.btnEquals
    };

    private TextView expression;
    private InfixExpression infix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new ArrayList<Button>(BUTTON_IDS.length);
        for(int id : BUTTON_IDS) {
            Button btn = (Button) findViewById(id);
            btn.setOnClickListener(this);
            buttons.add(btn);
        }

        expression = findViewById(R.id.txtExpression);
    }

    @Override
    public void onClick(View view)
    {
        if(expression.getText().equals("Enter an expression...")) expression.setText("");

        if(view.getId() == R.id.btnEquals) {

        }
        expression.append(view.getContentDescription());

    }


}
