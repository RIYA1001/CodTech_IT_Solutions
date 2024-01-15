import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int attemptsLeft;
    private JTextField guessField;
    private JTextArea resultArea;

    public NumberGuessingGame() {
        setTitle("Riya - Number Guessing Game");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeGame();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(238, 156, 167);
                Color color2 = new Color(255, 221, 225);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        panel.setLayout(null);

        JLabel guessLabel = new JLabel("Enter your guess:");
        guessLabel.setBounds(100, 30, 150, 30);

        guessField = new JTextField();
        guessField.setBounds(250, 30, 50, 30);

        JButton guessButton = new JButton("GUESS");
        guessButton.setBounds(100, 80, 200, 50);
        guessButton.setBackground(new Color(152, 251, 152)); // Peace color
        guessButton.setFont(new Font("Arial", Font.BOLD, 25));
        guessButton.addActionListener(new GuessButtonListener());

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(250, 80, 200, 50);
        playAgainButton.setBackground(new Color(173, 216, 230)); // Light blue color
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 25)); // Bold text
        playAgainButton.addActionListener(new PlayAgainButtonListener());

        // Horizontal line
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(0, 140, 500, 10);

        JLabel resultLabel = new JLabel("Result");
        resultLabel.setBounds(180, 150, 50, 30);

        resultArea = new JTextArea();
        resultArea.setBounds(50, 180, 300, 30);
        resultArea.setEditable(false);

        panel.add(guessLabel);
        panel.add(guessField);
        panel.add(guessButton);
        panel.add(playAgainButton);
        panel.add(separator);
        panel.add(resultLabel);
        panel.add(resultArea);

        add(panel);

        setVisible(true);
    }

    private void initializeGame() {
        randomNumber = (int) (Math.random() * 100) + 1;
        attemptsLeft = 10;
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                checkGuess(guess);
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid input. Please enter a number.");
            }
        }
    }

    private class PlayAgainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            initializeGame();
            resultArea.setText("");
            guessField.setText("");
            guessField.requestFocus();
        }
    }

    private void checkGuess(int guess) {
        attemptsLeft--;

        if (guess == randomNumber) {
            resultArea.setText("Congratulations! You guessed the correct number.");
            saveScore(attemptsLeft, randomNumber);
            initializeGame();
        } else {
            String hint = (guess < randomNumber) ? "Too low" : "Too high";
            resultArea.setText(String.format("%s. Attempts left: %d", hint, attemptsLeft));

            if (attemptsLeft == 0) {
                resultArea.append(
                        String.format("\nSorry, you've run out of attempts. The correct number was %d.", randomNumber));
                saveScore(attemptsLeft, randomNumber); // Save even if the player runs out of attempts
                initializeGame();
            }
        }

        guessField.setText("");
        guessField.requestFocus();
    }

    private void saveScore(int score, int correctNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) {
            writer.write(String.format("Attempts left: %d, Correct Number: %d%n", score, correctNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGame::new);
    }
}