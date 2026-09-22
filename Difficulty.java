package model;

public enum Difficulty {
    EASY("Easy", 8, 8, 3),
    MEDIUM("Medium", 10, 10, 4),
    HARD("Hard", 12, 12, 6);

    private final String name;
    private final int rows;
    private final int cols;
    private final int numberOfColors;

    Difficulty(String name, int rows, int cols, int numberOfColors) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.numberOfColors = numberOfColors;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    @Override
    public String toString() {
        return name;
    }
}
