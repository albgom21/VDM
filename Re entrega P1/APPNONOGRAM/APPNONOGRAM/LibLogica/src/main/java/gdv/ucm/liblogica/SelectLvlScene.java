package gdv.ucm.liblogica;

import java.util.ArrayList;
import java.util.Iterator;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class SelectLvlScene implements IState {

    private ButtonLvl [] bLvls;
    private ButtonBack bBack;

    public SelectLvlScene(IEngine engine) {
        IGraphics gr = engine.getGraphics();

        //Botones distintos niveles
        this.bLvls = new ButtonLvl[6];
        this.bLvls[0] = new ButtonLvl("4x4.png",engine, (gr.getWidthLogic()/3)  ,(gr.getHeightLogic()/5)*2,50,50, 4, 4);
        this.bLvls[1] = new ButtonLvl("5x5.png",engine,(gr.getWidthLogic()/2),(gr.getHeightLogic()/5)*2,  50,50, 5, 5);
        this.bLvls[2] = new ButtonLvl("5x10.png",engine,(gr.getWidthLogic()/3)*2,(gr.getHeightLogic()/5)*2, 50,50, 5, 10);
        this.bLvls[3] = new ButtonLvl("8x8.png",engine,(gr.getWidthLogic()/3),(gr.getHeightLogic()/5)*3,  50,50, 8, 8);
        this.bLvls[4] = new ButtonLvl("10x10.png",engine,(gr.getWidthLogic()/2) ,(gr.getHeightLogic()/5)*3,50,50, 10 , 10);
        this.bLvls[5] = new ButtonLvl("10x15.png",engine,(gr.getWidthLogic()/3)*2 ,(gr.getHeightLogic()/5)*3,50,50, 10, 15);

        this.bBack = new ButtonBack("back.png",engine,(gr.getWidthLogic()/5),gr.getBorderTop()+(75/2),200/2,75/2);

        engine.getAudio().newSound("back.wav", false);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(IGraphics graphics) {
        String s = "Selecciona el tamaño del puzzle";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/4), 0x442700,null, graphics.scaleToReal(15));
        for(int i = 0; i < 6; i++)
            this.bLvls[i].render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(IInput input) {
        ArrayList<IInput.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            for (int j = 0; j < 6; j++)
                this.bLvls[j].handleEvent(event);
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}