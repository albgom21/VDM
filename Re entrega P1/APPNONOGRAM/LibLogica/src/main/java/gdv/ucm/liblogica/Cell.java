package gdv.ucm.liblogica;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class Cell implements IInterface {
    // Attributes
    private int x;          // x a nivel lógico, en la matriz del tablero
    private int y;
    private int side;       // al ser cuadradas w == h
    private float offsetX;
    private float offsetY;
    private int separacion; // separación entre celdas

    private float tr_x;     // x a nivel render, pos en pixeles de la pantalla
    private float tr_y;

    public float getMedia() {
        return media;
    }

    private float media;
    private CellState state;
    private boolean isSol;
    private int borderTop;

    public Cell(int x, int y, float offsetX, float offsetY, boolean sol, CellState state, int borderTop)  {
        this.x = x;
        this.y = y;
        this.side = 30;
        this.offsetX = offsetX; // Cantidad de celdas en x
        this.offsetY = offsetY; // Cantidad de celdas en y
        this.separacion = this.side/3;

        this.media = (offsetX+offsetY)/2;

        this.isSol = sol;
        this.state = state;

        this.borderTop = borderTop;
    }

    @Override
    public boolean handleEvent(IInput.Event e) {
        int mX = e.x;
        int mY = e.y;

        if(e.type == IInput.InputTouchType.PRESSED && //click
           e.index == 1 &&                            // boton izq
           (mX >= tr_x - (this.side/2) && mX <= this.side + tr_x - (this.side/2)
           && mY >= tr_y - (this.side/2) + borderTop && mY <= this.side + tr_y - (this.side/2) + borderTop)){ // dentro del cuadrado
                if(state.equals(CellState.GRAY))
                    state = CellState.BLUE;
                else if(state.equals(CellState.BLUE))
                    state = CellState.WHITE;
                else if(state.equals(CellState.WHITE))
                    state = CellState.GRAY;
                else if(state.equals(CellState.RED))
                    state = CellState.GRAY;

                return true;
           }
        return false;
    }

    // Setters
    public void setTr_x(float tr_x) {
        this.tr_x = tr_x;
    }

    public void setTr_y(float tr_y) {
        this.tr_y = tr_y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public void setSeparacion(int separacion) {
        this.separacion = separacion;
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

    public int getSide() {
        return side;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public int getSeparacion() {
        return separacion;
    }

    @Override
    public void update(Double deltaTime) {}

    @Override
    public void render(IGraphics g){}
}