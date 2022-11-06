package gdv.ucm.liblogica;

import gdv.ucm.libengine.IButton;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
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
        this.bLvls[0] = new ButtonLvl("4x4.png",engine, (this.gr.getWidthLogic()/2) - 200,this.gr.getHeightLogic()/2,200,200, 4, 4);
        this.bLvls[1] = new ButtonLvl("5x5.png",engine,(this.gr.getWidthLogic()/2) ,this.gr.getHeightLogic()/2,200,200, 5, 5);
        this.bLvls[2] = new ButtonLvl("5x10.png",engine,(this.gr.getWidthLogic()/2) + 200,this.gr.getHeightLogic()/2,200,200, 5, 10);
        this.bLvls[3] = new ButtonLvl("8x8.png",engine,(this.gr.getWidthLogic()/2) - 200,this.gr.getHeightLogic()/2 + 300,200,200, 8, 8);
        this.bLvls[4] = new ButtonLvl("10x10.png",engine,(this.gr.getWidthLogic()/2),this.gr.getHeightLogic()/2 + 300,200,200, 10 , 10);
        this.bLvls[5] = new ButtonLvl("10x15.png",engine,(this.gr.getWidthLogic()/2) + 200,this.gr.getHeightLogic()/2 + 300,200,200, 10, 15);
        this.bBack = new ButtonBack("back.png",engine,100,100,200,75);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.gr.drawText("Selecciona el tamaño del puzzle",(this.gr.getWidthLogic()/2) - 175,this.gr.getHeightLogic()/5, 0x000000);
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


