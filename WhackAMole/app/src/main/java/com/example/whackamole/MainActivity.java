package com.example.whackamole;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });
        setHighScores();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setHighScores();
    }

    private void setHighScores() {
        TextView scoreListText = findViewById(R.id.scoreListText);
        List<Integer> scores = ScoreListUtil.loadHighScores(this);
        StringBuilder sb = new StringBuilder();
        if (scores == null) {
            scoreListText.setText(R.string.no_high_scores);
            return;
        }
        if (scores.isEmpty()) {
            scoreListText.setText(R.string.no_high_scores);
            return;
        } else {
            for (int i = 0; i < scores.size(); i++) {
                sb.append(i + 1).append(". ").append(scores.get(i)).append("\n");
            }
        }
        scoreListText.setText(sb.toString());
    }
}