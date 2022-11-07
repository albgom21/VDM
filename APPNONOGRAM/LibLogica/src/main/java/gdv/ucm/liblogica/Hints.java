package gdv.ucm.liblogica;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class Hints implements IInterface {
    // Attributes
    private int horizontalHints[][];
    private int verticalHints[][];
    private int depthCounter [];
    private boolean ant[];
    private boolean end;
    private int x;
    private int y;
    private Board b;
    private IGraphics gr;
    //Las j´s actuan como X y las i´s actuan como Y

    public Hints(Board b, IGraphics gr) {
        this.x = b.getWidth();
        this.y = b.getHeight();
        this.b = b;
        this.end = false;

        this.gr = gr;

        //Vectors
        this.horizontalHints = new int[x][y];
        this.verticalHints = new int[y][x];
        this.ant = new boolean[x];
        this.depthCounter = new int[x];


        //Counter
        int depthx = 0;
        for (int i = 0; i < this.y; ++i) {
            depthx = 0;
            for (int j = 0; j < this.x; ++j) {
                boolean sol = b.getCell(j,i).getisSol();
                if(sol){
                    this.verticalHints[i][depthx]++;
                    this.ant[j] = true;
                    this.horizontalHints[j][this.depthCounter[j]]++;
                }
                else // !Sol
                {
                    if(this.verticalHints[i][depthx] >= 1)
                        depthx++;
                    if (this.ant[j])
                        this.depthCounter[j]++;

                    this.ant[j] = false;
                }
            }
        }
    }

    public Pair check()
    {
        int counterBlue = 0;
        int counterRed = 0;

        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                boolean sol = b.getCell(j,i).getisSol();
                Cell cell = b.getCell(j,i);
                if(sol)
                {
                    if (cell.getState() == CellState.GRAY || cell.getState() == CellState.WHITE)
                        counterBlue++;
                }
                else // !Sol
                {
                    if(cell.getState() == CellState.BLUE) {
                        cell.setState(CellState.RED); //No estamos seguros (Preguntar)
                        counterRed++;
                    }
                }
            }
        }

        Pair a = new Pair(counterBlue,counterRed);
        return a;
    }

    @Override
    public void render(IGraphics g) {
        int depth[] = new int[x];
        int dH1=0;
        for (int i = y-1; i >= 0; --i) {
            for (int j = x-1; j >= 0; --j) {
                if(verticalHints[i][j]!=0) {
                    g.drawText(Integer.toString(verticalHints[i][j]), 170 - (dH1* 35), 230 + (i * 75), 0x000000, null);
                    dH1++;
                }
                if(horizontalHints[j][i]!=0) {
                    g.drawText(Integer.toString(horizontalHints[j][i]), 220 + (j * 75),180 - (depth[j]*35), 0x000000, null);
                    depth[j]++;
                }
            }
            dH1=0;
        }
    }

    @Override
    public void update(Double deltaTime) {
        Pair a = isSol();
        if(a.getFirst()==0 && a.getSecond()==0) {
            endGame();
            this.end = true;
        }
    }

    @Override
    public void handleEvent(IInput.Event e) {
    }

    public void clearWrongs() {
        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                Cell cell = b.getCell(j,i);
                if(cell.getState()==CellState.RED)
                    cell.setState(CellState.BLUE);
            }
        }
    }

    public boolean getEnd() { return this.end; }

    private void endGame()
    {
        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                Cell cell = b.getCell(j,i);
                if(cell.getState()!=CellState.BLUE)
                    cell.setState(CellState.NORENDER);
            }
        }
    }

    private Pair isSol()
    {
        int counterBlue = 0;
        int counterRed = 0;

        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                boolean sol = b.getCell(j,i).getisSol();
                Cell cell = b.getCell(j,i);
                if(sol)
                {
                    if (cell.getState() == CellState.GRAY || cell.getState() == CellState.WHITE)
                        counterBlue++;
                }
                else // !Sol
                {
                    if(cell.getState() == CellState.BLUE || cell.getState() == CellState.RED) {
                        counterRed++;
                    }
                }
            }
        }

        Pair a = new Pair(counterBlue,counterRed);
        return a;
    }
}
