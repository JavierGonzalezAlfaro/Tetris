package tetris.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import tetris.model.Board;
import tetris.model.Figure;
import tetris.model.Sound;
import tetris.persistence.FigureLoader;

public class SwingBoardViewer {

    private JLabel ImageLabel = new JLabel();
    private JLabel scoreLabel = new JLabel("0");
    private JLabel levelLabel = new JLabel("1");
    private Board board;
    private int count = 0;
    private JButton[][] buttonBoard;
    private JFrame frame;
    private Figure figure;
    private Figure nextFigure;
    private FigureLoader loader;
    private boolean play = true;
    private int delay;
    private static Sound sound = new Sound("Sound\\Tetris.wav");
    private boolean whait = false;

    public SwingBoardViewer(Board board, Board nextBoard, Figure figure, FigureLoader loader) {
        this.board = board;
        this.figure = figure;
        this.loader = loader;
        this.delay = 500;
        nextFigure = loader.load();
        this.buttonBoard = new JButton[board.getHeight()][board.getWidth()];
        createButtonsBoard();
        frame = new JFrame("Tetris");
        frame.setSize(300, 430);
        frame.setLocationRelativeTo(null);
        createComponents();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        sound.loop();
        figure.show();
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void show() {
        while (play) {
            refresh();
            delay();
            if (figure.fall()) {
                figure = nextFigure;
                nextFigure = loader.load();
                refreshImage();
                calculateScore(board.deleteFullLines());
                calculateScore(-1);
                board.fallFigures();
                figure.show();
                whait = false;
                if (!figure.canCreate()) {
                    frame.add(gameOver());
                }

            }
            calculateLevel();
        }
    }

    private void createComponents() {
        frame.add(createKeyListener());
        frame.add(createBoardPanel());
        frame.add(createInformationPanel(), BorderLayout.EAST);
    }

    private void createButtonsBoard() {
        for (int i = 0; i < buttonBoard.length; i++) {
            for (int j = 0; j < buttonBoard[0].length; j++) {
                final JButton button = new JButton();
                //button.setBorderPainted(false);
                button.setEnabled(false);
                button.setPreferredSize(new Dimension(15, 15));
                buttonBoard[i][j] = button;
            }
        }
    }

    private JPanel createBoardPanel() {
        JPanel panel = new JPanel();
        for (int i = 0; i < buttonBoard.length; i++) {
            for (int j = 0; j < buttonBoard[0].length; j++) {
                panel.add(buttonBoard[i][j]);
            }
        }
        return panel;
    }

    public void delay() {
        try {
            if (delay >= 50) {
                Thread.sleep(delay);
            } else {
                Thread.sleep(50);
            }
        } catch (InterruptedException ex) {
        }
    }

    private void refresh() {
        for (int i = 0; i < buttonBoard.length; i++) {
            for (int j = 0; j < buttonBoard[0].length; j++) {
                switch (board.getCell(i, j).getCellType()) { // Elige el tipo de loader
                    case 0:
                        buttonBoard[i][j].setBackground(Color.WHITE);
                        break;
                    case 1:
                        buttonBoard[i][j].setBackground(new Color(240, 238, 5));
                        break;
                    case 2:
                        buttonBoard[i][j].setBackground(new Color(1, 241, 242));
                        break;
                    case 3:
                        buttonBoard[i][j].setBackground(new Color(242, 160, 0));
                        break;
                    case 4:
                        buttonBoard[i][j].setBackground(new Color(0, 0, 238));
                        break;
                    case 5:
                        buttonBoard[i][j].setBackground(new Color(161, 0, 245));
                        break;
                    case 6:
                        buttonBoard[i][j].setBackground(new Color(0, 239, 2));
                        break;
                    case 7:
                        buttonBoard[i][j].setBackground(new Color(244, 0, 0));
                        break;
                }
            }
        }
    }

    private JPanel createKeyListener() {
        JPanel panel = new JPanel();
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (whait) {
                    return;
                }
                switch (KeyEvent.getKeyText(ke.getKeyCode())) {
                    case "Derecha":
                        if (play) {
                            figure.moveRight();
                        }
                        break;
                    case "Izquierda":
                        if (play) {
                            figure.moveLeft();
                        }
                        break;
                    case "Arriba":
                        if (play) {
                            figure.rotate();
                        }
                        break;
                    case "Abajo":
                        if (play) {
                            setDelay(50);
                        }
                        break;
                    case "Espacio":
                        if (play) {
                            figure.fullFall();
                            whait = true;
                        }
                        break;
                    case "P":
                        if (play) {
                            play = false;
                            sound.pause();
                        } else {
                            play = true;
                            sound.loop();
                        }
                        break;
                    case "S":
                        sound.silence();
                        break;
                }
                if (play) {
                    refresh();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if ("Abajo".equals(KeyEvent.getKeyText(ke.getKeyCode()))) {
                    setDelay(500 - (Integer.parseInt(levelLabel.getText()) - 1) * 25);
                }
            }
        };
        panel.addKeyListener(keyListener);
        panel.setFocusable(true);
        return panel;

    }

    private JPanel createInformationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(00, 20)));
        panel.add(new JLabel("    Next"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(createJLabel());
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(new JLabel("Score:           "));
        panel.add(scoreLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(new JLabel("Level:"));
        panel.add(levelLabel);
        return panel;
    }

    private void calculateScore(int lines) {
        switch (lines) {
            case -1:
                scoreLabel.setText(Integer.parseInt(scoreLabel.getText()) + 10 + "");
                count += 10;
                break;
            case 1:
                scoreLabel.setText(Integer.parseInt(scoreLabel.getText()) + 100 + "");
                count += 100;
                break;
            case 2:
                scoreLabel.setText(Integer.parseInt(scoreLabel.getText()) + 250 + "");
                count += 250;
                break;
            case 3:
                scoreLabel.setText(Integer.parseInt(scoreLabel.getText()) + 500 + "");
                count += 500;
                break;
            case 4:
                scoreLabel.setText(Integer.parseInt(scoreLabel.getText()) + 1000 + "");
                count += 1000;
                break;
        }

    }

    private void calculateLevel() {
        if (count >= 1000) {
            count -= 1000;
            levelLabel.setText(Integer.parseInt(levelLabel.getText()) + 1 + "");
            setDelay(500 - (Integer.parseInt(levelLabel.getText()) - 1) * 25);
        }
    }

    private JPanel gameOver() {
        JPanel panel = new JPanel();
        sound.pause();
        JOptionPane.showMessageDialog(panel, "Game Over, Puntuacion: " + scoreLabel.getText(), "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(0);
        return panel;
    }

    private JLabel createJLabel() {
        refreshImage();
        Border border = LineBorder.createGrayLineBorder();
        ImageLabel.setBorder(border);
        return ImageLabel;
    }

    private void refreshImage() {
        switch (nextFigure.getName()) {
            case "I":
                ImageLabel.setIcon(new ImageIcon("Images//Line.png"));
                break;
            case "Z":
                ImageLabel.setIcon(new ImageIcon("Images//zFigure.png"));
                break;
            case "L":
                ImageLabel.setIcon(new ImageIcon("Images//lFigure.png"));
                break;
            case "J":
                ImageLabel.setIcon(new ImageIcon("Images//jFigure.png"));
                break;
            case "O":
                ImageLabel.setIcon(new ImageIcon("Images//Square.png"));
                break;
            case "T":
                ImageLabel.setIcon(new ImageIcon("Images//tFigure.png"));
                break;
            case "S":
                ImageLabel.setIcon(new ImageIcon("Images//sFigure.png"));
                break;
        }
    }
}
