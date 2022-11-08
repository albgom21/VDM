package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class SelectLvlScene implements IState {
    private IGraphics gr;
    private IInput input;
    private ButtonLvl [] bLvls;
    private ButtonBack bBack;

    public SelectLvlScene(IEngine engine) {
        this.gr = engine.getGraphics();
        this.input = engine.getInput();
        this.bLvls = new ButtonLvl[6];
        int side = 25;
        this.bLvls[0] = new ButtonLvl("4x4.png",engine, (this.gr.getWidthLogic()/3)  ,(this.gr.getHeightLogic()/5)*2,50,50, 4, 4);
        this.bLvls[1] = new ButtonLvl("5x5.png",engine,(this.gr.getWidthLogic()/2),(this.gr.getHeightLogic()/5)*2,  50,50, 5, 5);
        this.bLvls[2] = new ButtonLvl("5x10.png",engine,(this.gr.getWidthLogic()/3)*2,(this.gr.getHeightLogic()/5)*2, 50,50, 5, 10);
        this.bLvls[3] = new ButtonLvl("8x8.png",engine,(this.gr.getWidthLogic()/3),(this.gr.getHeightLogic()/5)*3,  50,50, 8, 8);
        this.bLvls[4] = new ButtonLvl("10x10.png",engine,(this.gr.getWidthLogic()/2) ,(this.gr.getHeightLogic()/5)*3,50,50, 10 , 10);
        this.bLvls[5] = new ButtonLvl("10x15.png",engine,(this.gr.getWidthLogic()/3)*2 ,(this.gr.getHeightLogic()/5)*3,50,50, 10, 15);
        this.bBack = new ButtonBack("back.png",engine,(this.gr.getWidthLogic()/5),this.gr.getBorderTop()+(75/2),200/2,75/2);
//        engine.getAudio().newSound("click.wav");
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        String s = "Selecciona el tamaño del puzzle";
        this.gr.drawText(s,(this.gr.getWidthLogic()/2),this.gr.getHeightLogic()/5, 0x000000,null);
        for(int i = 0; i < 6; i++)
            this.bLvls[i].render(this.gr);
        this.bBack.render(this.gr);
        //multiplicar por el factor de escala, los offsets no
    }

    @Override
    public void handleInputs(/*List<Events> eventos*/) {
        for(int i = 0; i < this.input.getEvents().size(); i++) {
            for (int j = 0; j < 6; j++)
                this.bLvls[j].handleEvent(this.input.getEvents().get(i));
            this.bBack.handleEvent(this.input.getEvents().get(i));
        }
    }
}


