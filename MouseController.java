package controller;

import model.SameGameModel;
import view.SwingView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController extends MouseAdapter {
    private final SameGameModel model;
    private final SwingView view;

    public MouseController(SameGameModel model, SwingView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int tileSize = view.getTileSize();

        int col = e.getX() / tileSize;
        int row = e.getY() / tileSize;

        model.selectTile(row, col);
    }
}