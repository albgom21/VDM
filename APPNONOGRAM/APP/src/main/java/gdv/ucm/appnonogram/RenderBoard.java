package gdv.ucm.appnonogram;


import com.example.libenginea.GraphicsA;

public class RenderBoard {
    private Board b;
    private RenderCell renderCell;
    RenderBoard(Board b){
        this.b = b;
        this.renderCell = new RenderCell();
    }
    public void render(GraphicsA gr){
        for (int i = 0; i < this.b.getWidth(); ++i) {
            for (int j = 0; j < this.b.getHeight(); ++j) {
                renderCell.render(gr, this.b.getCell(i,j));
            }
        }
    }
}
