package com.example.stopwatch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StopwatchViewModel extends ViewModel {

    // Variable to track elapsed time
    private MutableLiveData<Long> elapsedTime = new MutableLiveData<>(0L);;

    // TODO: Add methods to start, stop, and reset the stopwatch
    public void start() {

    }
    public void stop() {

    }
    public void reset() {

    }
    public long getElapsedTime() {
        return elapsedTime.getValue();
    }
}
