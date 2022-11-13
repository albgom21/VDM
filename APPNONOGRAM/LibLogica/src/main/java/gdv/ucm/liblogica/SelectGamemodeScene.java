package gdv.ucm.liblogica;

import java.awt.Button;
import java.util.ArrayList;
import java.util.Iterator;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class SelectGamemodeScene implements IState {
    private ButtonFast bFast;
    private ButtonLore bLore;
    private ButtonBack bBack;

    public SelectGamemodeScene(IEngine engine) {
        IGraphics gr = engine.getGraphics();

        this.bFast = new ButtonFast("rapido.png",engine, gr.getWidthLogic()/2  ,(gr.getHeightLogic()/5)*2,200,75);
        this.bLore = new ButtonLore("historia.png",engine,gr.getWidthLogic()/2,(int)((gr.getHeightLogic()/5)*3.5),  200,75);

        if(!engine.getAudio().isLoaded("back.wav"))
            engine.getAudio().newSound("back.wav", false);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(IGraphics graphics) {
        String s = "Selecciona el modo de juego";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/6), 0x442700,null, graphics.scaleToReal(20));
        this.bFast.render(graphics);
        this.bLore.render(graphics);
    }

    @Override
    public void handleInputs(IInput input) {
        ArrayList<IInput.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            this.bLore.handleEvent(event);
            this.bFast.handleEvent(event);
        }
        input.clearEvents();
    }
}
