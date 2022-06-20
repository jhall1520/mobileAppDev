package com.example.group14_inclass06;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Group14_InClass06
 * MainActivity.java
 * Joel Hall
 * Jimmy Kropp
 */
public class MainActivity extends AppCompatActivity {

    ExecutorService threadPool;
    int numTimes = 1;
    SeekBar seekBar;
    Handler handler;
    ArrayList<Double> randomNumbers;
    ListView listView;
    ArrayAdapter adapter;
    ProgressBar progressBar;
    TextView textAverage;
    TextView average;
    TextView progress;
    Button buttonGenerate;

    private static final String ARG_NUMBERS = "NUMBERS";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomNumbers = new ArrayList<>();
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, randomNumbers);
        listView.setAdapter(adapter);
        threadPool = Executors.newFixedThreadPool(2);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMin(1);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        average = findViewById(R.id.textViewAverageOutput);
        progress = findViewById(R.id.textViewProgress);
        progress.setVisibility(View.INVISIBLE);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        textAverage = findViewById(R.id.textViewAverage);
        textAverage.setVisibility(View.INVISIBLE);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                progressBar.setVisibility(View.VISIBLE);
                textAverage.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                if (message.getData().getSerializable(ARG_NUMBERS) != null) {
                    randomNumbers.add(message.getData().getDouble(ARG_NUMBERS));
                    adapter.notifyDataSetChanged();

                    progressBar.setProgress(randomNumbers.size());
                    Double averageValue = 0.0;
                    for (int i = 0; i < randomNumbers.size(); i++) {
                        averageValue += randomNumbers.get(i);
                    }
                    averageValue = averageValue / randomNumbers.size();
                    average.setText(averageValue.toString());

                    String progressInfo = randomNumbers.size() + "/" + numTimes;
                    progress.setText(progressInfo);

                    if (randomNumbers.size() == numTimes)
                        buttonGenerate.setEnabled(true);

                }
                return false;
            }
        });

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonGenerate.setEnabled(false);
                randomNumbers.clear();
                numTimes = seekBar.getProgress();
                progressBar.setMax(numTimes);
                progressBar.setProgress(0);
                progress.setText("0/" + numTimes);
                average.setText("");

                adapter.notifyDataSetChanged();

                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < numTimes; i++) {

                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putDouble(ARG_NUMBERS, HeavyWork.getNumber());
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }

                    }
                });
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView times = findViewById(R.id.textViewTimes);

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String numberTimes = seekBar.getProgress() + " Times";
                times.setText(numberTimes);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}