package Puzzel_8;

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
    private int emptyIndex = 8;  // Índice del botón vacío (posición inicial)
    private Timer timer;
    private int secondsElapsed = 0;
    private Stack<int[]> forwardStack = new Stack<>();  // Pila para ir hacia adelante
    private Stack<int[]> backwardStack = new Stack<>(); // Pila para ir hacia atrás

    public Puzzle8() {
        setTitle("Juego de Puzzle 8");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Crear panel para el puzzle
        puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(3, 3, 5, 5)); // 3x3 grid layout para el puzzle
        puzzleButtons = new JButton[9];

        // Crear los botones del puzzle
        for (int i = 0; i < 9; i++) {
            puzzleButtons[i] = new JButton();
            puzzleButtons[i].setFont(new Font("Arial", Font.BOLD, 24));
            puzzleButtons[i].addMouseListener(this);          // Eventos de mouse para hacer clic
            puzzlePanel.add(puzzleButtons[i]);
        }

        // Seleccionar la dificultad
        setDifficulty();

        // Crear panel para los botones de control y contador de movimientos
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1)); // Panel con dos filas

        // Panel superior para contador de movimientos y tiempo
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());

        // Etiqueta para mostrar el número de movimientos
        moveCounterLabel = new JLabel("Movimientos: " + moveCount);
        infoPanel.add(moveCounterLabel);

        // Etiqueta para mostrar el tiempo
        timerLabel = new JLabel("Tiempo: 0s");
        infoPanel.add(timerLabel);

        // Agregar el panel de información al panel principal de botones
        buttonPanel.add(infoPanel);

        // Panel inferior para los botones de control
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Botones de control
        btnForward = new JButton("Adelante");
        btnBackward = new JButton("Atrás");
        btnReset = new JButton("Resetear");

        // Agregar escuchadores de eventos a los botones
        btnForward.addActionListener(this);
        btnBackward.addActionListener(this);
        btnReset.addActionListener(this);

        // Agregar botones al panel de control
        controlPanel.add(btnForward);
        controlPanel.add(btnBackward);
        controlPanel.add(btnReset);

        // Agregar el panel de control al panel principal de botones
        buttonPanel.add(controlPanel);

        // Agregar paneles al marco principal
        add(puzzlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Iniciar el temporizador
        startTimer();
    }

    // Método para establecer la dificultad
    private void setDifficulty() {
        String[] options = {"Fácil", "Medio", "Difícil"};
        int difficulty = JOptionPane.showOptionDialog(this, "Selecciona la dificultad:",
                "Dificultad del Juego", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        // Asegurarte de que se seleccionó una dificultad válida
        if (difficulty < 0) {
            difficulty = 0; // Por defecto a fácil si se cancela
        }

        // Desordenar el puzzle con la dificultad seleccionada
        shufflePuzzle(difficulty + 1); // Ajustar para que coincida con el caso en switch (1, 2, 3)
    }

    // Método para actualizar el contador de movimientos
    private void updateMoveCounter() {
        moveCount++;
        moveCounterLabel.setText("Movimientos: " + moveCount);
    }

    // Método para iniciar el temporizador
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
        }, 1000, 1000);  // Inicia después de 1 segundo y se ejecuta cada segundo
    }

    // Método principal para iniciar el juego
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Puzzle8 puzzleFrame = new Puzzle8();
            puzzleFrame.setVisible(true);
        });
    }

    // Implementar acciones de botones
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

    // Método para manejar el avance
    private void avanzar() {
        if (!forwardStack.isEmpty()) {
            int[] move = forwardStack.pop();  // Recuperar el último movimiento hacia adelante
            backwardStack.push(new int[]{emptyIndex, move[0]});  // Guardar el estado actual en backwardStack
            swapButtons(emptyIndex, move[0]);  // Realizar el movimiento visual
            emptyIndex = move[0];  // Actualizar el índice de la casilla vacía
        }
    }

    // Método para manejar el retroceso
    // Método para manejar el retroceso
    private void retroceder() {
        if (!backwardStack.isEmpty()) {
            int[] move = backwardStack.pop();  // Recuperar el último movimiento hacia atrás
            forwardStack.push(new int[]{emptyIndex, move[0]});  // Guardar el estado actual en forwardStack
            swapButtons(emptyIndex, move[0]);  // Realizar el movimiento visual
            emptyIndex = move[0];  // Actualizar el índice de la casilla vacía
        }
    }

    // Método para reiniciar el puzzle
    private void resetPuzzle() {
        moveCount = 0;
        updateMoveCounter();
        startTimer();
        setDifficulty();
        // Limpiar ambas pilas al reiniciar el puzzle
        forwardStack.clear();
        backwardStack.clear();
    }
    // Método para desordenar las fichas del puzzle aleatoriamente
    private void shufflePuzzle(int difficulty) {
        // Definir el número de intercambios según la dificultad
        int numSwaps = 0;
        switch (difficulty) {
            case 1: // Fácil
                numSwaps = 5;
                break;
            case 2: // Medio
                numSwaps = 10;
                break;
            case 3: // Difícil
                numSwaps = 15;
                break;
            default:
                numSwaps = 10; // Valor predeterminado
        }

        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            numbers.add(String.valueOf(i));
        }
        numbers.add(""); // Espacio vacío

        // Realiza el número de intercambios especificado
        for (int i = 0; i < numSwaps; i++) {
            int index1 = (int) (Math.random() * 9);
            int index2 = (int) (Math.random() * 9);
            Collections.swap(numbers, index1, index2);
        }

        for (int i = 0; i < 9; i++) {
            puzzleButtons[i].setText(numbers.get(i));
            if (numbers.get(i).equals("")) {
                emptyIndex = i; // Actualizar el índice de la casilla vacía
            }
        }
    }

    // Método para manejar los clics en las fichas del puzzle
    @Override
    public void mouseClicked(MouseEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int clickedIndex = findButtonIndex(clickedButton);

        // Mover la ficha si es adyacente a la casilla vacía
        if (isAdjacent(clickedIndex, emptyIndex)) {
            backwardStack.push(new int[]{emptyIndex, clickedIndex});  // Guardar el movimiento en backwardStack
            forwardStack.clear();  // Limpiar la pila de forwardStack ya que es un nuevo movimiento
            swapButtons(emptyIndex, clickedIndex);  // Realizar el intercambio visual
            emptyIndex = clickedIndex;  // Actualizar el índice de la casilla vacía
            updateMoveCounter();  // Actualizar el contador de movimientos

            // Verificar si el puzzle está completo
            if (isPuzzleSolved()) {
                timer.cancel();  // Detener el temporizador
                JOptionPane.showMessageDialog(this,
                        "¡Puzzle completado con éxito!\nMovimientos realizados: " + moveCount + "\nTiempo total: " + secondsElapsed + " segundos",
                        "Felicidades", JOptionPane.INFORMATION_MESSAGE);
                resetPuzzle(); // Reiniciar el juego después de cerrar el diálogo
            }
        }
    }

     // Método para encontrar el índice de un botón en el arreglo
     private int findButtonIndex(JButton button) {
        for (int i = 0; i < puzzleButtons.length; i++) {
            if (puzzleButtons[i] == button) {
                return i;
            }
        }
        return -1;
    }

    // Verificar si dos botones son adyacentes (vecinos)
    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / 3, col1 = index1 % 3;
        int row2 = index2 / 3, col2 = index2 % 3;
        return Math.abs(row1 - row2) + Math.abs(col1 - col2) == 1;
    }

    // Intercambiar los botones en el panel
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

    // Métodos vacíos de la interfaz MouseListener
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
