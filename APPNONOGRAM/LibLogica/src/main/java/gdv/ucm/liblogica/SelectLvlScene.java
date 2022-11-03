package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IState;

public class SelectLvlScene implements IState {
    private IFont textoJugar;
    private IGraphics gr;


    public SelectLvlScene(IEngine engine) {
        this.gr = engine.getGraphics();
        this.textoJugar = this.gr.newFont("zero.ttf", 30 , false);
        this.gr.setFont(this.textoJugar);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.gr.drawText("Selecciona el tamaño del puzzle",(this.gr.getWidthLogic()/2) - 175,this.gr.getHeightLogic()/5, 0x000000);
        //multiplicar por el factor de escala, los offsets no
    }

    @Override
    public void handleInputs(/*List<Events> eventos*/) {

    }
}


