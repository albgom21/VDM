package gdv.ucm.liblogica;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class Cell implements IInterface {
    // Attributes
    private int x; // x a nivel lógico, en la matriz del tablero
    private int y;
    private int side; // al ser cuadradas w == h

    private int tr_x; // x a nivel render, pos en pixeles de la pantalla
    private int tr_y;
    private CellState state;

    private boolean isSol;

    // Default constructor
    public Cell(int x, int y, boolean sol, CellState state)  {
        this.x = x;
        this.y = y;
        this.side = 50;
        this.tr_x = 200+(this.x*75);
        this.tr_y = 200+(this.y*75);
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
        g.fillSquare(200+(this.x*75),200+(this.y*75),this.side); //Espacio dependiendo de las columnas y filas
        if(state.equals(CellState.WHITE)){
            g.setColor(0x000000);
            g.drawSquare(200+(this.x*75),200+(this.y*75),this.side);
            g.drawLine(200+(this.x*75),200+(this.y*75),200+(this.x*75)+this.side,200+(this.y*75)+this.side);
        }
        //bordes en PC
    }

    @Override
    public void update(Double deltaTime) {

    }

    @Override
    public void handleEvent(IInput.Event e) {
        int mX = e.x;
        int mY = e.y;

    if(e.type == IInput.InputTouchType.PRESSED && //click
       e.index == 1 &&                            // boton izq
       (mX >= tr_x && mX <= this.side + tr_x && mY >= tr_y && mY <= this.side + tr_y)){ // dentro del cuadrado
            if(state.equals(CellState.GRAY))
                state = CellState.BLUE;
            else if(state.equals(CellState.BLUE))
                state = CellState.WHITE;
            else if(state.equals(CellState.WHITE))
                state = CellState.GRAY;



       }
    }
}