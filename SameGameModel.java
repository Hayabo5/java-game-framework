package model;

import framework.AbstractGridGameModel;
import framework.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SameGameModel extends AbstractGridGameModel<TileColor> {
    private final int numberOfColors;

    public SameGameModel(int rows, int cols, int numberOfColors) {
        super(rows, cols);
        this.numberOfColors = numberOfColors;
        setupNewGame();
    }

    @Override
    protected void setupNewGame() {
        Random random = new Random();
        TileColor[] colors = TileColor.values();

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                board[row][col] = colors[random.nextInt(numberOfColors)];
            }
        }
    }

    public void selectTile(int row, int col) {
        if (gameOver || !isInsideBoard(row, col)) {
            return;
        }

        TileColor selectedColor = (TileColor) board[row][col];

        if (selectedColor == null) {
            return;
        }

        List<Position> group = findConnectedGroup(row, col, selectedColor);

        if (group.size() < 2) {
            return;
        }

        removeGroup(group);
        applyGravity();
        shiftColumnsLeft();

        score += group.size() * group.size();

        if (!hasAvailableMoves()) {
            gameOver = true;
        }

        notifyObservers();
    }

    public Position getBestMoveSuggestion() {
        Position bestPosition = null;
        int bestSize = 1;
        boolean[][] checked = new boolean[getRows()][getCols()];

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                TileColor color = (TileColor) board[row][col];

                if (color == null || checked[row][col]) {
                    continue;
                }

                List<Position> group = findConnectedGroup(row, col, color);

                for (Position position : group) {
                    checked[position.row()][position.col()] = true;
                }

                if (group.size() > bestSize) {
                    bestSize = group.size();
                    bestPosition = new Position(row, col);
                }
            }
        }

        return bestPosition;
    }

    public List<Position> getBestMoveGroup() {
        Position suggestion = getBestMoveSuggestion();

        if (suggestion == null) {
            return new ArrayList<>();
        }

        TileColor color = (TileColor) board[suggestion.row()][suggestion.col()];
        return findConnectedGroup(suggestion.row(), suggestion.col(), color);
    }

    private List<Position> findConnectedGroup(int startRow, int startCol, TileColor color) {
        List<Position> group = new ArrayList<>();
        boolean[][] visited = new boolean[getRows()][getCols()];

        search(startRow, startCol, color, visited, group);

        return group;
    }

    private void search(int row, int col, TileColor color, boolean[][] visited, List<Position> group) {
        if (!isInsideBoard(row, col)) {
            return;
        }

        if (visited[row][col]) {
            return;
        }

        if (board[row][col] != color) {
            return;
        }

        visited[row][col] = true;
        group.add(new Position(row, col));

        search(row - 1, col, color, visited, group);
        search(row + 1, col, color, visited, group);
        search(row, col - 1, color, visited, group);
        search(row, col + 1, color, visited, group);
    }

    private void removeGroup(List<Position> group) {
        for (Position position : group) {
            board[position.row()][position.col()] = null;
        }
    }

    private void applyGravity() {
        for (int col = 0; col < getCols(); col++) {
            int writeRow = getRows() - 1;

            for (int row = getRows() - 1; row >= 0; row--) {
                if (board[row][col] != null) {
                    board[writeRow][col] = board[row][col];

                    if (writeRow != row) {
                        board[row][col] = null;
                    }

                    writeRow--;
                }
            }
        }
    }

    private void shiftColumnsLeft() {
        int writeCol = 0;

        for (int col = 0; col < getCols(); col++) {
            if (!isColumnEmpty(col)) {
                if (writeCol != col) {
                    moveColumn(col, writeCol);
                    clearColumn(col);
                }

                writeCol++;
            }
        }
    }

    private boolean isColumnEmpty(int col) {
        for (int row = 0; row < getRows(); row++) {
            if (board[row][col] != null) {
                return false;
            }
        }

        return true;
    }

    private void moveColumn(int fromCol, int toCol) {
        for (int row = 0; row < getRows(); row++) {
            board[row][toCol] = board[row][fromCol];
        }
    }

    private void clearColumn(int col) {
        for (int row = 0; row < getRows(); row++) {
            board[row][col] = null;
        }
    }

    private boolean hasAvailableMoves() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                TileColor color = (TileColor) board[row][col];

                if (color == null) {
                    continue;
                }

                if (isInsideBoard(row + 1, col) && board[row + 1][col] == color) {
                    return true;
                }

                if (isInsideBoard(row, col + 1) && board[row][col + 1] == color) {
                    return true;
                }
            }
        }

        return false;
    }
}