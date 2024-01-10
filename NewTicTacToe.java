import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewTicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();

    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();

    JButton[] buttons = new JButton[9];
    JButton resetButton = new JButton("Reset");
    JButton exitButton = new JButton("Exit");
    JButton vsComputerButton = new JButton("Computer vs Player");
    JButton vsPlayerButton = new JButton("Player 1 vs Player 2");

    JLabel textfield = new JLabel();
    JLabel player1ScoreLabel = new JLabel("Player 1 (X): 0");
    JLabel player2ScoreLabel = new JLabel("Player 2 (O): 0");

    boolean vsComputerMode = false;
    boolean player1_turn;
    Map<String, Integer> scoreboard = new HashMap<>();

    NewTicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(490, 685);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 35));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);
        int verticalGap = 20;
        textfield.setBorder(BorderFactory.createEmptyBorder(verticalGap, 0, verticalGap, 0));


        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);
        

        // for X & O

        button_panel.setLayout(new GridLayout(3, 3));
        JPanel leftEmptyPanel = new JPanel();
        JPanel rightEmptyPanel = new JPanel();
        leftEmptyPanel.setBackground(Color.BLACK);
        rightEmptyPanel.setBackground(Color.BLACK);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 100));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
     
        frame.add(leftEmptyPanel, BorderLayout.WEST);
        
        frame.add(button_panel, BorderLayout.CENTER);

        frame.add(rightEmptyPanel, BorderLayout.EAST);
        

        // for title

        title_panel.add(textfield, BorderLayout.NORTH);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
           

        // for score board

        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);
        scorePanel.setBackground(Color.BLACK);
        title_panel.add(scorePanel, BorderLayout.SOUTH);
        int vertiGap = 10;
        scorePanel.setBorder(BorderFactory.createEmptyBorder(vertiGap, 0, vertiGap, 0));


        // for reset and exit button

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setForeground(Color.PINK);
        resetButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.PINK);
        exitButton.setBackground(Color.BLACK);
        bottomPanel.add(resetButton);
        bottomPanel.add(exitButton);
        bottomPanel.setBackground(Color.BLACK);
        int vGap = 5;
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(vGap, 0, vGap, 0));


        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        resetButton.addActionListener(this);
        exitButton.addActionListener(this);
        

        // for vs button

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        vsComputerButton.setFont(new Font("Arial", Font.BOLD, 20));
        vsPlayerButton.setFont(new Font("Arial", Font.BOLD, 20));
        vsComputerButton.setForeground(Color.YELLOW);
        vsPlayerButton.setForeground(Color.YELLOW);
        vsComputerButton.setBackground(Color.BLACK);
        vsPlayerButton.setBackground(Color.BLACK);
        buttonPanel.add(vsPlayerButton);
        buttonPanel.add(vsComputerButton);
        buttonPanel.setBackground(Color.BLACK);

        title_panel.add(buttonPanel, BorderLayout.CENTER);
   
        vsComputerButton.addActionListener(this);
        vsPlayerButton.addActionListener(this);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetGame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == vsComputerButton) {
            vsComputerMode = true;
            resetGame();
            textfield.setText("Player (X) vs Computer (O)");
            if (!player1_turn) {
                computerMove();
            }
        } else if (e.getSource() == vsPlayerButton) {
            vsComputerMode = false;
            resetGame();
            textfield.setText("Player 1 (X) vs Player 2 (O)");
        } else {
            for (int i = 0; i < 9; i++) {
                if (e.getSource() == buttons[i]) {
                    if (vsComputerMode) {
                        handleVsComputerMove(i);
                    } else {
                        handleVsPlayerMove(i);
                    }
                }
            }
        }
    }

    private void handleVsComputerMove(int index) {
        if (buttons[index].getText().equals("")) {
            buttons[index].setForeground(new Color(255, 0, 0));
            buttons[index].setText("X");
            player1_turn = true;
            textfield.setText("Player (X) turn");
            check();
            if (!player1_turn) {
                computerMove();
                player1_turn = true; // Corrected line
                textfield.setText("Player (X) turn");
            }
        }
    }

    private void handleVsPlayerMove(int index) {
        if (player1_turn && buttons[index].getText().equals("")) {
            buttons[index].setForeground(new Color(255, 0, 0));
            buttons[index].setText("X");
            player1_turn = false;
            textfield.setText("Player 2 (O) turn");
            check();
        } else if (!player1_turn && buttons[index].getText().equals("")) {
            buttons[index].setForeground(new Color(0, 0, 255));
            buttons[index].setText("O");
            player1_turn = true;
            textfield.setText("Player 1 (X) turn");
            check();
        }
    }


    
    private void computerMove() {
        int index = -1;

        // Check for an opportunity to win
        for (int[] winCondition : WIN_CONDITIONS) {
            int count = 0;
            for (int i : winCondition) {
                if (buttons[i].getText().equals("O")) {
                    count++;
                } else if (buttons[i].getText().equals("X")) {
                    break;
                } else {
                    index = i;
                }
            }
            if (count == 2 && index != -1) {
                break;
            }
        }

        // If no opportunity to win, block the opponent
        if (index == -1) {
            for (int[] winCondition : WIN_CONDITIONS) {
                int count = 0;
                for (int i : winCondition) {
                    if (buttons[i].getText().equals("X")) {
                        count++;
                    } else if (buttons[i].getText().equals("O")) {
                        break;
                    } else {
                        index = i;
                    }
                }
                if (count == 2 && index != -1) {
                    break;
                }
            }
        }

        // If still no move, make a random move
        if (index == -1) {
            do {
                index = random.nextInt(9);
            } while (!buttons[index].getText().equals(""));
        }

        buttons[index].setForeground(new Color(0, 0, 255));
        buttons[index].setText("O");
        player1_turn = true;
        textfield.setText("Player 1 (X) turn");
        check();
    }



    public void firstTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("Player 1 (X) turn");
        } else {
            player1_turn = false;
            textfield.setText("Player 2 (O) turn");
            if (!player1_turn && vsComputerMode) {
                computerMove();
            }
        }
    }

    public void check() {
        boolean draw = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals("")) {
                draw = false;
                break;
            }
        }

        if (draw) {
            textfield.setText("Draw");
            disableButtons();
        } else {
            if (vsComputerMode) {
                checkForWin("O");
            } else {
                checkForWin("X");
                checkForWin("O");
            }
        }
    }

    private void checkForWin(String symbol) {
        for (int[] winCondition : WIN_CONDITIONS) {
            if (buttons[winCondition[0]].getText().equals(symbol) &&
                    buttons[winCondition[1]].getText().equals(symbol) &&
                    buttons[winCondition[2]].getText().equals(symbol)) {
                highlightWinningCells(winCondition);
                updateScore(symbol);
                return;
            }
        }
    }

    private void highlightWinningCells(int[] winCondition) {
        for (int index : winCondition) {
            buttons[index].setBackground(Color.YELLOW);
        }
        disableButtons();
        textfield.setText(symbolToPlayer.get(buttons[winCondition[0]].getText()) + " wins");
    }

    private void disableButtons() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
    }

    public void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(null);
        }
        firstTurn();
    }

    public void updateScore(String winner) {
        scoreboard.put(winner, scoreboard.getOrDefault(winner, 0) + 1);
        updateScoreLabels();
    }

    public void updateScoreLabels() {
        player1ScoreLabel.setText("Player 1 (X): " + scoreboard.getOrDefault("X", 0));
        player2ScoreLabel.setText("Player 2 (O): " + scoreboard.getOrDefault("O", 0));
        player1ScoreLabel.setForeground(Color.WHITE);
        player2ScoreLabel.setForeground(Color.WHITE);
    }

    private static final int[][] WIN_CONDITIONS = {
            { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 }
    };

    private final Map<String, String> symbolToPlayer = Map.of("X", "Player 1", "O", "Player 2");

    public static void main(String[] args) {
        new NewTicTacToe();
    }
}