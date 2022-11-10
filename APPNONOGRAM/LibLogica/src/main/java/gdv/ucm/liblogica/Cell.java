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

    public void setSide(int side) {
        this.side = side;
    }

    public void setSeparacion(int separacion) {
        this.separacion = separacion;
    }

    private int separacion;

    public void setTr_x(float tr_x) {
        this.tr_x = tr_x;
    }

    public void setTr_y(float tr_y) {
        this.tr_y = tr_y;
    }

    private float tr_x; // x a nivel render, pos en pixeles de la pantalla
    private float tr_y;

    public float getMedia() {
        return media;
    }

    private float media;
    private CellState state;
//    IGraphics gr;

    private boolean isSol;

    // Default constructor
    public Cell(int x, int y, float offsetX, float offsetY, boolean sol, CellState state)  {
        this.x = x;
        this.y = y;
        this.side = 30;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.separacion = this.side/3;

        this.media = (offsetX+offsetY)/2;
//        this.side = (int)(((this.gr.getWindow()/3)*2)/media);
//
//        this.separacion = this.side/8;
//
//        this.tr_x = this.gr.logicToRealX(this.gr.getWidthLogic()/2) //mitad de la pantalla
//                - (int)((this.offsetX/2) * (this.side)) // mitad casillas izq
//                - (int)((this.offsetX/2 - 1) * separacion) // mitad offsets (uno menos que las casillas)
//                + separacion*3
//                //+ (separacionE) // offset que se suma depende de si es par o impar
//                + (this.x*((this.side)+(separacion))); // pos de cada casilla
//
//        this.tr_y = this.gr.logicToRealY(this.gr.getHeightLogic()/2) //mitad pantalla
//                - ((int)((this.offsetY/2)*(this.side + separacion))) // mitad casillas arriba
//                + (this.y*(this.side+separacion)); // pos de cada casilla

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
    public void render(IGraphics g){
//        int color;
//        float media = (offsetX+offsetY)/2;
//        this.side = (int)(((g.getWindow()/3)*2)/media);
//
//        this.separacion = this.side/8;
//
//        this.tr_x = g.logicToRealX(g.getWidthLogic()/2) //mitad de la pantalla
//                - (int)((this.offsetX/2) * (this.side)) // mitad casillas izq
//                - (int)((this.offsetX/2 - 1) * separacion) // mitad offsets (uno menos que las casillas)
//                + separacion*3
//                + (this.x*((this.side)+(separacion))); // pos de cada casilla
//
//        this.tr_y = g.logicToRealY(g.getHeightLogic()/2) //mitad pantalla
//                - ((int)((this.offsetY/2)*(this.side + separacion))) // mitad casillas arriba
//                + (this.y*(this.side+separacion)); // pos de cada casilla

//
//        if(state.equals(CellState.GRAY))
//            color=0x7f7a7a;
//        else if(state.equals(CellState.BLUE))
//            color=0x5b6ee1;
//        else if(state.equals(CellState.RED))
//            color=0xac3232;
//        else //WHITE
//            color=0xececec;
//        g.setColor(color);
//
//        if(!state.equals(CellState.NORENDER)) {
//            g.fillSquare((int)tr_x - (this.side/2), (int)tr_y - (this.side/2), this.side); //Espacio dependiendo de las columnas y filas
//            if (state.equals(CellState.WHITE)) {
//                g.setColor(0x000000);
//                g.drawSquare((int)tr_x - (int)((this.side/2)), (int)tr_y - (int)(this.side/2), this.side);
//                g.drawLine((int)tr_x - (this.side/2), (int)tr_y - (this.side/2), (int)tr_x + (this.side/2), (int)tr_y + (this.side/2));
//            }
//        }
    }

    @Override
    public boolean handleEvent(IInput.Event e) {
        int mX = e.x;
        int mY = e.y;

    if(e.type == IInput.InputTouchType.PRESSED && //click
       e.index == 1 &&                            // boton izq
       (mX >= tr_x - (this.side/2) && mX <= this.side + tr_x - (this.side/2)
       && mY >= tr_y - (this.side/2) && mY <= this.side + tr_y - (this.side/2))){ // dentro del cuadrado
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

    @Override
    public void update(Double deltaTime) {}
}