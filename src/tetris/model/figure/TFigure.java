package tetris.model.figure;

import tetris.model.Board;
import tetris.model.Figure;

public class TFigure implements Figure {

    private int x;
    private int y;
    private int position;
    private Board board;
    private boolean canCreate;

    public TFigure(Board board) {
        this.x = 0;
        this.y = 4;
        this.board = board;
        this.position = 0;
        
    }

    @Override
    public void show() {
        canCreate = buildDownHorizontalLFigure();
    }

    @Override
    public void rotate() {
        if (x == 0) {
            return;
        }
        if (position == 0) {
            if (checkConversion()) {
                cleanLine();
                buildLeftVerticalLFigure();
                position = 1;
            }
        } else if (position == 1) {
            if (checkConversion()) {
                cleanLine();
                buildUpHorizontalLFigure();
                position = 2;
            }
        } else if (position == 2) {
            if (checkConversion()) {
                cleanLine();
                buildRigthVerticalLFigure();
                position = 3;
            }
        } else {
            if (checkConversion()) {
                cleanLine();
                buildDownHorizontalLFigure();
                position = 0;
            }
        }
    }

    @Override
    public boolean fall() {
        if (checkFall()) {
            cleanLine();
            x += 1;
            if (position == 0) {
                buildDownHorizontalLFigure();
            } else if (position == 1) {
                buildLeftVerticalLFigure();
            } else if (position == 2) {
                buildUpHorizontalLFigure();
            } else {
                buildRigthVerticalLFigure();
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
            if (position == 0) {
                buildDownHorizontalLFigure();
            } else if (position == 1) {
                buildLeftVerticalLFigure();
            } else if (position == 2) {
                buildUpHorizontalLFigure();
            } else {
                buildRigthVerticalLFigure();
            }
        }
    }

    @Override
    public void moveLeft() {
        if (checkLeftSide()) {
            cleanLine();
            y -= 1;
            if (position == 0) {
                buildDownHorizontalLFigure();
            } else if (position == 1) {
                buildLeftVerticalLFigure();
            } else if (position == 2) {
                buildUpHorizontalLFigure();
            } else {
                buildRigthVerticalLFigure();
            }
        }
    }

    private boolean buildDownHorizontalLFigure() {
        if (board.getCell(x, y).getCellType() == 0 || board.getCell(x, y - 1).getCellType() == 0 || board.getCell(x, y + 1).getCellType() == 0 || board.getCell(x + 1, y).getCellType() == 0) {
            board.getCell(x, y).setCellType(5);
            board.getCell(x, y + 1).setCellType(5);
            board.getCell(x, y - 1).setCellType(5);
            board.getCell(x + 1, y).setCellType(5);
            return true;
        }
        return false;
    }

    private void buildLeftVerticalLFigure() {
        board.getCell(x, y).setCellType(5);
        board.getCell(x - 1, y).setCellType(5);
        board.getCell(x + 1, y).setCellType(5);
        board.getCell(x, y - 1).setCellType(5);
    }

    private void buildUpHorizontalLFigure() {
        board.getCell(x, y).setCellType(5);
        board.getCell(x, y + 1).setCellType(5);
        board.getCell(x, y - 1).setCellType(5);
        board.getCell(x - 1, y).setCellType(5);
    }

    private void buildRigthVerticalLFigure() {
        board.getCell(x, y).setCellType(5);
        board.getCell(x - 1, y).setCellType(5);
        board.getCell(x + 1, y).setCellType(5);
        board.getCell(x, y + 1).setCellType(5);
    }

    private boolean checkFall() {
        if (position == 0) {
            if (x + 2 == board.getHeight() || board.getCell(x + 2, y).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 1) {
            if (x + 2 == board.getHeight() || board.getCell(x + 2, y).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 2) {
            if (x + 1 == board.getHeight() || board.getCell(x + 1, y).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (x + 2 == board.getHeight() || board.getCell(x + 2, y).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        }
    }

    private void cleanLine() {
        if (position == 0) {
            board.getCell(x, y).setCellType(0);
            board.getCell(x, y + 1).setCellType(0);
            board.getCell(x, y - 1).setCellType(0);
            board.getCell(x + 1, y).setCellType(0);
        } else if (position == 1) {
            board.getCell(x, y).setCellType(0);
            board.getCell(x - 1, y).setCellType(0);
            board.getCell(x + 1, y).setCellType(0);
            board.getCell(x, y - 1).setCellType(0);
        } else if (position == 2) {
            board.getCell(x, y).setCellType(0);
            board.getCell(x, y + 1).setCellType(0);
            board.getCell(x, y - 1).setCellType(0);
            board.getCell(x - 1, y).setCellType(0);
        } else {
            board.getCell(x, y).setCellType(0);
            board.getCell(x - 1, y).setCellType(0);
            board.getCell(x + 1, y).setCellType(0);
            board.getCell(x, y + 1).setCellType(0);
        }
    }

    private boolean checkRightSide() {
        if (position == 0) {
            if (y + 2 == board.getWidth() || board.getCell(x + 1, y + 1).getCellType() > 0 || board.getCell(x, y + 2).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 1) {
            if (y + 1 == board.getWidth() || board.getCell(x, y + 1).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0 || board.getCell(x - 1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 2) {
            if (y + 2 == board.getWidth() || board.getCell(x, y + 2).getCellType() > 0 || board.getCell(x -1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (y + 2 == board.getWidth() || board.getCell(x, y + 2).getCellType() > 0 || board.getCell(x - 1, y + 1).getCellType() > 0 || board.getCell(x + 1, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        }
    }

    private boolean checkLeftSide() {
        if (position == 0) {
            if (y - 1 == 0 || board.getCell(x, y - 2).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 1) {
            if (y - 1 == 0 || board.getCell(x, y - 2).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0 || board.getCell(x - 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 2) {
            if (y - 1 == 0 || board.getCell(x, y - 2).getCellType() > 0 || board.getCell(x - 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (y == 0 || board.getCell(x, y - 1).getCellType() > 0 || board.getCell(x - 1, y - 1).getCellType() > 0 || board.getCell(x + 1, y - 1).getCellType() > 0) {
                return false;
            }
            return true;
        }
    }

    private boolean checkConversion() {
        if (position == 0) {
            if (board.getCell(x - 1, y).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 1) {
            if (y + 1 == board.getWidth() || board.getCell(x, y + 1).getCellType() > 0) {
                return false;
            }
            return true;
        } else if (position == 2) {
            if (x + 1 == board.getHeight() || board.getCell(x + 1, y).getCellType() > 0) {
                return false;
            }
            return true;
        } else {
            if (y == 0 || board.getCell(x, y - 1).getCellType() > 0) {
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
        return "T";
    }
}
