package view;

import framework.GameObserver;
import model.SameGameModel;
import model.TileColor;

public class ConsoleView implements GameObserver {
    private final SameGameModel model;

    public ConsoleView(SameGameModel model) {
        this.model = model;
    }

    @Override
    public void onGameChanged() {
        printBoard();
    }

    public void printBoard() {
        System.out.println("Score: " + model.getScore());

        for (int row = 0; row < model.getRows(); row++) {
            for (int col = 0; col < model.getCols(); col++) {
                TileColor tile = model.getTile(row, col);

                if (tile == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(tile.name().charAt(0) + " ");
                }
            }

            System.out.println();
        }

        if (model.isGameOver()) {
            System.out.println("Game over!");
        }

        System.out.println();
    }
}