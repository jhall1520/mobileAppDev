package com.example.hw02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * HW02
 * CreateTaskActivity
 * Joel Hall
 * Jimmy Kropp
 **/

public class CreateTaskActivity extends AppCompatActivity {

    final static public String CREATE_KEY = "CREATE";

    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String priority = "High";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // if user clicks on set Date
        findViewById(R.id.buttonSetDate).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                // sets up calendar
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // formats date
                                TextView textView = findViewById(R.id.textViewShowDate);
                                String date;
                                if (day < 10 && month < 10)
                                    date = "0" + (month + 1) + "/" + "0" + day + "/" + year;
                                else if (day < 10)
                                    date = (month + 1) + "/" + "0" + day + "/" + year;
                                else if (month < 10)
                                    date = "0" + (month + 1) + "/" + day + "/" + year;
                                else
                                    date = (month + 1) + "/" + day + "/" + year;
                                // updates date in CreateTaskActivity.java
                                textView.setText(date);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Holds name and data info
                EditText name = findViewById(R.id.editTextTaskName);
                TextView date = findViewById(R.id.textViewShowDate);

                // if the name text box is empty display a toast
                if (name.getText().toString().equals("")) {
                    Toast.makeText(CreateTaskActivity.this, "Please put a name for the task.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // if a date has not been selected display a toast
                if (date.getText().equals("")) {
                    Toast.makeText(CreateTaskActivity.this, "Please pick a date for the task.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // if all data is entered create a task and send it back to MainActivity.java
                Task task = new Task(name.getText().toString(), date.getText().toString(), priority);
                Intent intent = new Intent();
                intent.putExtra(CREATE_KEY, task);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // if cancel button is clicked then finish activity
        findViewById(R.id.buttonCancelTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // gets radioGroup
        RadioGroup radioGroup = findViewById(R.id.radioGroupPriority);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                // when a new radio button is clicked we update the priority variable
                if (checkId == R.id.radioButtonLow) {
                    priority = "Low";
                } else if (checkId == R.id.radioButtonMedium) {
                    priority = "Medium";
                } else if (checkId == R.id.radioButtonHigh) {
                    priority = "High";
                }
            }
        });
    }
}