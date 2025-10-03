package com.example.whackamole;

import android.os.Looper;
import android.util.Log;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class GameModel extends ViewModel {
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> gameStarted = new MutableLiveData<>(false);
    private final MoleModel[] moleModel = new MoleModel[9];
    private  int moleInterval = 1000;
    private final int difficultyInterval = 5000;
    private double difficulty = 1;
    private final Handler difficultyHandler = new Handler(Looper.getMainLooper());
    private final Handler moleHandler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();
public void init() {
        for (int i = 0; i < moleModel.length; i++) {
            moleModel[i] = new MoleModel();
        }
    score.setValue(0);
    gameOver.setValue(false);
}
    public void startGame() {
        gameStarted.setValue(true);
        score.setValue(0);
        gameOver.setValue(false);
        difficultyHandler.postDelayed(difficultyRunnable, difficultyInterval);
        moleHandler.postDelayed(moleRunnable, moleInterval);
    }

    private final Runnable difficultyRunnable = new Runnable() {
        @Override
        public void run() {
                difficulty += 0.2;
                moleInterval = (moleInterval - 100);
                Log.d("GameModel", "increaseDifficulty" + difficulty);
                difficultyHandler.postDelayed(this, difficultyInterval);
            }
        };

    private final Runnable moleRunnable = new Runnable() {
        @Override
        public void run() {
                Log.d("GameModel", "activateMole start");
                int randomMole = random.nextInt(moleModel.length);
                boolean foundMole = false;
                do{
                    if(Boolean.FALSE.equals(moleModel[randomMole ].getIsActive().getValue())){
                        moleModel[randomMole].getIsActive().setValue(true);
                        foundMole = true;
                    } else {
                        randomMole = random.nextInt(moleModel.length);
                    }

                }while(!foundMole);
                Log.d("GameModel", "activateMole: " + randomMole);
                moleHandler.postDelayed(this, moleInterval);
        }
    };

    public void endGame() {
        // Stop all timers to prevent more moles from appearing
        difficultyHandler.removeCallbacks(difficultyRunnable);
        moleHandler.removeCallbacks(moleRunnable);

        // Set the gameOver LiveData to true..
        gameOver.postValue(true); // postValue is safer if this could ever be called from a background thread
        gameStarted.postValue(false); // Also update the gameStarted state
    }

public LiveData<Integer> getScore() {
            return score;
}

public LiveData<Boolean> getGameOver() {
        return gameOver;
}

public LiveData<Boolean> getGameStarted() {
        return gameStarted;
}

public void incrementScore() {
        if (score.getValue() == null) {
            score.setValue(0);
        }
        score.setValue(score.getValue() + 1);
}
public MoleModel[] getMoleModel() {
        return moleModel;
}
    @Override
    protected void onCleared() {
        super.onCleared();
        difficultyHandler.removeCallbacks(difficultyRunnable);
        moleHandler.removeCallbacks(moleRunnable);
    }

}
