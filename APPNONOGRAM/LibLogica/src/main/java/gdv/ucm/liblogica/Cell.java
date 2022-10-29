package gdv.ucm.liblogica;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInterface;

public class Cell implements IInterface {
    // Attributes
    private int x;
    private int y;
    private CellState state;



    private boolean isSol;

    // Default constructor
    public Cell(int x, int y, boolean sol, CellState state) {
        this.x = x;
        this.y = y;
        this.isSol = sol;
        this.state = state;
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

    @Override
    public void render(IGraphics g){
        int color;

        if(state.equals(CellState.GRAY))
            color=0x808080;
        else if(state.equals(CellState.BLUE))
            color=0x0000ff;

        else if(state.equals(CellState.RED))
            color=0xff0000;

        else //WHITE
            color=0xffffff;


        g.setColor(color);

//        g.fillSquare((1920/3)+(this.x *52),(1080/3)+(this.y*52),150);
//        g.fillSquare(200+(this.x*75),100+(this.y*75),50);
        g.fillSquare(200+(this.x*75),200+(this.y*75),50); //Espacio dependiendo de las columnas y filas
        //bordes en PC
    }

    @Override
    public void update(Double deltaTime) {

    }
}