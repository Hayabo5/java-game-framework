package sokoban;

import framework.ConsoleView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;

public class SokobanApp {
    private final Runnable returnToMenu;

    public SokobanApp(Runnable returnToMenu) {
        this.returnToMenu = returnToMenu;
    }

    public void startGame() {
        SokobanModel model = new SokobanModel();

        ConsoleView<SokobanTile> consoleView = new ConsoleView<>(model);
        SokobanView view = new SokobanView(model, 50);

        model.addObserver(consoleView);
        model.addObserver(view);

        JFrame frame = new JFrame("Sokoban");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton resetButton = new JButton("Restart");
        JButton menuButton = new JButton("Game Menu");

        resetButton.addActionListener(e -> {
            model.resetGame();
            view.requestFocusInWindow();
        });

        menuButton.addActionListener(e -> {
            frame.dispose();
            returnToMenu.run();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(menuButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(view, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.addKeyListener(new SokobanController(model));
        view.requestFocusInWindow();

        consoleView.printBoard();

        final boolean[] messageShown = {false};

        new Timer(300, e -> {
            if (model.isGameOver() && !messageShown[0]) {
                messageShown[0] = true;

                if (model.hasWon()) {
                    JOptionPane.showMessageDialog(frame, "You won!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Game Over! The box is stuck.");
                }
            }
        }).start();
    }
}