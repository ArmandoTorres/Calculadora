package com.sdq.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String OPERATOR = "%x/+-";
    private static final String MULTIPLY = "x";
    private static final String DIVIDE = "/";
    private static final String ADDITION = "+";
    private static final String SUBTRACTION = "-";
    private static final String PERCENTAGE = "%";

    private EditText et_main;
    private Button btn_all_clear, btn_clear, btn_split, btn_seven, btn_eight, btn_nine,
            btn_multiply, btn_six, btn_five, btn_four, btn_minus, btn_one, btn_two, btn_three,
            btn_plus, btn_zero, btn_point, btn_equals, btn_percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set References
        et_main = findViewById(R.id.et_main);
        btn_all_clear = findViewById(R.id.btn_all_clear);
        btn_clear = findViewById(R.id.btn_clear);
        btn_split = findViewById(R.id.btn_split);
        btn_seven = findViewById(R.id.btn_seven);
        btn_eight = findViewById(R.id.btn_eight);
        btn_nine = findViewById(R.id.btn_nine);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_six = findViewById(R.id.btn_six);
        btn_five = findViewById(R.id.btn_five);
        btn_four = findViewById(R.id.btn_four);
        btn_minus = findViewById(R.id.btn_minus);
        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        btn_plus = findViewById(R.id.btn_plus);
        btn_zero = findViewById(R.id.btn_zero);
        btn_point = findViewById(R.id.btn_point);
        btn_equals = findViewById(R.id.btn_equals);
        btn_percentage = findViewById(R.id.btn_percentage);
        btn_all_clear.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_split.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_nine.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_zero.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_equals.setOnClickListener(this);
        btn_percentage.setOnClickListener(this);
    }

    private String clearOne(String text) {
        if (text != null && text != "" && text.length() > 0) {
            text = text.substring(0,text.length()-1);
        }
        return text;
    }

    @Override
    public void onClick(View view) {
        try {
            String text = et_main.getText() != null ? et_main.getText().toString() : "";

            switch (view.getId()) {
                case R.id.btn_equals:
                    if (text != null && text != "" && text.length() > 0) {
                        if ( (isNumeric(text.substring(text.length()-1)) || text.substring(text.length()-1).equals(PERCENTAGE))
                                && isNumeric(String.valueOf(text.charAt(0)))){
                            et_main.setText(calculate(text));
                        } else {
                            Toast.makeText(getApplicationContext(),R.string.invalid_format,Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    break;
                case R.id.btn_all_clear:
                    et_main.setText("");
                    break;
                case R.id.btn_clear:
                    et_main.setText(clearOne(text));
                    break;
                case R.id.btn_point:
                case R.id.btn_minus:
                case R.id.btn_multiply:
                case R.id.btn_split:
                case R.id.btn_plus:
                    if (text != null && text != "" && text.length() > 0) {
                        if (isNumeric(text.substring(text.length()-1))
                                || text.substring(text.length()-1).equals(PERCENTAGE)){
                            Object tag = view.getTag();
                            if (tag != null && tag instanceof String){
                                et_main.setText(text+(String)tag);
                            }
                        }
                    }
                    break;
                case R.id.btn_percentage:
                    if (text != null && text != "" && text.length() > 0) {
                        if (isNumeric(text.substring(text.length()-1))){
                            Object tag = view.getTag();
                            if (tag != null && tag instanceof String){
                                et_main.setText(text+(String)tag);
                            }
                        }
                    }
                    break;
                case R.id.btn_eight:
                case R.id.btn_five:
                case R.id.btn_four:
                case R.id.btn_nine:
                case R.id.btn_one:
                case R.id.btn_seven:
                case R.id.btn_six:
                case R.id.btn_two:
                case R.id.btn_zero:
                case R.id.btn_three:
                    if (text != null && text.length() >= 20 ){
                        Toast.makeText(getApplicationContext(),R.string.max_lenght,Toast.LENGTH_LONG).show();
                        return;
                    }
                    Object tag = view.getTag();
                    if (tag != null && tag instanceof String){
                        et_main.setText(text+(String)tag);
                    }
                    break;
            }

        } catch (Exception ex){
            String errorMessage = getString(R.string.on_click_error)+ex.getMessage();
            Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
        }
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public String calculate(String input) {

        if (!(input.contains(MULTIPLY) || input.contains(DIVIDE) || input.contains(SUBTRACTION)
                || input.contains(ADDITION) || input.contains(PERCENTAGE) )){
            return input;
        }

        List<String> elements = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            String element = String.valueOf(input.charAt(i));
            if (OPERATOR.contains(element) || elements.isEmpty() || OPERATOR.contains(elements.get(elements.size() - 1))) {
                elements.add(element);
            } else {
                elements.set(elements.size() - 1,
                        elements.get(elements.size() - 1) + element);
            }
        }

        double result = 0;

        //«Percentage»
        if (elements.contains(PERCENTAGE)) {
            for (int j = 0; j < elements.size(); j++) {
                if (PERCENTAGE.contains(elements.get(j))) {
                    if (elements.get(j).equals(PERCENTAGE)) {
                        if (Double.parseDouble(elements.get(j - 1)) > 0) {
                            result = Double.parseDouble(elements.get(j - 1))
                                    / 100;
                        }
                    }
                    elements.set(j - 1, String.valueOf(result));
                    elements.remove(j);
                    boolean b1 = elements.contains(PERCENTAGE);
                    if (!b1) {
                        break;
                    } else {
                        --j;
                    }
                }
            }
        }

        String[] operatorPair = new String[]{"x/", "+-"};

        //«paréntesis, potencias, multiplicación, división, suma, resta»
        for (String operator : operatorPair) {
            for (int j = 0; j < elements.size(); j++) {
                if (operator.contains(elements.get(j))) {
                    if (elements.get(j).equals(MULTIPLY)) {
                        result = Double.parseDouble(elements.get(j - 1))
                                * Double.parseDouble(elements.get(j + 1));
                    }
                    if (elements.get(j).equals(DIVIDE)) {
                        result = Double.parseDouble(elements.get(j - 1))
                                / Double.parseDouble(elements.get(j + 1));
                    }
                    if (elements.get(j).equals(ADDITION)) {
                        result = Double.parseDouble(elements.get(j - 1))
                                + Double.parseDouble(elements.get(j + 1));
                    }
                    if (elements.get(j).equals(SUBTRACTION)) {
                        result = Double.parseDouble(elements.get(j - 1))
                                - Double.parseDouble(elements.get(j + 1));
                    }
                    elements.set(j - 1, String.valueOf(result));
                    elements.remove(j);
                    elements.remove(j);
                    boolean b1 = elements.contains(String.valueOf(operator.charAt(0)));
                    boolean b2 = elements.contains(String.valueOf(operator.charAt(1)));
                    if (!b1 && !b2) {
                        break;
                    } else {
                        --j;
                    }
                }
            }
        }

        //Verifying if have decimal part.
        if (result > 0){
            double resultWithOutDecimal = result - Math.floor(result);
            if (Math.abs(resultWithOutDecimal) == 0){
                return String.valueOf((int)result);
            }
        }

        return String.valueOf(result);
    }
}
