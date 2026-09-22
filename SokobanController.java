package sokoban;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Keyboard controller for Sokoban.
 */
public class SokobanController extends KeyAdapter {
    private final SokobanModel model;

    public SokobanController(SokobanModel model) {
        this.model = model;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            model.move(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            model.move(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            model.move(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            model.move(Direction.RIGHT);
        }
    }
}