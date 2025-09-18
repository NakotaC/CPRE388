package com.example.stopwatch;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class  StopwatchViewModel extends ViewModel {

    // Variable to track elapsed time
    private final MutableLiveData<Long> elapsedTime = new MutableLiveData<>(0L);
    private boolean isRunning = false;
    private long startTime = 0L;
    private long stopTime = 0L;
    private boolean isPaused = false;
    private final Handler timerHandler = new Handler(Looper.getMainLooper());

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                long currentTime = System.currentTimeMillis();
                long elapsed = currentTime - startTime;
                elapsedTime.postValue(elapsed);
                timerHandler.postDelayed(this, 10);
            }
        }
    };
    public LiveData<Long> getElapsedTime() {
        return elapsedTime;
    }
    public void start() {
        if (!isRunning) {
            if (isPaused) {
                startTime += System.currentTimeMillis() - stopTime;
                isPaused = false;
            } else {
                startTime = System.currentTimeMillis();
            }
            isRunning = true;
            timerHandler.post(timerRunnable);
        }

    }
    public void stop() {
        if(isRunning){
            isRunning = false;
            isPaused = true;
            stopTime = System.currentTimeMillis();
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
    public void reset() {
        isRunning = false;
        isPaused = false;
        elapsedTime.setValue(0L);
        startTime = 0L;
        stopTime = 0L;
        timerHandler.removeCallbacks(timerRunnable);
    }
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Prevent memory leaks by removing callbacks
        timerHandler.removeCallbacks(timerRunnable);
    }
}
