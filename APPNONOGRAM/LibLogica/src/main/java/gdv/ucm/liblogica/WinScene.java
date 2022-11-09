package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class WinScene implements IState {

    private IInput input;
    private Board b;
    private ButtonBack bBack;

    public WinScene(IEngine engine, Board b) {
        IGraphics gr = engine.getGraphics();
        this.input = engine.getInput();
        this.bBack = new ButtonBack("back.png", engine,gr.getWidthLogic()/2, (gr.getHeightLogic()/6)*5,200/2,75/2);
        this.b = b;

        if(!engine.getAudio().isLoaded("win.wav"))
            engine.getAudio().newSound("win.wav", false);
        engine.getAudio().playSound("win");
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(IGraphics graphics) {
        String s = "¡ENHORABUENA!";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/8),
                    0x06561e, null, graphics.scaleToReal(15));
        this.b.render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(IInput input) {
        for(int i = 0; i < input.getEvents().size(); i++)
            if(this.bBack.handleEvent(input.getEvents().get(i)))
                input.clearIndexEvent(i);
    }
}