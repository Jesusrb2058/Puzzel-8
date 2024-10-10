package Puzzle_8;
  

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Puzzle8 extends JFrame implements ActionListener, MouseListener {
    private JPanel puzzlePanel, buttonPanel;
    private JButton[] puzzleButtons;
    private JButton btnForward, btnBackward, btnReset;
    private JLabel moveCounterLabel, timerLabel;
    private int moveCount = 0;
    private int emptyIndex = 8; 
    private Timer timer;
    private int secondsElapsed = 0;
    private Stack<int[]> forwardStack = new Stack<>();  
    private Stack<int[]> backwardStack = new Stack<>(); 

    public Puzzle8() {
        setTitle("Juego de Puzzle 8");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

    
        puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(3, 3, 5, 5)); 
        puzzleButtons = new JButton[9];

        
        for (int i = 0; i < 9; i++) {
            puzzleButtons[i] = new JButton();
            puzzleButtons[i].setFont(new Font("Arial", Font.BOLD, 24));
            puzzleButtons[i].addMouseListener(this);  
            puzzlePanel.add(puzzleButtons[i]);
        }

        
        setDifficulty();

        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1)); 

        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());

        
        moveCounterLabel = new JLabel("Movimientos: " + moveCount);
        infoPanel.add(moveCounterLabel);

        
        timerLabel = new JLabel("Tiempo: 0s");
        infoPanel.add(timerLabel);

        
        buttonPanel.add(infoPanel);

        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

       
        btnForward = new JButton("Adelante");
        btnBackward = new JButton("Atrás");
        btnReset = new JButton("Resetear");

       
        btnForward.addActionListener(this);
        btnBackward.addActionListener(this);
        btnReset.addActionListener(this);

       
        controlPanel.add(btnForward);
        controlPanel.add(btnBackward);
        controlPanel.add(btnReset);

        
        buttonPanel.add(controlPanel);

        
        add(puzzlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        
        startTimer();
    }

   
    private void setDifficulty() {
        String[] options = {"Fácil", "Medio", "Difícil"};
        int difficulty = JOptionPane.showOptionDialog(this, "Selecciona la dificultad:",
                "Dificultad del Juego", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

    
        if (difficulty < 0) {
            difficulty = 0; 
        }

       
        shufflePuzzle(difficulty + 1); 
    }

    
    private void updateMoveCounter() {
        moveCount++;
        moveCounterLabel.setText("Movimientos: " + moveCount);
    }

   
    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        secondsElapsed = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
                SwingUtilities.invokeLater(() -> timerLabel.setText("Tiempo: " + secondsElapsed + "s"));
            }
        }, 1000, 1000);  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnForward) {
            avanzar();
        } else if (e.getSource() == btnBackward) {
            retroceder();
        } else if (e.getSource() == btnReset) {
            resetPuzzle();
        }
    }

    
    private void avanzar() {
        if (!forwardStack.isEmpty()) {
            int[] move = forwardStack.pop();  
            backwardStack.push(new int[]{emptyIndex, move[0]});  
            swapButtons(emptyIndex, move[0]);  
            emptyIndex = move[0];  
        }
    }


    private void retroceder() {
        if (!backwardStack.isEmpty()) {
            int[] move = backwardStack.pop();  
            forwardStack.push(new int[]{emptyIndex, move[0]}); 
            swapButtons(emptyIndex, move[0]);  
            emptyIndex = move[0];  
        }
    }

  
    private void resetPuzzle() {
        moveCount = 0;
        updateMoveCounter();
        startTimer();
        setDifficulty();
       
        forwardStack.clear();
        backwardStack.clear();
    }
   
    private void shufflePuzzle(int difficulty) {
        
        int numSwaps = 0;
        switch (difficulty) {
            case 1: 
                numSwaps = 5;
                break;
            case 2: 
                numSwaps = 10;
                break;
            case 3: 
                numSwaps = 15;
                break;
            default:
                numSwaps = 10; 
        }

        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            numbers.add(String.valueOf(i));
        }
        numbers.add(""); 

        for (int i = 0; i < numSwaps; i++) {
            int index1 = (int) (Math.random() * 9);
            int index2 = (int) (Math.random() * 9);
            Collections.swap(numbers, index1, index2);
        }

        for (int i = 0; i < 9; i++) {
            puzzleButtons[i].setText(numbers.get(i));
            if (numbers.get(i).equals("")) {
                emptyIndex = i; 
            }
        }
    }

   
    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int clickedIndex = findButtonIndex(clickedButton);

       
        if (isAdjacent(clickedIndex, emptyIndex)) {
            backwardStack.push(new int[]{emptyIndex, clickedIndex});  
            forwardStack.clear(); 
            swapButtons(emptyIndex, clickedIndex);  
            emptyIndex = clickedIndex;  
            updateMoveCounter();  

           
            if (isPuzzleSolved()) {
                timer.cancel();  
                JOptionPane.showMessageDialog(this,
                        "¡Puzzle completado con éxito!\nMovimientos realizados: " + moveCount + "\nTiempo total: " + secondsElapsed + " segundos",
                        "Felicidades", JOptionPane.INFORMATION_MESSAGE);
                resetPuzzle(); 
            }
        }
    }

    
     private int findButtonIndex(JButton button) {
        for (int i = 0; i < puzzleButtons.length; i++) {
            if (puzzleButtons[i] == button) {
                return i;
            }
        }
        return -1;
    }

   
    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / 3, col1 = index1 % 3;
        int row2 = index2 / 3, col2 = index2 % 3;
        return Math.abs(row1 - row2) + Math.abs(col1 - col2) == 1;
    }

   
    private void swapButtons(int idx1, int idx2) {
        String tempText = puzzleButtons[idx1].getText();
        puzzleButtons[idx1].setText(puzzleButtons[idx2].getText());
        puzzleButtons[idx2].setText(tempText);
    }

    // Verificar si el puzzle está resuelto
    private boolean isPuzzleSolved() {
        for (int i = 0; i < 8; i++) {
            if (!puzzleButtons[i].getText().equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void run() {
        this.setVisible(true);
    }
    
}
