import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int attemptsLeft;
    private JTextField guessField;
    private JTextArea resultArea;

    public NumberGuessingGame() {
        setTitle("Riya - Number Guessing Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeGame();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel guessLabel = new JLabel("Enter your guess:");
        guessField = new JTextField();
        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());

        JLabel resultLabel = new JLabel("Result:");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        panel.add(guessLabel);
        panel.add(guessField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(guessButton);
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

    private void checkGuess(int guess) {
        attemptsLeft--;

        if (guess == randomNumber) {
            resultArea.setText("Congratulations! You guessed the correct number.");
            saveScore(attemptsLeft);
            initializeGame();
        } else {
            String hint = (guess < randomNumber) ? "Too low" : "Too high";
            resultArea.setText(String.format("%s. Attempts left: %d", hint, attemptsLeft));

            if (attemptsLeft == 0) {
                resultArea.append(String.format("\nSorry, you've run out of attempts. The correct number was %d.", randomNumber));
                initializeGame();
            }
        }

        guessField.setText("");
        guessField.requestFocus();
    }

    private void saveScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) {
            writer.write(String.format("Attempts left: %d%n", score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGame::new);
    }
}
