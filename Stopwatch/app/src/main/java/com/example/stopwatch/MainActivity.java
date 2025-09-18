package com.example.stopwatch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private StopwatchViewModel viewModel;
    private TextView tvTime;
    private Button btnStartStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(StopwatchViewModel.class);

        // TODO: Initialize UI components (TextView, Buttons)

        Button btnReset = findViewById(R.id.btnReset);
        btnStartStop = findViewById(R.id.btnStartStop);
        tvTime = findViewById(R.id.TvTime);

        // TODO: Set up button listeners for Start/Stop and Reset

        btnStartStop.setOnClickListener(v -> {
                    if (viewModel.isRunning()) {
                        viewModel.stop();
                        btnStartStop.setText(R.string.Start);
                    } else {
                        viewModel.start();
                        btnStartStop.setText(R.string.Stop);
                    }
                }
        );

        btnReset.setOnClickListener(v -> {
            viewModel.reset();
            btnStartStop.setText(R.string.Start);
            tvTime.setText(R.string.Reset_Time);
        });


        viewModel.getElapsedTime().observe(this, elapsedTime -> {
            long seconds = elapsedTime / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long millis = elapsedTime % 1000 / 10;
            seconds %= 60;
            minutes %= 60;
            String formattedTime = String.format(Locale.US, "%02d:%02d:%02d.%02d", hours, minutes, seconds, millis);
            tvTime.setText(formattedTime);
        });
    }



}
