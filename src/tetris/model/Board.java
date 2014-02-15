package tetris.model;

import javax.swing.JOptionPane;

public class Board {

    private Cell[][] board;
    private int[] blankLine = {-1, -1, -1, -1};

    public Board(Cell[][] board) {
        this.board = board;
        iniciateBoard();
    }

    public int getWidth() {
        return board[0].length;
    }

    public int getHeight() {
        return board.length;
    }

    public Cell getCell(int i, int j) {
        return board[i][j];
    }

    public void setCell(int i, int j, int type) {
        board[i][j] = new Cell(type);
    }

    private void iniciateBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell(0);
            }
        }
    }

    public int deleteFullLines() {
        boolean checker = false;
        int deleteCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getCellType() == 0) {
                    checker = false;
                    break;
                }
                checker = true;
            }
            if (checker) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j].setCellType(0);
                }
                blankLine[deleteCount] = i;
                deleteCount++;
            }
        }
        return deleteCount;
    }

    public void fallFigures() {
        for (int k = 0; k < blankLine.length; k++) {
            if (blankLine[k] != -1) {
                for (int i = blankLine[k]; i > 0; i--) {
                    for (int j = 0; j < board[0].length; j++) {
                        board[i][j].setCellType(board[i-1][j].getCellType());
                    }
                }
                blankLine[k]=-1;
            }
        }
    }
    
}
