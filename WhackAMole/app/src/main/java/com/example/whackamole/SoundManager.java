package com.example.whackamole;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundManager {
    private SoundPool soundPool;
    private int activateSoundId;
    private int deactivateSoundId;
    private Context context;

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

    public void playActivateSound() {
        if (activateSoundId != 0) {
            soundPool.play(activateSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public void playDeactivateSound() {
        if (deactivateSoundId != 0) {
            soundPool.play(deactivateSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
