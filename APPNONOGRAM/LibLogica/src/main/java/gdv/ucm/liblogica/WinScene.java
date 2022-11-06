package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class WinScene implements IState {
    private IGraphics gr;
    private IInput input;
    private Board b;
    private ButtonBack bBack;

    public WinScene(IEngine engine, Board b) {
        this.gr = engine.getGraphics();
        this.input = engine.getInput();
        this.bBack = new ButtonBack("back.png",engine,(this.gr.getWidthLogic()/2) - 25,this.gr.getHeightLogic()/2,200,75);
        this.b = b;
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.gr.drawText("Enhorabuena!",(this.gr.getWidthLogic()/2) - 50,this.gr.getHeightLogic()/10, 0x000000);
        this.b.render(this.gr);
        this.bBack.render(this.gr);
    }

    @Override
    public void handleInputs() {
        for(int i = 0; i < this.input.getEvents().size(); i++)
            this.bBack.handleEvent(this.input.getEvents().get(i));
    }
}
