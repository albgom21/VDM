package gdv.ucm.liblogica;

import gdv.ucm.libengine.IGraphics;

public class Cell {
    // Attributes
    private int x;
    private int y;
    private CellState state;



    private boolean isSol;

    // Default constructor
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isSol = false;
        this.state = CellState.GREY;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setState(CellState state) {
        this.state = state;
    }


   // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getisSol() { return isSol; }

    public CellState getState() {
        return state;
    }

    void render(IGraphics g){}

}