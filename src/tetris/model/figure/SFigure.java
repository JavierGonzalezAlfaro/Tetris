package tetris.model.figure;

import tetris.model.Board;
import tetris.model.Figure;

public class SFigure implements Figure {

    private int x;
    private int y;
    private boolean lie;
    private Board board;
    private boolean canCreate;

    public SFigure(Board board) {
        this.x = 0;
        this.y = 4;
        this.lie = true;
        this.board = board;
        
    }
    @Override
    public void show() {
        canCreate = canCreate=buildHorizontalSFigure();
    }

    @Override
    public void rotate() {
        if (x == 0) {
            return;
        }
        if (lie) {
            if (checkConversion()) {
                cleanLine();
                buildVerticalSFigure();
                lie = false;
            }
        } else {
            if (checkConversion()) {
                cleanLine();
                buildHorizontalSFigure();
                lie = true;
            }
        }
    }

    @Override
    public boolean fall() {
        if (checkFall()) {
            cleanLine();
            x += 1;
            if (lie) {
                buildHorizontalSFigure();
            } else {
                buildVerticalSFigure();
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void moveRight() {
        if (checkRightSide()) {
            cleanLine();
            y += 1;
            if (lie) {
                buildHorizontalSFigure();
            } else {
                buildVerticalSFigure();
            }
        }
    }

    @Override
    public void moveLeft() {
        if (checkLeftSide()) {
            cleanLine();
            y -= 1;
            if (lie) {
                buildHorizontalSFigure();
            } else {
                buildVerticalSFigure();
            }
        }
    }

    private boolean buildHorizontalSFigure() {
        if (board.getCell(x, y).getCellType() == 0 || board.getCell(x, y + 1).getCellType() == 0 || board.getCell(x+1, y).getCellType() == 0 || board.getCell(x + 1, y - 1).getCellType() == 0) {
            board.getCell(x, y).setCellType(6);   // LOS PUSE CON EL NUMERO 6 PORQUE SUPUSE QUE TU USARIAS EL 5 PERO VAMOS, COMPRUEBA
            board.getCell(x, y + 1).setCellType(6);
            board.getCell(x + 1, y).setCellType(6);
            board.getCell(x + 1, y - 1).setCellType(6);
            return true;
        }
        return false;
    }

    private void buildVerticalSFigure() {
        board.getCell(x, y).setCellType(6);
        board.getCell(x + 1, y + 1).setCellType(6);
        board.getCell(x, y + 1).setCellType(6);
        board.getCell(x - 1, y).setCellType(6);
    }

    private boolean checkFall() {
        if (lie) {
            if (x + 2 == board.getHeight() || board.getCell(x + 2, y).getCellType() > 0 || board.getCell(x + 2, y - 1).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (x + 2 == board.getHeight() || board.getCell(x + 2, y + 1).getCellType() > 0 || board.getCell(x + 1, y).getCellType() > 0) {
                return false;
            }
            return true;
        }
    }

    private void cleanLine() {
        if (lie) {
            board.getCell(x, y).setCellType(0);
            board.getCell(x, y + 1).setCellType(0);
            board.getCell(x + 1, y).setCellType(0);
            board.getCell(x + 1, y - 1).setCellType(0);
        } else {
            board.getCell(x, y).setCellType(0);
            board.getCell(x + 1, y + 1).setCellType(0);
            board.getCell(x, y + 1).setCellType(0);
            board.getCell(x - 1, y).setCellType(0);
        }
    }

    private boolean checkRightSide() {
        if (lie) {
            if (y + 2 == board.getWidth() || board.getCell(x, y + 2).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (y + 2 == board.getWidth() || board.getCell(x, y + 2).getCellType() > 0 || board.getCell(x + 1, y + 2).getCellType() > 0 || board.getCell(x - 1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        }
    }

    private boolean checkLeftSide() {
        if (lie) {
            if (y - 1 == 0 || board.getCell(x, y - 1).getCellType() > 0 || board.getCell(x + 1, y - 2).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (y == 0 || board.getCell(x, y - 1).getCellType() > 0 || board.getCell(x + 1, y).getCellType() > 0 || board.getCell(x - 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        }
    }

    private boolean checkConversion() {
        if (lie) {
            if (board.getCell(x - 1, y).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (y == 0 || board.getCell(x + 1, y).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        }
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
        return "S";
    }
}