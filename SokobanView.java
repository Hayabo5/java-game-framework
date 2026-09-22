package sokoban;

import framework.GameObserver;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Graphical view for Sokoban.
 */
public class SokobanView extends JPanel implements GameObserver {
    private final SokobanModel model;
    private final int tileSize;

    public SokobanView(SokobanModel model, int tileSize) {
        this.model = model;
        this.tileSize = tileSize;

        setPreferredSize(new Dimension(
                model.getCols() * tileSize,
                model.getRows() * tileSize + 40
        ));

        setFocusable(true);
    }

    @Override
    public void onGameChanged() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < model.getRows(); row++) {
            for (int col = 0; col < model.getCols(); col++) {
                SokobanTile tile = model.getTile(row, col);
                int x = col * tileSize;
                int y = row * tileSize;

                drawTile(g, tile, x, y);
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("Moves: " + model.getScore(), 10, model.getRows() * tileSize + 25);

        if (model.isGameOver()) {
            g.drawString("You won!", 120, model.getRows() * tileSize + 25);
        }
    }

    private void drawTile(Graphics g, SokobanTile tile, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, tileSize, tileSize);

        if (tile == SokobanTile.WALL) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, tileSize, tileSize);
        } else if (tile == SokobanTile.GOAL) {
            g.setColor(Color.PINK);
            g.fillOval(x + 15, y + 15, tileSize - 30, tileSize - 30);
        } else if (tile == SokobanTile.CRATE) {
            g.setColor(new Color(150, 90, 40));
            g.fillRect(x + 8, y + 8, tileSize - 16, tileSize - 16);
        } else if (tile == SokobanTile.PLAYER) {
            g.setColor(Color.BLUE);
            g.fillOval(x + 8, y + 8, tileSize - 16, tileSize - 16);
        }

        g.setColor(Color.BLACK);
        g.drawRect(x, y, tileSize, tileSize);
    }
}