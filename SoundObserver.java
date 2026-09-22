package view;

import framework.GameObserver;
import model.SameGameModel;

import java.awt.Toolkit;

/**
 * Observer that reacts to game state changes by producing a simple sound.
 * This observer is separate from the visual views.
 */
public class SoundObserver implements GameObserver {
    private final SameGameModel model;
    private int previousScore;

    public SoundObserver(SameGameModel model) {
        this.model = model;
        this.previousScore = model.getScore();
    }

    @Override
    public void onGameChanged() {
        if (model.getScore() > previousScore) {
            Toolkit.getDefaultToolkit().beep();
        }

        previousScore = model.getScore();
    }
}