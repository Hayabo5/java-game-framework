package framework;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGridGameModel<T> implements GameModel<T> {
    private final int rows;
    private final int cols;
    protected final Object[][] board;
    private final List<GameObserver> observers;
    protected int score;
    protected boolean gameOver;

    public AbstractGridGameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Object[rows][cols];
        this.observers = new ArrayList<>();
        this.score = 0;
        this.gameOver = false;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getTile(int row, int col) {
        return (T) board[row][col];
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onGameChanged();
        }
    }

    protected boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    protected void clearBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = null;
            }
        }
    }

    protected abstract void setupNewGame();

    @Override
    public void resetGame() {
        score = 0;
        gameOver = false;
        setupNewGame();
        notifyObservers();
    }
}