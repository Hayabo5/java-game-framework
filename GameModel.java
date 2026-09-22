package framework;

/**
 * Common interface for grid-based games.
 * Both SameGame and Sokoban can use this interface.
 */
public interface GameModel<T> {
    int getRows();

    int getCols();

    T getTile(int row, int col);

    int getScore();

    boolean isGameOver();

    void resetGame();

    void addObserver(GameObserver observer);

    void removeObserver(GameObserver observer);
}