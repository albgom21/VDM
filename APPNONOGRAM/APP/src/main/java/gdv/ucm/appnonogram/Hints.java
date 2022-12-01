package gdv.ucm.appnonogram;

import com.example.libenginea.GraphicsA;
import com.example.libenginea.InterfaceA;
import com.example.libenginea.InputA;

public class Hints implements InterfaceA {
    // Atributos
    private int horizontalHints[][]; // Pistas superiores
    private int verticalHints[][];   // Pistas laterales
    private int depthCounter [];     // Profundidad de pistas en una columna (horizontalHints)
    private boolean ant[];           // Saber si la celda anterior (la de arriba) era sol o no
    private boolean end;             // Saber si hemos acabado


    private int x;
    private int y;
    private Board b;
    //Las j´s actuan como X y las i´s actuan como Y

    public Hints(Board b) {
        this.x = b.getWidth();
        this.y = b.getHeight();
        this.b = b;
        this.end = false;

        // Vectores
        this.horizontalHints = new int[x][y];
        this.verticalHints = new int[y][x];
        this.ant = new boolean[x];
        this.depthCounter = new int[x];

        // Contador
        int depthx = 0;
        // Rellenar las pistas a partir de un tablero
        for (int i = 0; i < this.y; ++i) {
            depthx = 0;
            for (int j = 0; j < this.x; ++j) {
                boolean sol = b.getCell(j,i).getisSol();
                if(sol){
                    this.verticalHints[i][depthx]++;
                    this.ant[j] = true;
                    this.horizontalHints[j][this.depthCounter[j]]++;
                }
                else {
                    if(this.verticalHints[i][depthx] >= 1)
                        depthx++;
                    if (this.ant[j])
                        this.depthCounter[j]++;

                    this.ant[j] = false;
                }
            }
        }
    }

    public Pair check() {
        int counterBlue = 0; // Celdas sol sin marcar
        int counterRed = 0;  // Celdas marcadas que no son sol

        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                boolean sol = b.getCell(j,i).getisSol();
                Cell cell = b.getCell(j,i);
                if(sol) {
                    if (cell.getState() == CellState.GRAY || cell.getState() == CellState.WHITE)
                        counterBlue++;
                }
                else {
                    if(cell.getState() == CellState.BLUE) {
//                        cell.setState(CellState.RED);
                        counterRed++;
                    }
                }
            }
        }

        Pair a = new Pair(counterBlue,counterRed);
//        if(a.getFirst()==0 && a.getSecond()==0) { // Todas las sol marcadas y sin fallos = win
//            endGame();
//            this.end = true;
//        }
        return a;
    }

    @Override
    public void render(GraphicsA g) {}

    @Override
    public void update(Double deltaTime) {
        Pair a = isSol(); // Comprobar si se ha completado el nivel
        if(a.getFirst()==0) { // Todas las sol marcadas y sin fallos = win
            endGame();
            this.end = true;
        }
    }

    @Override
    public boolean handleEvent(InputA.Event e) {
        return true;
    }

    public void clearWrongs() { // Volver al estado anterior de pulsar comprobar
        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                Cell cell = b.getCell(j,i);
                if(cell.getState()==CellState.RED)
                    cell.setState(CellState.BLUE);
            }
        }
    }


    private void endGame() { // Mostrar solo celdas solución
        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                Cell cell = b.getCell(j,i);
                if(cell.getState()!=CellState.BLUE)
                    cell.setState(CellState.NORENDER);
            }
        }
    }

    private Pair isSol() { // Check pero sin cambiar las celdas de color
        int counterBlue = 0;
        int counterRed = 0;

        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                boolean sol = b.getCell(j,i).getisSol();
                Cell cell = b.getCell(j,i);
                if(sol) {
                    if (cell.getState() == CellState.GRAY || cell.getState() == CellState.WHITE)
                        counterBlue++;
                }
                else {
                    if(cell.getState() == CellState.BLUE || cell.getState() == CellState.RED)
                        counterRed++;
                }
            }
        }

        Pair a = new Pair(counterBlue,counterRed);
        return a;
    }

    public int[][] getHorizontalHints() {
        return horizontalHints;
    }

    public int[][] getVerticalHints() {
        return verticalHints;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Board getB() {
        return b;
    }

    public boolean getEnd() { return this.end; }
}