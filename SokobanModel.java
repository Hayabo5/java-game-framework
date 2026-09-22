package sokoban;

import framework.AbstractGridGameModel;
import framework.Position;

public class SokobanModel extends AbstractGridGameModel<SokobanTile> {
    private static final String[] LEVEL = {
            "########",
            "#      #",
            "#   .  #",
            "#   $  #",
            "#   @  #",
            "#      #",
            "########"
    };

    private Position playerPosition;
    private boolean won;

    public SokobanModel() {
        super(LEVEL.length, LEVEL[0].length());
        setupNewGame();
    }

    @Override
    protected void setupNewGame() {
        clearBoard();
        won = false;

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                char c = LEVEL[row].charAt(col);

                switch (c) {
                    case '#':
                        board[row][col] = SokobanTile.WALL;
                        break;
                    case '.':
                        board[row][col] = SokobanTile.GOAL;
                        break;
                    case '$':
                        board[row][col] = SokobanTile.CRATE;
                        break;
                    case '@':
                        board[row][col] = SokobanTile.PLAYER;
                        playerPosition = new Position(row, col);
                        break;
                    default:
                        board[row][col] = SokobanTile.BLANK;
                        break;
                }
            }
        }
    }

    public void move(Direction direction) {
        if (gameOver) {
            return;
        }

        Position next = playerPosition.movedBy(direction.rowChange(), direction.colChange());

        if (!isInsideBoard(next.row(), next.col())) {
            return;
        }

        SokobanTile nextTile = (SokobanTile) board[next.row()][next.col()];

        if (nextTile == SokobanTile.WALL) {
            return;
        }

        if (isCrate(nextTile)) {
            Position behind = next.movedBy(direction.rowChange(), direction.colChange());

            if (!isInsideBoard(behind.row(), behind.col())) {
                return;
            }

            SokobanTile behindTile = (SokobanTile) board[behind.row()][behind.col()];

            if (behindTile == SokobanTile.WALL || isCrate(behindTile)) {
                return;
            }

            moveCrate(next, behind);
        }

        movePlayer(next);
        score++;

        if (checkWin()) {
            won = true;
            gameOver = true;
        } else if (checkLose()) {
            won = false;
            gameOver = true;
        }

        notifyObservers();
    }

    public boolean hasWon() {
        return won;
    }

    private boolean isCrate(SokobanTile tile) {
        return tile == SokobanTile.CRATE || tile == SokobanTile.CRATE_ON_GOAL;
    }

    private void moveCrate(Position from, Position to) {
        SokobanTile targetTile = (SokobanTile) board[to.row()][to.col()];

        if (targetTile == SokobanTile.GOAL) {
            board[to.row()][to.col()] = SokobanTile.CRATE_ON_GOAL;
        } else {
            board[to.row()][to.col()] = SokobanTile.CRATE;
        }

        SokobanTile oldCrateTile = (SokobanTile) board[from.row()][from.col()];

        if (oldCrateTile == SokobanTile.CRATE_ON_GOAL) {
            board[from.row()][from.col()] = SokobanTile.GOAL;
        } else {
            board[from.row()][from.col()] = SokobanTile.BLANK;
        }
    }

    private void movePlayer(Position newPosition) {
        SokobanTile oldPlayerTile = (SokobanTile) board[playerPosition.row()][playerPosition.col()];

        if (oldPlayerTile == SokobanTile.PLAYER_ON_GOAL) {
            board[playerPosition.row()][playerPosition.col()] = SokobanTile.GOAL;
        } else {
            board[playerPosition.row()][playerPosition.col()] = SokobanTile.BLANK;
        }

        SokobanTile targetTile = (SokobanTile) board[newPosition.row()][newPosition.col()];

        if (targetTile == SokobanTile.GOAL) {
            board[newPosition.row()][newPosition.col()] = SokobanTile.PLAYER_ON_GOAL;
        } else {
            board[newPosition.row()][newPosition.col()] = SokobanTile.PLAYER;
        }

        playerPosition = newPosition;
    }

    private boolean checkWin() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                if (board[row][col] == SokobanTile.CRATE) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkLose() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                SokobanTile tile = (SokobanTile) board[row][col];

                if (tile == SokobanTile.CRATE) {
                    if (isStuckInCorner(row, col)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isStuckInCorner(int row, int col) {
        boolean up = isWall(row - 1, col);
        boolean down = isWall(row + 1, col);
        boolean left = isWall(row, col - 1);
        boolean right = isWall(row, col + 1);

        return (up && left) || (up && right) || (down && left) || (down && right);
    }

    private boolean isWall(int row, int col) {
        if (!isInsideBoard(row, col)) {
            return true;
        }

        return board[row][col] == SokobanTile.WALL;
    }
}