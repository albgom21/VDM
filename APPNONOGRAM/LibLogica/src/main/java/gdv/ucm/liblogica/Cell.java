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
    private int separacion;
    private int separacionE;

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
        this.separacion = this.side/3;
        this.separacionE = this.side/3;

        this.tr_x = this.gr.logicToRealX(this.gr.getWidthLogic()/2) - this.gr.logicToRealX((int)this.offsetX*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(this.side/3)))
                + this.gr.logicToRealX(this.x*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(this.side/3)));
        this.tr_y = this.gr.logicToRealY(this.gr.getHeightLogic()/2) - this.gr.logicToRealY((int)this.offsetY*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(this.side/3)))
                + this.gr.logicToRealY(this.y*(this.gr.scaleToReal(this.side)+this.gr.scaleToReal(this.side/3))); //BORDER_TOP = 30

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

    public int getSeparacionE() {
        return separacionE;
    }

    @Override
    public void render(IGraphics g){
        int color;
        int minWin = Math.min(this.gr.getWidth(), this.gr.getHeight());
        int minOffset = (int) Math.max(this.offsetX, this.offsetY);
        int tablero = (int)((minWin - (minWin/5)) / minOffset);
        this.side = (int) (tablero-(minOffset*(tablero/(minOffset*3))));

        this.separacion = this.side/5;
        this.separacionE = this.side/5;
        if(this.offsetX % 2 != 0)
            this.separacionE /= 2;

        this.tr_x = this.gr.logicToRealX(this.gr.getWidthLogic()/2) //mitad de la pantalla
                - (((this.offsetX/2) * this.side)) // mitad casillas izq
                - (((this.offsetX/2 - 1) * separacion)) // mitad offsets (uno menos que las casillas)
                + ((separacionE)) // offset que se suma depende de si es par o impar
                + (this.x*((this.side)+(separacion))); // pos de cada casilla

        this.tr_y = this.gr.logicToRealY(this.gr.getHeightLogic()/2) //mitad pantalla
                - ((int)(this.offsetY/2)*(this.side)
                - (((this.offsetY/2 - 1) * separacion)))                 // mitad casillas arriba
                + (this.y*(this.side+separacion)); // pos de cada casilla

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
            g.fillSquare((int)tr_x - (this.side)/2, (int)tr_y - (this.side)/2 , (this.side)); //Espacio dependiendo de las columnas y filas
            if (state.equals(CellState.WHITE)) {
                g.setColor(0x000000);
                g.drawSquare((int)tr_x - this.gr.scaleToReal(this.side)/2, (int)tr_y - this.gr.scaleToReal(this.side)/2, this.gr.scaleToReal(this.side));
                g.drawLine((int)tr_x - this.gr.scaleToReal(this.side)/2, (int)tr_y - this.gr.scaleToReal(this.side)/2, (int)tr_x + this.gr.scaleToReal(this.side)/2, (int)tr_y + this.gr.scaleToReal(this.side)/2);
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
       (mX >= tr_x - (this.gr.scaleToReal(this.side)/2) && mX <= this.gr.scaleToReal(this.side) + tr_x - (this.gr.scaleToReal(this.side)/2)
       && mY >= tr_y - (this.gr.scaleToReal(this.side)/2) && mY <= this.gr.scaleToReal(this.side) + tr_y - (this.gr.scaleToReal(this.side)/2))){ // dentro del cuadrado
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