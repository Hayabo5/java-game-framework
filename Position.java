package framework;

import java.util.Objects;

/**
 * Represents a position in a grid (row, column).
 * This class is part of the framework and reused by all games.
 */
public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public Position movedBy(int rowChange, int colChange) {
        return new Position(row + rowChange, col + colChange);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;
        Position other = (Position) o;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}