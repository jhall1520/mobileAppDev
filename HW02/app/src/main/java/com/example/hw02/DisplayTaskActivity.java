package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * HW02
 * DisplayTaskActivity
 * Joel Hall
 * Jimmy Kropp
 **/

public class DisplayTaskActivity extends AppCompatActivity {
    final public static String TASK_RETURN_KEY = "TASK";
    Task task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        // Displays the values of the current tasks
        TextView name = findViewById(R.id.textViewOutputTaskName);
        TextView date = findViewById(R.id.textViewOutputDate);
        TextView priority = findViewById(R.id.textViewOutputPriority);

        // if getIntent has the passed Task object
        if (getIntent() != null && getIntent().getSerializableExtra(MainActivity.TASK_KEY) != null) {
            // update information
            task = (Task) getIntent().getSerializableExtra(MainActivity.TASK_KEY);
            name.setText(task.getName());
            date.setText(task.getDate());
            priority.setText(task.getPriority());
        }

        // if cancel is pressed just end activity
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // if the delete button is pressed
        findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass the task back to MainActivity
                Intent intent = new Intent();
                intent.putExtra(TASK_RETURN_KEY, task);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}