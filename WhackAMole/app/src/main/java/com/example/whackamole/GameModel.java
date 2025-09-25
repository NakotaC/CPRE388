package com.example.whackamole;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Timer;
import java.util.TimerTask;

public class GameModel extends ViewModel {
    private final MutableLiveData<Integer> score = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private  MoleModel[] moleModel = new MoleModel[9];
    private  int moleInterval = 2000;
    private final long difficultyInterval = 10000;
    private int difficulty = 1;
    private Timer difficultyTimer = new Timer();
    private Timer moleTimer = new Timer();

    public void startGame() {
        gameStarted.setValue(true);
        score.setValue(0);
        gameOver.setValue(false);
        for (int i = 0; i < moleModel.length; i++) {
            moleModel[i] = new MoleModel();
        }
        difficultyTimer.schedule(increaseDifficulty(), difficultyInterval, difficultyInterval);
        moleTimer.schedule(activateMole(), moleInterval, moleInterval);
    }

private TimerTask increaseDifficulty() {
        return new TimerTask() {
            @Override
            public void run() {
                difficulty++;
                moleInterval -= 200;
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

}
