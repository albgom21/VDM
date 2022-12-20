package gdv.ucm.appnonogram;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectLvlScene implements StateA {

    private ButtonLvl [] bLvls; //Botones de los niveles
    private ButtonBack bBack;

    private EngineA engine;
    private GraphicsA gr;

    public SelectLvlScene(EngineA engine) {
        this.engine = engine;
        this.engine.setSaveBoard(false);
        this.gr = engine.getGraphics();
        this.bLvls = new ButtonLvl[6];

        this.bLvls[0] = new ButtonLvl("4x4.png",engine, (gr.getWidthLogic()/4)  ,(gr.getHeightLogic()/5)*2,75,75, 4, 4);
        this.bLvls[1] = new ButtonLvl("5x5.png",engine,(gr.getWidthLogic()/2),(gr.getHeightLogic()/5)*2,  75,75, 5, 5);
        this.bLvls[2] = new ButtonLvl("6x6.png",engine,(gr.getWidthLogic()/4)*3,(gr.getHeightLogic()/5)*2, 75,75, 6, 6);
        this.bLvls[3] = new ButtonLvl("8x8.png",engine,(gr.getWidthLogic()/4),(gr.getHeightLogic()/5)*3,  75,75, 8, 8);
        this.bLvls[4] = new ButtonLvl("10x10.png",engine,(gr.getWidthLogic()/2) ,(gr.getHeightLogic()/5)*3,75,75, 10 , 10);
        this.bLvls[5] = new ButtonLvl("10x15.png",engine,(gr.getWidthLogic()/4)*3 ,(gr.getHeightLogic()/5)*3,75,75, 10, 15);

        this.bBack = new ButtonBack("back.png",engine,(gr.getWidthLogic()/5),gr.getBorderTop(),200/2,75/2);

        engine.getAudio().newSound("back.wav", false);
    }

    @Override
    public void update(double deltaTime) {
        if(this.engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            this.bBack.setPos(0,this.gr.getBorderTop());
            this.bLvls[0].setPos((gr.getWidthLogic()/4)  ,(gr.getHeightLogic()/5)*2);
            this.bLvls[1].setPos((gr.getWidthLogic()/2),(gr.getHeightLogic()/5)*2);
            this.bLvls[2].setPos((gr.getWidthLogic()/4)*3,(gr.getHeightLogic()/5)*2);
            this.bLvls[3].setPos((gr.getWidthLogic()/4),(gr.getHeightLogic()/5)*4);
            this.bLvls[4].setPos((gr.getWidthLogic()/2) ,(gr.getHeightLogic()/5)*4);
            this.bLvls[5].setPos((gr.getWidthLogic()/4)*3 ,(gr.getHeightLogic()/5)*4);
        }
        else
        {
            this.bBack.setPos((this.gr.getWidthLogic()/5),this.gr.getBorderTop());
            this.bLvls[0].setPos((gr.getWidthLogic()/4)  ,(gr.getHeightLogic()/5)*2);
            this.bLvls[1].setPos((gr.getWidthLogic()/2),(gr.getHeightLogic()/5)*2);
            this.bLvls[2].setPos((gr.getWidthLogic()/4)*3,(gr.getHeightLogic()/5)*2);
            this.bLvls[3].setPos((gr.getWidthLogic()/4),(gr.getHeightLogic()/5)*3);
            this.bLvls[4].setPos((gr.getWidthLogic()/2) ,(gr.getHeightLogic()/5)*3);
            this.bLvls[5].setPos((gr.getWidthLogic()/4)*3 ,(gr.getHeightLogic()/5)*3);
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        String s = "Selecciona el tamaño del puzzle";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY((int)(graphics.getHeightLogic()/4.5)), 0x442700,null, graphics.scaleToReal(20));
        for(int i = 0; i < 6; i++)
            this.bLvls[i].render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            for (int j = 0; j < 6; j++)
                this.bLvls[j].handleEvent(event);
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}