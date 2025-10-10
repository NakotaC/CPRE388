package com.example.whackamole;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

/**
 * This class handles manages the sounds for the game.
 */
public class SoundManager {
    private SoundPool soundPool;
    private int activateSoundId;
    private int tapMoleSoundId;
    private int missedMoleSoundId;
    private Context context;

    // Use flags to track if sounds are loaded
    private boolean isActivateSoundLoaded = false;
    private boolean isTapMoleSoundLoaded = false;
    private boolean isMissedMoleSoundLoaded = false;

    /**
     * Constructor for SoundManager. Initializes the SoundPool and loads sounds.
     * @param context The application context.
     */
    public SoundManager(Context context) {
        this.context = context;

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        loadSounds();
    }

    private void loadSounds() {
        if (soundPool == null || context == null) {
            Log.e("SoundManager", "Cannot load sounds, SoundPool or Context is null.");
            return;
        }

        try {
            // Log right before the call that crashes
            Log.d("SoundManager", "Attempting to load R.raw.activate");
            activateSoundId = soundPool.load(context, R.raw.activate, 1);
           Log.d("SoundManager", "Attempting to load R.raw.tapmole");
            tapMoleSoundId = soundPool.load(context, R.raw.tapmole, 1);
            Log.d("SoundManager", "Finished initiating sound loading.");
            missedMoleSoundId = soundPool.load(context, R.raw.missedmole, 1);
        } catch (Exception e) {
            // Catch any exception to see what it is
            Log.e("SoundManager", "Exception during soundPool.load()", e);
        }
    }

    /**
     * Plays the sound for a mole appearing
     */
    public void playActivateSound() {
        if (activateSoundId != 0) {
            soundPool.play(activateSoundId, 1.0f, 1.0f, 0, 0, 1.5f);
        }
    }

    /**
     * Plays the sound of a mole being tapped
     */
    public void playTapMoleSound() {
        if (tapMoleSoundId != 0) {
            soundPool.play(tapMoleSoundId, 1.0f, 1.0f, 0, 0, 1.5f);
        }
    }

    /**
     * Plays the sound of a missed mole
     */
    public void playMissedMoleSound() {
        if (missedMoleSoundId != 0) {
            soundPool.play(missedMoleSoundId, 1.0f, 1.0f, 0, 0, 1.25f);
        }
    }

    /**
     * Releases the sound resources.
     */
    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
