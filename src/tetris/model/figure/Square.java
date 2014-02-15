package tetris.model.figure;

import tetris.model.Board;
import tetris.model.Figure;

public class Square implements Figure {

    private int x;
    private int y;
    Board board;
    private boolean canCreate;

    public Square(Board board) {
        this.x = 0;
        this.y = 4;
        this.board = board;
    }

    @Override
    public void show() {
        canCreate = buildSquare();
    }

    @Override
    public void rotate() {
    }

    @Override
    public boolean fall() {
        if (checkFall()) {
            cleanSquare();
            x += 1;
            buildSquare();
            return false;
        } else {
            return true;
        }
    }

    private boolean buildSquare() {
        if (board.getCell(x, y).getCellType() == 0 || board.getCell(x, y + 1).getCellType() == 0 || board.getCell(x + 1, y + 1).getCellType() == 0 || board.getCell(x + 1, y).getCellType() == 0) {
            board.getCell(x, y).setCellType(1);
            board.getCell(x + 1, y).setCellType(1);
            board.getCell(x, y + 1).setCellType(1);
            board.getCell(x + 1, y + 1).setCellType(1);
            return true;
        }
        return false;
    }

    private void cleanSquare() {
        board.getCell(x, y).setCellType(0);
        board.getCell(x + 1, y).setCellType(0);
        board.getCell(x, y + 1).setCellType(0);
        board.getCell(x + 1, y + 1).setCellType(0);
    }

    private boolean checkFall() {
        if (x + 2 == board.getHeight() || board.getCell(x + 2, y).getCellType() > 0 || board.getCell(x + 2, y + 1).getCellType() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public void moveRight() {
        if (checkRightSide()) {
            cleanSquare();
            y += 1;
            buildSquare();
        }
    }

    @Override
    public void moveLeft() {
        if (checkLeftSide()) {
            cleanSquare();
            y -= 1;
            buildSquare();
        }
    }

    private boolean checkLeftSide() {
        if (y == 0 || board.getCell(x, y - 1).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0) {
            return false;
        }
        return true;
    }

    private boolean checkRightSide() {
        if (y + 1 == board.getWidth() - 1 || board.getCell(x, y + 2).getCellType() > 0 || board.getCell(x + 1, y + 2).getCellType() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean fullFall() {
        while (checkFall()) {
            fall();
        }
        return true;
    }

    @Override
    public boolean canCreate() {
        return canCreate;
    }
    @Override
    public String getName() {
        return "O";
    }
}
