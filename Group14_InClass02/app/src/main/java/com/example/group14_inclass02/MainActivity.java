/**
 * In Class 02
 * Group14_InClass02
 * Jimmy Kropp
 * Joel Hall
 */


package com.example.group14_inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    float discount = 0;
    float calc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                if (checkedID == R.id.rad5) {
                    discount = (float) 0.05;
                } else if (checkedID == R.id.rad10) {
                    discount = (float) 0.10;
                } else if (checkedID == R.id.rad15) {
                    discount = (float) 0.15;
                } else if (checkedID == R.id.rad20) {
                    discount = (float) 0.20;
                } else if (checkedID == R.id.rad50) {
                    discount = (float) 0.50;
                }
            }
        });

        findViewById(R.id.btnCalc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat("0.00");

                EditText editText = findViewById(R.id.txtInput);
                if (editText.getText() != null) {
                    try {
                        String temp = editText.getText().toString();
                        Float val = Float.parseFloat(temp);
                        if (val > 0) {
                            calc = (1 - discount) * val;
                            TextView textView = findViewById(R.id.txtOut);
                            String output = df.format(calc);
                            textView.setText(output);

                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "This is not a valid positive number!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.txtClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView clear1 = findViewById(R.id.txtInput);
                clear1.setText("");

                TextView clear2 = findViewById(R.id.txtOut);
                clear2.setText("");

                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                radioGroup.clearCheck();

                RadioButton rad5 = findViewById(R.id.rad5);
                rad5.setChecked(true);
            }
        });
    }
}
