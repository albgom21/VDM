package gdv.ucm.liblogica;

public class Hints {
    // Attributes
    private int horizontalHints[][];
    private int verticalHints[][];

    public Hints(Board b){
        horizontalHints = new int[b.getWidth()][(b.getHeight()+1)/2];
        verticalHints = new int[b.getHeight()][(b.getWidth()+1)/2];
        boolean a = true;
        int n = 0;
        int depth = 0;
        for (int i = 0; i < b.getWidth(); ++i) {
            for (int j = 0; j < b.getHeight(); ++j) {
                boolean sol = b.getCell(i,j).getisSol();
                if(sol){
                    n++;
                }
                else if (!sol && n!=0){
                    verticalHints[i][depth] = n;
                    depth++;
                    n=0;
                }
            }
        }
    }
}
