package com.example.whackamole;

import static com.example.whackamole.ScoreListUtil.saveHighScores;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GameModel gameModel = new ViewModelProvider(this).get(GameModel.class);
        MoleModel[] moleModels = gameModel.getMoleModel();
        for (int i = 0; i < moleModels.length; i++) {
            ImageButton mole = findViewById(R.id.mole + i);
        }
        ImageButton backBtn = findViewById(R.id.gameBackBtn);
        backBtn.setOnClickListener(v -> {
            saveHighScores(this, gameModel.getScore().getValue());
            finish();
        });

    }

    private void activateMoleView(GameModel gameModel, int index) {


    }

}