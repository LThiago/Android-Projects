package com.lthiago.numbershapes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void testNumber(View view) {
        Log.i("INFO", "Button pressed");

        EditText numberRAW = findViewById(R.id.number);
        String message;

        if (numberRAW.getText().toString().isEmpty()) {
            message = "Please enter a number.";
        } else {

            Number myNumber = new Number();
            myNumber.setNumber(Integer.parseInt(numberRAW.getText().toString()));
            message = String.valueOf(myNumber.getNumber());

            if (myNumber.isSquare()) {
                if (myNumber.isTriangular()) {
                    message += " is square and triangular.";
                } else {
                    message += " is square but not triangular.";
                }
            } else {
                if (myNumber.isTriangular()) {
                    message += " is triangular but not square.";
                } else {
                    message += " is neither triangular nor square.";
                }
            }
        }
        showToast(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
