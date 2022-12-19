package gdv.ucm.appnonogram;

import com.example.libenginea.AudioA;
import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InterfaceA;
import com.example.libenginea.InputA;

import java.io.Serializable;

public class Cell implements InterfaceA, Serializable {
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
    private boolean loselife;

    public Cell(int x, int y, float offsetX, float offsetY, boolean sol, CellState state)  {
        this.x = x;
        this.y = y;
        this.side = 30;
        this.offsetX = offsetX; // Cantidad de celdas en x
        this.offsetY = offsetY; // Cantidad de celdas en y
        this.separacion = this.side/3;
        this.loselife = false;

        this.media = (offsetX+offsetY)/2;

        this.isSol = sol;
        this.state = state;
    }

    @Override
    public boolean handleEvent(InputA.Event e) {
        int mX = e.x;
        int mY = e.y;

        if(e.type == InputA.InputTouchType.NORMAL_PRESSED && //click
           e.index == 1 &&                            // boton izq
           (mX >= tr_x - (this.side/2) && mX <= this.side + tr_x - (this.side/2)
           && mY >= tr_y - (this.side/2) && mY <= this.side + tr_y - (this.side/2))){ // dentro del cuadrado

                if(state.equals(CellState.GRAY) && !this.isSol) {
                    state = CellState.RED;
//                    this.audio.playSound("wrong");
                    this.loselife = true;
                }
                else if(state.equals(CellState.GRAY))
                    state = CellState.BLUE;
                else if(state.equals(CellState.BLUE))
                    state = CellState.GRAY;
                else if(state.equals(CellState.WHITE))
                    state = CellState.GRAY;
                //No poder cambiar de rojo a nada, esperar a que el color rojo se vaya
                else if(state.equals(CellState.RED)) //Preguntar si desactivar esto
                    state = CellState.GRAY;

                return true;
           }
        else if(e.type == InputA.InputTouchType.LONG_PRESSED && //click
                e.index == 1 &&                            // boton izq
                (mX >= tr_x - (this.side/2) && mX <= this.side + tr_x - (this.side/2)
                        && mY >= tr_y - (this.side/2) && mY <= this.side + tr_y - (this.side/2))){ // dentro del cuadrado
            if(state.equals(CellState.GRAY))
                state = CellState.WHITE;
            else if(state.equals(CellState.BLUE))
                state = CellState.WHITE;

            return true;
        }
        return false;
    }

    public boolean loseLife()
    {
        if(this.loselife) {
            this.loselife = false;
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
    public void render(GraphicsA g){}
}