package sokoban;

/**
 * Represents movement directions in Sokoban.
 */
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int rowChange() {
        return rowChange;
    }

    public int colChange() {
        return colChange;
    }
}