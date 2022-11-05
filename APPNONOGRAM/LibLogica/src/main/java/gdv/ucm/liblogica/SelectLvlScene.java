package gdv.ucm.liblogica;

import gdv.ucm.libengine.IButton;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class SelectLvlScene implements IState {
    private IGraphics gr;
    private IButton bPlay;
    private IInput input;

    public SelectLvlScene(IEngine engine) {
        this.gr = engine.getGraphics();
        this.input = engine.getInput();
        this.bPlay = this.gr.newButton("perroTriste.jpg",(this.gr.getWidthLogic()/2) - 25,this.gr.getHeightLogic()/2,200,200);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.gr.drawText("Selecciona el tamaño del puzzle",(this.gr.getWidthLogic()/2) - 175,this.gr.getHeightLogic()/5, 0x000000);
        this.bPlay.render(this.gr);
        //multiplicar por el factor de escala, los offsets no
    }

    @Override
    public void handleInputs(/*List<Events> eventos*/) {
        for(int i = 0; i < this.input.getEvents().size(); i++)
            this.bPlay.handleEvent(this.input.getEvents().get(i));
    }
}


