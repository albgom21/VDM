package gdv.ucm.appnonogram;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectGamemodeScene implements StateA {
    private ButtonFast bFast;
    private ButtonLore bLore;
    private ButtonBackTitle bBack;

    private EngineA engine;
    private GraphicsA gr;

    public SelectGamemodeScene(EngineA engine) {
        this.engine = engine;
        this.engine.setSaveBoard(false);

        this.gr = engine.getGraphics();

        this.bFast = new ButtonFast("rapido.png",engine, gr.getWidthLogic()/2  ,(gr.getHeightLogic()/5)*2,200,75);
        this.bLore = new ButtonLore("historia.png",engine,gr.getWidthLogic()/2,(int)((gr.getHeightLogic()/5)*3.5),  200,75);
        this.bBack = new ButtonBackTitle("back.png",engine,(gr.getWidthLogic()/5),gr.getBorderTop(),200/2,75/2);

        engine.getAudio().newSound("back.wav", false);
    }

    @Override
    public void update(double deltaTime) {
        if(this.engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            this.bFast.setPos(this.gr.getWidthLogic()/2  ,(this.gr.getHeightLogic()/5)*2);
            this.bLore.setPos(this.gr.getWidthLogic()/2,(int)((this.gr.getHeightLogic()/5)*3.5));
            this.bBack.setPos(0,this.gr.getBorderTop());
        }
        else
        {
            this.bFast.setPos(this.gr.getWidthLogic()/2  ,(this.gr.getHeightLogic()/5)*2);
            this.bLore.setPos(this.gr.getWidthLogic()/2,(int)((this.gr.getHeightLogic()/5)*3.5));
            this.bBack.setPos((this.gr.getWidthLogic()/5),this.gr.getBorderTop());
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        String s = "Selecciona el modo de juego";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY((int)(graphics.getHeightLogic()/4.5)), 0x442700,null, graphics.scaleToReal(20));
        this.bFast.render(graphics);
        this.bLore.render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bLore.handleEvent(event);
            this.bFast.handleEvent(event);
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}
