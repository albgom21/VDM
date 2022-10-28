package gdv.ucm.liblogica;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInterface;

public class Hints implements IInterface {
    // Attributes
    private int horizontalHints[][];
    private int verticalHints[][];
    private int depthCounter [];
    private boolean ant[];
    private int x;
    private int y;

    //Las j´s actuan como X y las i´s actuan como Y

    public Hints(Board b) {
        x = b.getWidth();
        y = b.getHeight();

        //Vectors
        horizontalHints = new int[x][y];
        verticalHints = new int[y][x];
        ant = new boolean[x];
        depthCounter = new int[x];


        //Counter
        int depthx = 0;
        for (int i = 0; i < y; ++i) {
            depthx = 0;
            for (int j = 0; j < x; ++j) {
                boolean sol = b.getCell(j,i).getisSol();
                if(sol){
                    verticalHints[i][depthx]++;
                    ant[j] = true;
                    horizontalHints[depthCounter[j]][j]++;
                }
                else // !Sol
                {
                    if(verticalHints[i][depthx] >= 1)
                        depthx++;
                    if (ant[j])
                        depthCounter[j]++;

                    ant[j] = false;
                }
            }
        }
    }

    public Pair Check(Board b)
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
        int dH1=0;
        int dH2=0;
        int dV1=y-1;
        int dV2=x-1;
        for (int i = y-1; i >= 0; --i) {
            for (int j = x-1; j >= 0; --j) {
                if(verticalHints[i][j]!=0) {
                    g.drawText(Integer.toString(verticalHints[i][j]), 175 - (dH1 * 35), 230 + (dV1 * 75), 0x000000);
                    dH1++;
                }
                if(horizontalHints[j][i]!=0) {
                    g.drawText(Integer.toString(horizontalHints[j][i]), 220 + (dV2 * 75), 175 - (dH2 * 35), 0x000000);
                    dH2++;
                }
            }
            if(i == dV2)
                dV2--;
            dH1=0;
            dH2=0;
            dV1--;
        }
    }

    @Override
    public void update(Double deltaTime) {

    }
}
