package com.example.whackamole;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoleModel extends ViewModel {

    private final MutableLiveData<Boolean> isActive = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isExpired = new MutableLiveData<>();

    private final Handler expirationHandler = new Handler(Looper.getMainLooper());

    private final Runnable expirationRunnable = () -> {
        // When the timer finishes, the mole becomes inactive and is marked as expired.
        isActive.setValue(false);
        isExpired.setValue(true);
    };

    /**
     * Starts the expiration timer for this mole.
     * When the timer finishes, the mole will deactivate itself.
     * @param duration The time in milliseconds until the mole expires.
     */
    public void startExpirationTimer(long duration) {
        isExpired.setValue(false); // Reset expiration state
        // Post the runnable to run after the specified duration.
        expirationHandler.postDelayed(expirationRunnable, duration);
    }

    /**
     * Cancels the expiration timer.
     * This should be called when the mole is successfully hit or the game ends.
     */
    public void cancelExpirationTimer() {
        // Remove any pending expiration tasks for this mole.
        expirationHandler.removeCallbacks(expirationRunnable);
    }

    public MoleModel() {
        isActive.setValue(false);
        isExpired.setValue(false);
    }

    public MutableLiveData<Boolean> getIsActive() {
        return isActive;
    }

    // Add a getter for isExpired if you need to observe it elsewhere
    public MutableLiveData<Boolean> getIsExpired() {
        return isExpired;
    }

    // It's good practice to also clean up in onCleared, though the Handler uses the main looper
    // which lives for the app's lifetime. Removing callbacks is the most critical part.
    @Override
    protected void onCleared() {
        super.onCleared();
        cancelExpirationTimer();
    }
}
