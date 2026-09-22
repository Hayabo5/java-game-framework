import controller.MouseController;
import highscore.HighScoreManager;
import model.Difficulty;
import model.SameGameModel;
import sokoban.SokobanApp;
import view.ConsoleView;
import view.SoundObserver;
import view.SwingView;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Main {
    public static void main(String[] args) {
        showGameMenu();
    }

    private static void showGameMenu() {
        JFrame menuFrame = new JFrame("Game Framework");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Choose game:", JLabel.CENTER);
        JButton sameGameButton = new JButton("SameGame");
        JButton sokobanButton = new JButton("Sokoban");

        sameGameButton.addActionListener(e -> {
            menuFrame.dispose();
            showSameGameStartMenu();
        });

        sokobanButton.addActionListener(e -> {
            menuFrame.dispose();
            new SokobanApp(() -> showGameMenu()).startGame();
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(titleLabel);
        panel.add(sameGameButton);
        panel.add(sokobanButton);

        menuFrame.add(panel);
        menuFrame.setSize(300, 170);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }

    private static void showSameGameStartMenu() {
        JFrame startFrame = new JFrame("SameGame - Choose Level");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Choose difficulty level:", JLabel.CENTER);
        JComboBox<Difficulty> difficultyBox = new JComboBox<>(Difficulty.values());
        JButton startButton = new JButton("Start SameGame");
        JButton backButton = new JButton("Back");

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(titleLabel);
        panel.add(difficultyBox);
        panel.add(startButton);
        panel.add(backButton);

        startButton.addActionListener(e -> {
            Difficulty selectedDifficulty = (Difficulty) difficultyBox.getSelectedItem();
            startFrame.dispose();
            startGame(selectedDifficulty);
        });

        backButton.addActionListener(e -> {
            startFrame.dispose();
            showGameMenu();
        });

        startFrame.add(panel);
        startFrame.setSize(300, 190);
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }

    private static void startGame(Difficulty difficulty) {
        SameGameModel model = new SameGameModel(
                difficulty.getRows(),
                difficulty.getCols(),
                difficulty.getNumberOfColors()
        );

        HighScoreManager highScoreManager = new HighScoreManager();

        ConsoleView consoleView = new ConsoleView(model);
        SwingView swingView = new SwingView(model, 50);
        SoundObserver soundObserver = new SoundObserver(model);

        model.addObserver(consoleView);
        model.addObserver(swingView);
        model.addObserver(soundObserver);

        swingView.addMouseListener(new MouseController(model, swingView));

        JFrame frame = new JFrame("SameGame - " + difficulty);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton resetButton = new JButton("Restart");
        JButton hintButton = new JButton("Hint (0 / 5)");
        JButton highScoreButton = new JButton("High Scores");
        JButton changeLevelButton = new JButton("Change Level");
        JButton mainMenuButton = new JButton("Main Menu");

        final int maxHints = 5;
        final int[] usedHints = {0};
        final boolean[] scoreSaved = {false};

        resetButton.addActionListener(e -> {
            model.resetGame();
            usedHints[0] = 0;
            scoreSaved[0] = false;
            hintButton.setText("Hint (0 / 5)");
        });

        hintButton.addActionListener(e -> {
            if (usedHints[0] >= maxHints) {
                JOptionPane.showMessageDialog(frame, "You have used all 5 hints.");
                return;
            }

            if (model.getBestMoveSuggestion() == null) {
                JOptionPane.showMessageDialog(frame, "No possible moves left.");
                return;
            }

            usedHints[0]++;
            hintButton.setText("Hint (" + usedHints[0] + " / " + maxHints + ")");
            swingView.highlightGroup(model.getBestMoveGroup());

            JOptionPane.showMessageDialog(
                    frame,
                    "Hint used: " + usedHints[0] + " / " + maxHints
            );
        });

        highScoreButton.addActionListener(e -> {
            StringBuilder builder = new StringBuilder("High Scores:\n");

            int rank = 1;
            for (int s : highScoreManager.getScores()) {
                builder.append(rank).append(". ").append(s).append("\n");
                rank++;
            }

            if (rank == 1) {
                builder.append("No high scores saved yet.");
            }

            JOptionPane.showMessageDialog(frame, builder.toString());
        });

        changeLevelButton.addActionListener(e -> {
            frame.dispose();
            showSameGameStartMenu();
        });

        mainMenuButton.addActionListener(e -> {
            frame.dispose();
            showGameMenu();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(hintButton);
        buttonPanel.add(highScoreButton);
        buttonPanel.add(changeLevelButton);
        buttonPanel.add(mainMenuButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(swingView, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        consoleView.printBoard();

        new Timer(500, e -> {
            if (model.isGameOver() && !scoreSaved[0]) {
                scoreSaved[0] = true;
                highScoreManager.addScore(model.getScore());

                JOptionPane.showMessageDialog(
                        frame,
                        "Game Over! Score: " + model.getScore()
                );
            }
        }).start();
    }
}