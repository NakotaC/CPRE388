package com.example.whackamole;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Timer;
import java.util.TimerTask;

public class GameModel extends ViewModel {
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> gameStarted = new MutableLiveData<>(false);
    private  MoleModel[] moleModel = new MoleModel[9];
    private  int moleInterval = 2000;
    private double difficulty = 1;
    private final Timer difficultyTimer = new Timer();
    private final Timer moleTimer = new Timer();
    private int lastActivatedMole = -1;

    public void startGame() {
        gameStarted.setValue(true);
        score.setValue(0);
        gameOver.setValue(false);
        for (int i = 0; i < moleModel.length; i++) {
            moleModel[i] = new MoleModel();
        }
        int difficultyInterval = 10000;
        difficultyTimer.schedule(increaseDifficulty(), difficultyInterval, difficultyInterval);
        moleTimer.schedule(activateMole(), moleInterval, moleInterval);
    }

private TimerTask increaseDifficulty() {
        return new TimerTask() {
            @Override
            public void run() {
                difficulty += 0.2;
                moleInterval = (int) (moleInterval * difficulty);
                moleTimer.schedule(activateMole(), moleInterval, moleInterval);
            }
        };
}

private TimerTask activateMole() {
        return new TimerTask() {
            @Override
            public void run() {
                int randomMole = (int) (Math.random() % moleModel.length);
                boolean foundMole = false;
                do{
                    if(Boolean.FALSE.equals(moleModel[randomMole].getIsActive().getValue())){
                        moleModel[randomMole].getIsActive().setValue(true);
                        foundMole = true;
                        lastActivatedMole = randomMole;
                    } else {
                        randomMole = (int) (Math.random() % moleModel.length);
                    }

                }while(!foundMole);
            }
        };
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

public int getLastActivatedMole() {
        return lastActivatedMole;
}

}
