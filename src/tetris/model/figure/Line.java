package tetris.model.figure;

import tetris.model.Board;
import tetris.model.Figure;

public class Line implements Figure {

    private boolean lie;
    private int x;
    private int y;
    private Board board;
    private boolean canCreate;

    public Line(Board board) {
        this.x = 0;
        this.y = 4;
        this.board = board;
        
    }

    @Override
    public void show() {
        canCreate = buildHorizontalLine();
    }

    @Override
    public void rotate() {
        if (x == 0) {
            return;
        }
        if (lie) {
            if (checkConversion()) {
                cleanLine();
                buildVerticalLine();
                lie = false;
            }
        } else {
            if (checkConversion()) {
                cleanLine();
                buildHorizontalLine();
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
                buildHorizontalLine();
            } else {
                buildVerticalLine();
            }
            return false;
        } else {
            return true;
        }
    }

    private boolean buildHorizontalLine() {
        if (board.getCell(x, y).getCellType() == 0 || board.getCell(x, y - 1).getCellType() == 0 || board.getCell(x, y + 1).getCellType() == 0 || board.getCell(x, y + 2).getCellType() == 0) {
            lie = true;
            board.getCell(x, y - 1).setCellType(2);
            board.getCell(x, y).setCellType(2);
            board.getCell(x, y + 1).setCellType(2);
            board.getCell(x, y + 2).setCellType(2);
            return true;
        }
        return false;
    }

    private void buildVerticalLine() {
        lie = false;
        board.getCell(x - 1, y).setCellType(2);
        board.getCell(x, y).setCellType(2);
        board.getCell(x + 1, y).setCellType(2);
        board.getCell(x + 2, y).setCellType(2);
    }

    private void cleanLine() {
        if (lie) {
            board.getCell(x, y - 1).setCellType(0);
            board.getCell(x, y).setCellType(0);
            board.getCell(x, y + 1).setCellType(0);
            board.getCell(x, y + 2).setCellType(0);
        } else {
            board.getCell(x - 1, y).setCellType(0);
            board.getCell(x, y).setCellType(0);
            board.getCell(x + 1, y).setCellType(0);
            board.getCell(x + 2, y).setCellType(0);
        }
    }

    private boolean checkFall() {
        if (lie) {
            if (x + 1 == board.getHeight() || board.getCell(x + 1, y - 1).getCellType() > 0 || board.getCell(x + 1, y).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0 || board.getCell(x + 1, y + 2).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (x + 3 == board.getHeight() || board.getCell(x + 3, y).getCellType() > 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void moveRight() {
        if (checkRightSide()) {
            cleanLine();
            y += 1;
            if (lie) {
                buildHorizontalLine();
            } else {
                buildVerticalLine();
            }
        }
    }

    @Override
    public void moveLeft() {
        if (checkLeftSide()) {
            cleanLine();
            y -= 1;
            if (lie) {
                buildHorizontalLine();
            } else {
                buildVerticalLine();
            }
        }
    }

    private boolean checkConversion() {
        if (lie) {
            if (x + 1 == board.getHeight() || x + 2 == board.getHeight() || board.getCell(x + 1, y).getCellType() > 0 || board.getCell(x + 2, y).getCellType() > 0 || board.getCell(x - 1, y).getCellType() > 0) {
                return false;
            }
            return true;

        } else {
            if (y == 0 || y + 1 == board.getWidth() || y + 2 == board.getWidth() || board.getCell(x, y - 1).getCellType() > 0 || board.getCell(x, y + 1).getCellType() > 0 || board.getCell(x, y + 2).getCellType() > 0) {
                return false;
            }
            return true;
        }
    }

    private boolean checkLeftSide() {
        if (lie) {
            if (y - 2 == -1 || board.getCell(x, y - 2).getCellType() > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            if (y - 1 == -1 || board.getCell(x - 1, y - 1).getCellType() > 0 || board.getCell(x, y - 1).getCellType() > 0
                    || board.getCell(x + 1, y - 1).getCellType() > 0 || board.getCell(x + 2, y - 1).getCellType() > 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean checkRightSide() {
        if (lie) {
            if (y + 3 == board.getWidth() || board.getCell(x, y + 3).getCellType() > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            if (y + 1 == board.getWidth() || board.getCell(x - 1, y + 1).getCellType() > 0
                    || board.getCell(x, y + 1).getCellType() > 0
                    || board.getCell(x + 1, y + 1).getCellType() > 0 || board.getCell(x + 2, y + 1).getCellType() > 0) {
                return false;
            } else {
                return true;
            }
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
        return "I";
    }
}
