package gdv.ucm.appnonogram;


import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;

public class RenderBoard {
    private Board b;
    private RenderCell renderCell;
    private ImageA fullLife;
    private ImageA noLife;

    RenderBoard(Board b){
        this.b = b;
        this.renderCell = new RenderCell();
        this.fullLife = b.getEngine().getGraphics().newImage("vida.png");
        this.noLife = b.getEngine().getGraphics().newImage("vidaGastada.png");
    }

    public void render(GraphicsA gr, EngineA engine){
        for (int i = 0; i < this.b.getWidth(); ++i) {
            for (int j = 0; j < this.b.getHeight(); ++j) {
                renderCell.render(gr, this.b.getCell(i,j), engine);
            }
        }
    }

    public void renderLifes(GraphicsA gr) //Preguntar carga de recursos (se ve raro el codigo)
    {
        for(int i = 0; i < 3; i++)
        {
            if(i < b.getLives()) //Renderizar vidas actuales
                gr.drawImage(this.fullLife,(gr.getWidthLogic()/3)*2 - (25 + i*40),(gr.getHeightLogic()/10)*9, 35, 35);
            else //Renderizar perdidas
                gr.drawImage(this.noLife,(gr.getWidthLogic()/3)*2 - (25 + i*40),(gr.getHeightLogic()/10)*9, 35, 35);
        }
    }
}
