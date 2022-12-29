package gdv.ucm.liblogica;

import java.util.ArrayList;
import java.util.Iterator;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class WinScene implements IState {
    private Board b;
    private RenderBoard renderBoard;
    private ButtonBack bBack;

    public WinScene(IEngine engine, Board b) {
        IGraphics gr = engine.getGraphics();
        this.bBack = new ButtonBack("back.png", engine,gr.getWidthLogic()/2, (gr.getHeightLogic()/6)*5,200/2,75/2);
        this.b = b;
        this.renderBoard = new RenderBoard(this.b);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(IGraphics graphics) {
        String s = "¡ENHORABUENA!";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/8),
                    0x06561e, null, graphics.scaleToReal(20));
        this.renderBoard.render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(IInput input) {
        ArrayList<IInput.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}