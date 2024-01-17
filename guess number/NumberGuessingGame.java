import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int attemptsLeft;
    private JTextField guessField;
    private JLabel resultLabel;

    public NumberGuessingGame() {
        setTitle("Riya - Number Guessing Game");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeGame();

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 32, 63));
      
        JLabel guessLabel = new JLabel("Enter your guess:");
        guessLabel.setBounds(100, 30, 270, 50);
        guessLabel.setFont(new Font("Arial", Font.BOLD, 20));
        guessLabel.setForeground(new Color(173, 239, 209));

        guessField = new JTextField();
        guessField.setBounds(350, 30, 70, 45);
        guessField.setFont(new Font("Arial", Font.BOLD, 20));

        JButton guessButton = createStyledButton("GUESS");
        guessButton.setBounds(70, 120, 200, 50);
        guessButton.addActionListener(new GuessButtonListener());

        JButton playAgainButton = createStyledButton("Play Again");
        playAgainButton.setBounds(300, 120, 200, 50);
        playAgainButton.addActionListener(new PlayAgainButtonListener());

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(20, 190, 560, 10);

        resultLabel = new JLabel();
        resultLabel.setBounds(50, 200, 500, 50);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 15));
        resultLabel.setForeground(new Color(173, 239, 209));

        panel.add(guessLabel);
        panel.add(guessField);
        panel.add(guessButton);
        panel.add(playAgainButton);
        panel.add(separator);
        panel.add(resultLabel);

        add(panel);
        setVisible(true);
    }

    private void initializeGame() {
        randomNumber = (int) (Math.random() * 100) + 1;
        attemptsLeft = 10;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255,231, 122));
        button.setForeground(new Color(44, 95, 45));
        button.setFont(new Font("Arial", Font.BOLD, 25));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255,231, 122));
                button.setBackground(new Color(44, 95, 45));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255,231, 122));
                button.setForeground(new Color(44, 95, 45));
            }
        });

        return button;
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                checkGuess(guess);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input. Please enter a number.");
            }
        }
    }

    private class PlayAgainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            initializeGame();
            guessField.setEnabled(true);
            resultLabel.setText("");
            guessField.setText("");
            guessField.requestFocus();
        }
    }

    private void checkGuess(int guess) {
        attemptsLeft--;

        if (guess == randomNumber) {
            resultLabel.setText("Congratulations! You guessed the correct number.");
            saveScore(attemptsLeft, randomNumber);
            initializeGame();
            guessField.setEnabled(false);
        } else {
            int nearThreshold = 2; // Adjust this threshold as needed
            int difference = Math.abs(randomNumber - guess);

            String hint;
            if (difference <= nearThreshold) {
                hint = "You are near";
            } else {
                hint = (guess < randomNumber) ? "Too low" : "Too high";
            }

            resultLabel.setText(String.format("%s. Attempts left: %d", hint, attemptsLeft));

            if (attemptsLeft == 0) {
                resultLabel.setText(
                        String.format("Sorry, you've run out of attempts. The correct number was %d.", randomNumber));
                saveScore(attemptsLeft, randomNumber);
                initializeGame();
                guessField.setEnabled(false);
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