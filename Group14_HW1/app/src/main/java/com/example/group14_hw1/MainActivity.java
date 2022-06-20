package com.example.group14_hw1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.DateFormat;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * HW1
 * Group14_HW1
 * Joel Hall
 * Jimmy Kropp
 */

public class MainActivity extends AppCompatActivity {

    double tip = 0.10;
    int splitPerson = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.customTipBar);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioGroup slipPersonGroup = findViewById(R.id.splitPersonGroup);
        Button clearButton = findViewById(R.id.btnClear);

        TextView enterBill = findViewById(R.id.txtEnterBill);
        enterBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable editable) {
                // Finds out what the text was changed to
                String value = editable.toString();
                // if the value is not empty then update
                if (!value.equals("")) {
                    update(value);
                } else {
                    // if the value is empty, put all tip, total, and total/person values back to default
                    TextView tipView = findViewById(R.id.txtTip);
                    tipView.setText("$0.0");
                    TextView total = findViewById(R.id.txtTotal);
                    total.setText("$0.0");
                    TextView totalPerson = findViewById(R.id.txtTotalPerson);
                    totalPerson.setText("$0.0");
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Finds which Radio Button was clicked and updates
                if (checkedId == R.id.rad10) {
                    tip = 0.10;
                } else if (checkedId == R.id.rad15) {
                    tip = 0.15;
                } else if (checkedId == R.id.rad18) {
                    tip = 0.18;
                } else if (checkedId == R.id.radCustom) {
                    tip = seekBar.getProgress() / 100.0;
                }
                update(enterBill.getText().toString());
            }
        });

        slipPersonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Finds which Radio Button was clicked and updates
                if (checkedId == R.id.radOne) {
                    splitPerson = 1;
                } else if (checkedId == R.id.radTwo) {
                    splitPerson = 2;
                } else if (checkedId == R.id.radThree) {
                    splitPerson = 3;
                } else if (checkedId == R.id.radFour) {
                    splitPerson = 4;
                }
                update(enterBill.getText().toString());
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Changes the percent on the right side of the SeekBar
                TextView percentage = findViewById(R.id.textView40);
                String percent = seekBar.getProgress() + "%";
                percentage.setText(percent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Updates custom Tip
                tip = seekBar.getProgress() / 100.0;
                update(enterBill.getText().toString());
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterBill.setText("");
                tip = 0.1;
                splitPerson = 1;
                // Set Tip back to default
                TextView textTip = findViewById(R.id.txtTip);
                textTip.setText("$0.0");
                // Set Total back to default
                TextView totalBill = findViewById(R.id.txtTotal);
                totalBill.setText("$0.0");
                // Set Total/Person back to default
                TextView splitPeople = findViewById(R.id.txtTotalPerson);
                splitPeople.setText("$0.0");
                seekBar.setProgress(40);
                // Set back to splitting by one person
                slipPersonGroup.clearCheck();
                RadioButton one = findViewById(R.id.radOne);
                one.setChecked(true);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(String stringBill) {
        // if string is not empty then update
        if (!stringBill.equals("")) {
            DecimalFormat df = new DecimalFormat("0.0");
            // updates tip value
            Double billValue = Double.parseDouble(stringBill);
            TextView textTip = findViewById(R.id.txtTip);
            String tipVal = "$" + df.format((billValue * tip));
            textTip.setText(tipVal);
            // updates total bill value
            TextView totalBill = findViewById(R.id.txtTotal);
            double totalAmount = billValue + (billValue * tip);
            String total = "$" + df.format(totalAmount);
            totalBill.setText(total);
            // updates total per person value
            TextView splitPeople = findViewById(R.id.txtTotalPerson);
            double person = totalAmount / splitPerson;
            String split = "$" + df.format(person);
            splitPeople.setText(split);
        }
    }

}