package gdv.ucm.liblogica;

import gdv.ucm.libengine.IGraphics;

public class RenderBoard {
    private Board b;
    private RenderCell renderCell;
    RenderBoard(Board b){
        this.b = b;
        this.renderCell = new RenderCell();
    }
    public void render(IGraphics gr){
        for (int i = 0; i < this.b.getWidth(); ++i) {
            for (int j = 0; j < this.b.getHeight(); ++j) {
                renderCell.render(gr, this.b.getCell(i,j));
            }
        }
    }
}
