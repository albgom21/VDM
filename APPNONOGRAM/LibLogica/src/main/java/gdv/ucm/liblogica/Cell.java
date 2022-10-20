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
        this.state = CellState.GRAY;
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

    void render(IGraphics g){
        int color;

        if(state.equals(CellState.GRAY))
            color=0x808080;
        else if(state.equals(CellState.BLUE))
            color=0x808080;

        else if(state.equals(CellState.RED))
            color=0x808080;

        else //WHITE
            color=0x808080;


        g.setColor(color);

//        g.fillSquare((1920/3)+(this.x *52),(1080/3)+(this.y*52),150);
        g.fillSquare(200+(this.x*75),100+(this.y*75),50);
    }
}