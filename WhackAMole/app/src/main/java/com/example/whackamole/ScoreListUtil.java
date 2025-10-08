package com.example.whackamole;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements methods for saving and loading high scores.
 */
public class ScoreListUtil {

    private static final String HIGHSCORES_FILENAME = "highscores.txt";
    private static List<Integer> scoreList = new ArrayList<>();

    /**
     * Saves the high scores to a file. Only saves the 5 highest scores.
     * @param context The context of the activity.
     * @param score The score to save.
     */
    public static void saveHighScores(Context context, Integer score) {
        FileOutputStream fos = null;

        scoreList.add(score);
        scoreList.sort(Collections.reverseOrder());
        try {
            fos = context.openFileOutput(HIGHSCORES_FILENAME, Context.MODE_PRIVATE);

            // Determine how many scores to write (up to 5 or the list size if smaller).
            int scoresToWrite = Math.min(scoreList.size(), 5);

            // Loop through the sorted list and write each score.
            for (int i = 0; i < scoresToWrite; i++) {
                // Get the score from the list at the current index.
                Integer scoreFromList = scoreList.get(i);
                fos.write((scoreFromList.toString() + "\n").getBytes());
            }
        } catch (Exception e) {
            Log.e("ScoreListUtil", "Error saving high scores", e);

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    Log.e("ScoreListUtil", "Error closing file output stream", e);
                }
            }
        }
    }

    /**
     * Loads the high scores from a file.
     * @param context The context of the activity.
     * @return A list of the high scores.
     */
    public static List<Integer> loadHighScores(Context context) {
        FileInputStream fis = null;
        List<Integer> scores = null;
        try {
            fis = context.openFileInput(HIGHSCORES_FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            scores = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(Integer.parseInt(line));
            }
        } catch (Exception e) {
            Log.e("ScoreListUtil", "Error loading high scores", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    Log.e("ScoreListUtil", "Error closing file input stream", e);
                }
            }
        }
        if (scores != null) {
            scores.sort(Collections.reverseOrder());
            scoreList = scores;
            return scores;
        } else {
            return null;
        }
    }
}
