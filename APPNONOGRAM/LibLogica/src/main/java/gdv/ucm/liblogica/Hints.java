package gdv.ucm.liblogica;

public class Hints {
    // Attributes
    private int horizontalHints[][];
    private int verticalHints[][];
    private int depthCounter [];
    private boolean ant[];

    public Hints(Board b){
        //Vectors
        horizontalHints = new int[b.getWidth()][(b.getHeight()+1)/2];
        verticalHints = new int[b.getHeight()][(b.getWidth()+1)/2];
        ant = new boolean[b.getWidth()];
        depthCounter = new int[b.getWidth()];

        //Comprobar si se inicializa depth a 0 y ant a false (Preguntar)

        //Counter
        int depthx = 0;
        for (int i = 0; i < b.getHeight(); ++i) {
            depthx = 0;
            for (int j = 0; j < b.getWidth(); ++j) {
                boolean sol = b.getCell(i,j).getisSol();
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

        for (int i = 0; i < b.getHeight(); ++i) {
            for (int j = 0; j < b.getWidth(); ++j) {
                boolean sol = b.getCell(i,j).getisSol();
                Cell cell = b.getCell(i,j);
                if(sol)
                {
                    if (cell.getState() == CellState.GREY || cell.getState() == CellState.WHITE)
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
}
