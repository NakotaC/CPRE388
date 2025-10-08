package com.example.whackamole;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

/**
 * This class handles manages the sounds for the game.
 */
public class SoundManager {
    private SoundPool soundPool;
    private int activateSoundId;
    private int deactivateSoundId;
    private Context context;

    /**
     * Constructor for SoundManager.
     * @param context The application context.
     */
    public SoundManager(Context context) {
        this.context = context;

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        loadSounds();
    }

    private void loadSounds() {
        activateSoundId = soundPool.load(context, R.raw.activate, 1);
        deactivateSoundId = soundPool.load(context, R.raw.deactivate, 1);
    }

    /**
     * Plays the mole activate sound.
     */
    public void playActivateSound() {
        if (activateSoundId != 0) {
            soundPool.play(activateSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    /**
     * Plays the mole deactivate sound.
     */
    public void playDeactivateSound() {
        if (deactivateSoundId != 0) {
            soundPool.play(deactivateSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
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
