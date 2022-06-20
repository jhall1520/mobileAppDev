package com.example.hw02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

/**
 * HW02
 * MainActivity
 * Joel Hall
 * Jimmy Kropp
 **/

public class MainActivity extends AppCompatActivity {

    final public static String TASK_KEY = "TASK";
    final public static String TASKS_KEY = "TASKS";

    private Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create array list for all tasks
        ArrayList<Task> tasks = new ArrayList<>();
        // displays how many tasks are currently in the list
        TextView displayText = findViewById(R.id.displayTasksTextView);

        // current task with the lowest date and highest priority
        TextView taskName = findViewById(R.id.textViewTaskName);
        TextView date = findViewById(R.id.textViewDate);
        TextView priority = findViewById(R.id.textViewPriority);

        // Activity Result for DisplayTaskActivity.java
        ActivityResultLauncher<Intent> startForResultDisplay = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // if the delete button was clicked
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getSerializableExtra(DisplayTaskActivity.TASK_RETURN_KEY) != null) {
                        // remove tasks from the task list
                        tasks.remove(selectedTask);
                        // update number of tasks
                        String numberTasks = "You have " + tasks.size() + " tasks";
                        displayText.setText(numberTasks);
                        // if there are still more tasks in the list
                        if (tasks.size() > 0) {
                            // set values to the first task in the list
                            taskName.setText(tasks.get(0).getName());
                            date.setText(tasks.get(0).getDate());
                            priority.setText(tasks.get(0).getPriority());
                            // if there are no more tasks in the list
                        } else {
                            // set values back to default
                            String none = "None";
                            String empty = "";
                            taskName.setText(none);
                            date.setText(empty);
                            priority.setText(empty);
                        }
                    }
                }
            }
        });

        findViewById(R.id.viewTasksButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Grab all taskNames from list
                CharSequence[] taskNames = new CharSequence[tasks.size()];
                for (int i = 0; i < tasks.size(); i++) {
                    taskNames[i] = tasks.get(i).getName();
                }
                // create a AlertDialog that lets the user click on a certain task
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Task")
                        .setItems(taskNames, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedTask = tasks.get(i);

                                Intent intent = new Intent(MainActivity.this, DisplayTaskActivity.class);
                                intent.putExtra(TASK_KEY, tasks.get(i));
                                // when a task is clicked start the DisplayTaskActivity.java
                                startForResultDisplay.launch(intent);
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create();
                builder.show();
            }
        });

        // this Activity Result is for CreateTaskActivity.java
        ActivityResultLauncher<Intent> startForResultCreate = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // if the user selected submit
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getSerializableExtra(CreateTaskActivity.CREATE_KEY) != null) {
//                      // get the tasks that was created and add it to the task list
                        tasks.add((Task) result.getData().getSerializableExtra(CreateTaskActivity.CREATE_KEY));
                        // update number of tasks
                        String numberTasks = "You have " + tasks.size() + " tasks";
                        displayText.setText(numberTasks);
                        // if there is more than one task in the list
                        if (tasks.size() > 1) {
                            // sort the list by date and then priority
                            for (int j = 0; j < tasks.size(); j++) {
                                for (int i = 1; i < tasks.size(); i++) {
                                    Task temp = tasks.get(i);
                                    // if the dates are equal
                                    if (tasks.get(i - 1).getDate().equals(tasks.get(i).getDate())) {
                                        // if the current task is low priority and the other task doesn't have low priority
                                        if (tasks.get(i - 1).getPriority().equals("Low")) {
                                            // switch the tasks
                                            if (!tasks.get(i).getPriority().equals("Low")) {
                                                tasks.set(i, tasks.get(i - 1));
                                                tasks.set(i - 1, temp);
                                            }
                                        } else if (tasks.get(i - 1).getPriority().equals("Medium")) {
                                            if (tasks.get(i).getPriority().equals("High")) {
                                                tasks.set(i, tasks.get(i - 1));
                                                tasks.set(i - 1, temp);
                                            }
                                        }
                                        // if the year is greater than the next year switch the tasks
                                    } else if (tasks.get(i - 1).getDate().substring(6).compareTo(temp.getDate().substring(6)) > 0) {
                                        tasks.set(i, tasks.get(i - 1));
                                        tasks.set(i - 1, temp);
                                        // if the month is greater than the next month switch the tasks
                                    } else if (tasks.get(i - 1).getDate().substring(0, 2).compareTo(temp.getDate().substring(0, 2)) > 0) {
                                        tasks.set(i, tasks.get(i - 1));
                                        tasks.set(i - 1, temp);
                                        // if the day is greater than the next day switch the tasks
                                    } else if (tasks.get(i - 1).getDate().substring(3, 5).compareTo(temp.getDate().substring(3, 5)) > 0) {
                                        tasks.set(i, tasks.get(i - 1));
                                        tasks.set(i - 1, temp);
                                    }
                                }
                            }
                        }
                        // update the MainActivity task page
                        taskName.setText(tasks.get(0).getName());
                        date.setText(tasks.get(0).getDate());
                        priority.setText(tasks.get(0).getPriority());
                    }
                }
            }
        });

        findViewById(R.id.createTasksButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts CreateTaskActivity.java
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startForResultCreate.launch(intent);
            }
        });
    }

}