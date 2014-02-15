package tetris.persistence;

import tetris.model.Board;
import tetris.model.Figure;
import tetris.model.figure.JFigure;
import tetris.model.figure.LFigure;
import tetris.model.figure.Line;
import tetris.model.figure.SFigure;
import tetris.model.figure.Square;
import tetris.model.figure.TFigure;
import tetris.model.figure.ZFigure;


public class FigureLoader {
    private Board board;

    public FigureLoader(Board board) {
        this.board = board;
    }
    
    public Figure load(){
        double probability = Math.random();
        if (probability < 0.1428){
            return loadSquare();
        }else if (probability < 0.2856){
            return loadLine();
        }else if (probability < 0.4284){
            return loadLFigure();
        }else if (probability < 0.5712){
            return loadJFigure();
        }else if (probability < 0.714){
            return loadZFigure();
        }else if (probability < 0.8568){
            return loadSFigure();
        }else {
            return loadTFigure();
        }
    }

    private Figure loadSquare() {
        Figure figure = new Square(board);
        return figure;
    }

    private Figure loadLine() {
        Figure figure = new Line(board);
        return figure;
    }

    private Figure loadLFigure() {
        Figure figure = new LFigure(board);
        return figure;
    }
    private Figure loadJFigure() {
        Figure figure = new JFigure(board);
        return figure;
    }

    private Figure loadZFigure() {
        Figure figure = new ZFigure(board);
        return figure;
    }

    private Figure loadSFigure() {
        Figure figure = new SFigure(board);
        return figure;
    }

    private Figure loadTFigure() {
        Figure figure = new TFigure(board);
        return figure;
    }
}
