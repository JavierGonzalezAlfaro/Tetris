package tetris.model;


public interface Figure {
    public void show();
    public void rotate();
    public boolean fall();
    public boolean fullFall();
    public void moveRight();
    public void moveLeft();
    public boolean canCreate();
    public String getName();
}
