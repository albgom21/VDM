package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IState;

public class TitleScene implements IState {
    private IFont textoJugar;
    private IGraphics gr;


    public TitleScene(IEngine engine) {
        this.gr = engine.getGraphics();
        this.textoJugar = this.gr.newFont("zero.ttf", 30 , false);
        this.gr.setFont(this.textoJugar);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.gr.drawText("NONOGRAMAS",(this.gr.getWidthLogic()/2) - 50,this.gr.getHeightLogic()/10, 0x000000);
        this.gr.drawText("JUGAR",(this.gr.getWidthLogic()/2) - 25 ,this.gr.getHeightLogic()/2, 0x000000);

    }

    @Override
    public void handleInputs(/*List<Events> eventos*/) {

    }
}


