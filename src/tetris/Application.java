package tetris;

import tetris.model.Board;
import tetris.model.Cell;
import tetris.model.Figure;
import tetris.persistence.FigureLoader;
import tetris.ui.swing.SwingBoardViewer;

public class Application {

    public static void main(String[] args) {
        Board board = new Board(new Cell[19][10]);
        Board nextBoard = new Board(new Cell[5][5]);
        FigureLoader loader = new FigureLoader(board);
        Figure figure = loader.load();
        SwingBoardViewer viewer = new SwingBoardViewer(board, nextBoard, figure, loader);
        while(true){
            viewer.show();
        }
    }
}
