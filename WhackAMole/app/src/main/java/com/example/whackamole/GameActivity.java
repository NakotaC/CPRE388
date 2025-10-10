package com.example.whackamole;

import static com.example.whackamole.ScoreListUtil.saveHighScores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

/**
 * This class represents the Game Activity for the Whack A mole game.
 * It handles the UI and logic for the game, using the GameModel.
 */
public class GameActivity extends AppCompatActivity {
    private GameModel gameModel;
    private int missedMoles = 0;
    private ImageButton mole1;
    private ImageButton mole2;
    private ImageButton mole3;
    private ImageButton mole4;
    private ImageButton mole5;
    private ImageButton mole6;
    private ImageButton mole7;
    private ImageButton mole8;
    private ImageButton mole9;
    private SoundManager soundManager;

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

        gameModel = new ViewModelProvider(this).get(GameModel.class);
        mole1 = findViewById(R.id.mole1);
        mole2 = findViewById(R.id.mole2);
        mole3 = findViewById(R.id.mole3);
        mole4 = findViewById(R.id.mole4);
        mole5 = findViewById(R.id.mole5);
        mole6 = findViewById(R.id.mole6);
        mole7 = findViewById(R.id.mole7);
        mole8 = findViewById(R.id.mole8);
        mole9 = findViewById(R.id.mole9);

        ImageView xImage1 = findViewById(R.id.xImage1);
        ImageView xImage2 = findViewById(R.id.xImage2);
        ImageView xImage3 = findViewById(R.id.xImage3);

        xImage1.setVisibility(View.INVISIBLE);
        xImage2.setVisibility(View.INVISIBLE);
        xImage3.setVisibility(View.INVISIBLE);

        soundManager = new SoundManager(this);

        gameModel.init();

        for (int i = 0; i < gameModel.getMoleModel().length; i++) {
            int finalI = i;
            gameModel.getMoleModel()[i].getIsActive().observe(this, isActive -> {
                if (isActive) {
                    activateMoleView(gameModel, finalI);
                } else {
                    deactivateMoleView(gameModel, finalI);
                }
            });
            gameModel.getMoleModel()[i].getIsExpired().observe(this, isExpired -> {
                if (isExpired) {
                    deactivateMoleView(gameModel, finalI);
                    soundManager.playMissedMoleSound();
                    missedMoles++;
                    if (missedMoles == 1) {
                        xImage1.setVisibility(View.VISIBLE);
                    } else if (missedMoles == 2) {
                        xImage2.setVisibility(View.VISIBLE);
                    } else if (missedMoles == 3) {
                        xImage3.setVisibility(View.VISIBLE);
                    } else if (missedMoles > 3) {
                        xImage1.setVisibility(View.INVISIBLE);
                        xImage2.setVisibility(View.INVISIBLE);
                        xImage3.setVisibility(View.INVISIBLE);
                        gameModel.endGame();
                    }
                }
            });

            deactivateMoleView(gameModel, i);
        }

        ImageButton backBtn = findViewById(R.id.gameBackBtn);
        backBtn.setOnClickListener(v -> {
            saveHighScores(this, gameModel.getScore().getValue());
            soundManager.release();
            finish();
        });

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> gameModel.startGame());

        gameModel.getGameStarted().observe(this, gameStarted -> {
            if (gameStarted) {
                startBtn.setVisibility(View.INVISIBLE);
            } else {
                startBtn.setVisibility(View.VISIBLE);
            }
        });

        TextView gameOverText = findViewById(R.id.gameOverTxt);
        gameModel.getGameOver().observe(this, gameOver -> {
            if (gameOver) {
                gameOverText.setVisibility(View.VISIBLE);
            } else {
                gameOverText.setVisibility(View.INVISIBLE);
            }
        });


        TextView scoreText = findViewById(R.id.gameScoreTxt);
        gameModel.getScore().observe(this, score -> {
            String scoreString = "Score: " + score;
            scoreText.setText(scoreString);
        });
    }


    private void deactivateMoleView(GameModel gameModel, int i) {
        switch (i) {
            case 0:
                mole1.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mole2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mole3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mole4.setVisibility(View.INVISIBLE);
                break;
            case 4:
                mole5.setVisibility(View.INVISIBLE);
                break;
            case 5:
                mole6.setVisibility(View.INVISIBLE);
                break;
            case 6:
                mole7.setVisibility(View.INVISIBLE);
                break;
            case 7:
                mole8.setVisibility(View.INVISIBLE);
                break;
            case 8:
                mole9.setVisibility(View.INVISIBLE);
                break;
        }
        gameModel.getMoleModel()[i].cancelExpirationTimer();

    }

    private void activateMoleView(GameModel gameModel, int index) {
        switch (index) {
            case 0:
                mole1.setVisibility(View.VISIBLE);
                break;
            case 1:
                mole2.setVisibility(View.VISIBLE);
                break;
            case 2:
                mole3.setVisibility(View.VISIBLE);
                break;
            case 3:
                mole4.setVisibility(View.VISIBLE);
                break;
            case 4:
                mole5.setVisibility(View.VISIBLE);
                break;
            case 5:
                mole6.setVisibility(View.VISIBLE);
                break;
            case 6:
                mole7.setVisibility(View.VISIBLE);
                break;
            case 7:
                mole8.setVisibility(View.VISIBLE);
                break;
            case 8:
                mole9.setVisibility(View.VISIBLE);
                break;
        }
        int moleTimer = Math.max(350, 900 - (50 * gameModel.getDifficulty().getValue()));
        gameModel.getMoleModel()[index].startExpirationTimer((900 / gameModel.getDifficulty().getValue()));
        soundManager.playActivateSound();
    }


    /**
     * OnClick handler for all of the mole buttons.
     * @param view The view that was clicked.
     */
    public void onMoleClicked(View view) {
        ImageButton clickedMole = (ImageButton) view;
        int clickedID = clickedMole.getId();
        int clickedIndex = -1;

        if (clickedID == R.id.mole1) {
            clickedIndex = 0;
        } else if (clickedID == R.id.mole2) {
            clickedIndex = 1;
        } else if (clickedID == R.id.mole3) {
            clickedIndex = 2;
        } else if (clickedID == R.id.mole4) {
            clickedIndex = 3;
        } else if (clickedID == R.id.mole5) {
            clickedIndex = 4;
        } else if (clickedID == R.id.mole6) {
            clickedIndex = 5;
        } else if (clickedID == R.id.mole7) {
            clickedIndex = 6;
        } else if (clickedID == R.id.mole8) {
            clickedIndex = 7;
        } else if (clickedID == R.id.mole9) {
            clickedIndex = 8;
        }

        if (Boolean.TRUE.equals(gameModel.getMoleModel()[clickedIndex].getIsActive().getValue())) {
            gameModel.incrementScore();
            gameModel.getMoleModel()[clickedIndex].getIsActive().setValue(false);
            soundManager.playTapMoleSound();
            deactivateMoleView(gameModel, clickedIndex);
        }

    }
}