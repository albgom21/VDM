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
    private float offsetX;
    private float offsetY;

    private float tr_x; // x a nivel render, pos en pixeles de la pantalla
    private float tr_y;
    private CellState state;
    IGraphics gr;

    private boolean isSol;

    // Default constructor
    public Cell(int x, int y, float offsetX, float offsetY, boolean sol, CellState state, IGraphics graphics)  {
        this.x = x;
        this.y = y;
        this.side = 30;
        this.gr = graphics;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.tr_x = this.gr.getWidthLogic()/2 - this.offsetX*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10))
                    + (this.x*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10)));
        this.tr_y = this.gr.getHeightLogic()/2 - this.offsetY*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10))
                    + (this.y*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10))); //BORDER_TOP = 30
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

        this.tr_x = this.gr.logicToRealX(this.gr.getWidthLogic()/2) - this.gr.logicToRealX((int)this.offsetX*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10)))
                + this.gr.logicToRealX(this.x*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10)));
        this.tr_y = this.gr.logicToRealY(this.gr.getHeightLogic()/2) - this.gr.logicToRealY((int)this.offsetY*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10)))
                + this.gr.logicToRealY(this.y*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(10))); //BORDER_TOP = 30

        if(state.equals(CellState.GRAY))
            color=0x7f7a7a;
        else if(state.equals(CellState.BLUE))
            color=0x5b6ee1;
        else if(state.equals(CellState.RED))
            color=0xac3232;
        else //WHITE
            color=0xececec;
        g.setColor(color);

        if(!state.equals(CellState.NORENDER)) {
            g.fillSquare((int)tr_x, (int)tr_y, this.gr.scaleToReal(this.side)); //Espacio dependiendo de las columnas y filas
            if (state.equals(CellState.WHITE)) {
                g.setColor(0x000000);
                g.drawSquare((int)tr_x, (int)tr_y, this.side);
                g.drawLine((int)tr_x, (int)tr_y, (int)tr_x + this.side, (int)tr_y + this.side);
            }
        }
        //bordes en PC
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

    @Override
    public void update(Double deltaTime) {}
}