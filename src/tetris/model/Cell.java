package tetris.model;

public class Cell {

    private int cellType;

    public Cell(int type) {
        this.cellType = type;
    }

    public int getCellType() {
        return cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    @Override
    public String toString() {
        return cellType!=0 ? "[#]" : "[ ]";
    }
}
