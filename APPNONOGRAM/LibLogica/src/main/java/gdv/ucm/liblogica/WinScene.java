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
        this.bBack = new ButtonBack("back.png", engine,(this.gr.getWidth()/3), this.gr.getBorderTop(),200/2,75/2);
        this.b = b;
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        String s = "Enhorabuena!";
        this.gr.drawText(s,(this.gr.getWidth()/2) - this.gr.getWidthString(s)/2,this.gr.getHeight()/9, 0x000000);
        this.b.render(this.gr);
        this.bBack.render(this.gr);
    }

    @Override
    public void handleInputs() {
        for(int i = 0; i < this.input.getEvents().size(); i++)
            this.bBack.handleEvent(this.input.getEvents().get(i));
    }
}
