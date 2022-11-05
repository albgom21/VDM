package gdv.ucm.liblogica;

import gdv.ucm.libengine.IButton;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class TitleScene implements IState {
    private IFont textoJugar;
    private IGraphics gr;
    private IButton bPlay;
    private IInput input;

    public TitleScene(IEngine engine) {
        this.gr = engine.getGraphics();
        this.input = engine.getInput();
        this.textoJugar = this.gr.newFont("coolvetica.otf", 30 , false);
        this.gr.setFont(this.textoJugar);
        this.bPlay = this.gr.newButton("perroTriste.jpg",(this.gr.getWidthLogic()/2) - 25,this.gr.getHeightLogic()/2,200,200);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.gr.drawText("NONOGRAMAS",(this.gr.getWidthLogic()/2) - 50,this.gr.getHeightLogic()/10, 0x000000);
        this.gr.drawText("JUGAR",(this.gr.getWidthLogic()/2) - 25 ,this.gr.getHeightLogic()/2, 0x000000);
        this.bPlay.render(this.gr);
    }

    @Override
    public void handleInputs() {
        for(int i = 0; i < this.input.getEvents().size(); i++)
            this.bPlay.handleEvent(this.input.getEvents().get(i));
    }
}


