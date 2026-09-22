package highscore;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles saving and loading high scores using Java serialization.
 */
public class HighScoreManager {
    private static final String FILE_NAME = "highscores.ser";
    private final List<Integer> scores;

    public HighScoreManager() {
        scores = loadScores();
    }

    /**
     * Adds a new score and saves the updated high score list.
     *
     * @param score the score to add
     */
    public void addScore(int score) {
        scores.add(score);
        scores.sort(Collections.reverseOrder());

        while (scores.size() > 10) {
            scores.remove(scores.size() - 1);
        }

        saveScores();
    }

    /**
     * Returns the saved high scores.
     *
     * @return list of high scores
     */
    public List<Integer> getScores() {
        return new ArrayList<>(scores);
    }

    private void saveScores() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(scores);
        } catch (IOException e) {
            System.out.println("Could not save high scores.");
        }
    }

    @SuppressWarnings("unchecked")
    private List<Integer> loadScores() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
