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
        this.bBack = new ButtonBack("back.png", engine,this.gr.getWidthLogic()/2, (this.gr.getHeightLogic()/6)*5,200/2,75/2);
        this.b = b;
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        String s = "¡ENHORABUENA!";
        this.gr.drawText(s,this.gr.logicToRealX(this.gr.getWidthLogic()/2),this.gr.logicToRealY(this.gr.getHeightLogic()/4), 0x442700, null);
        this.b.render(this.gr);
        this.bBack.render(this.gr);
    }

    @Override
    public void handleInputs() {
        for(int i = 0; i < this.input.getEvents().size(); i++)
            this.bBack.handleEvent(this.input.getEvents().get(i));
    }
}
