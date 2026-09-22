package view;

import framework.GameObserver;
import framework.Position;
import model.SameGameModel;
import model.TileColor;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class SwingView extends JPanel implements GameObserver {
    private final SameGameModel model;
    private final int tileSize;
    private List<Position> highlightedGroup = new ArrayList<>();

    public SwingView(SameGameModel model, int tileSize) {
        this.model = model;
        this.tileSize = tileSize;

        setPreferredSize(new Dimension(
                model.getCols() * tileSize,
                model.getRows() * tileSize + 40
        ));
    }

    public void highlightGroup(List<Position> group) {
        this.highlightedGroup = group;
        repaint();
    }

    public void clearHighlight() {
        this.highlightedGroup = new ArrayList<>();
        repaint();
    }

    @Override
    public void onGameChanged() {
        clearHighlight();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < model.getRows(); row++) {
            for (int col = 0; col < model.getCols(); col++) {
                TileColor tile = model.getTile(row, col);

                if (tile != null) {
                    g.setColor(tile.getAwtColor());
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);

                    g.setColor(Color.BLACK);
                    g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
                }
            }
        }

        for (Position position : highlightedGroup) {
            int row = position.row();
            int col = position.col();

            g.setColor(Color.BLACK);
            g.drawRect(col * tileSize + 3, row * tileSize + 3, tileSize - 6, tileSize - 6);
            g.drawRect(col * tileSize + 4, row * tileSize + 4, tileSize - 8, tileSize - 8);
            g.drawRect(col * tileSize + 5, row * tileSize + 5, tileSize - 10, tileSize - 10);
        }

        g.setColor(Color.BLACK);
        g.drawString("Score: " + model.getScore(), 10, model.getRows() * tileSize + 25);

        if (model.isGameOver()) {
            g.drawString("Game Over!", 120, model.getRows() * tileSize + 25);
        }
    }

    public int getTileSize() {
        return tileSize;
    }
}