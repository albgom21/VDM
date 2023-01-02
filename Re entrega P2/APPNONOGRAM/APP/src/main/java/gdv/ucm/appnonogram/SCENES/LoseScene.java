package gdv.ucm.appnonogram.SCENES;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

import gdv.ucm.appnonogram.BUTTONS.ButtonBack;
import gdv.ucm.appnonogram.BUTTONS.ButtonRetry;

public class LoseScene implements StateA {
    private ButtonRetry bRetry;
    private ButtonBack bBack;

    private EngineA engine;
    private GraphicsA gr;

    public LoseScene(EngineA engine, int cols, int fils, int lvl, String type) {
        this.engine = engine;
        this.engine.setSaveBoard(false);

        this.gr = engine.getGraphics();
        engine.getAudio().playSound("lose");
        this.bBack = new ButtonBack("back.png",engine,gr.getWidthLogic()/3, (gr.getHeightLogic()/6)*5,200/2,75/2);
        this.bRetry = new ButtonRetry("retry.png", engine,(gr.getWidthLogic()/3)*2, (gr.getHeightLogic()/6)*5,200/2,75/2, cols, fils, lvl,type);
    }

    @Override
    public void update(double deltaTime) {
        if(this.engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.bBack.setPos(gr.getWidthLogic()/3, (gr.getHeightLogic()/6)*5);
            this.bRetry.setPos((gr.getWidthLogic()/3)*2, (gr.getHeightLogic()/6)*5);
        }
        else
        {
            this.bBack.setPos(gr.getWidthLogic()/3, (gr.getHeightLogic()/6)*5);
            this.bRetry.setPos((gr.getWidthLogic()/3)*2, (gr.getHeightLogic()/6)*5);
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        String s = "¡Has perdido!";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/3),
                0xac3232, null, graphics.scaleToReal(60));
        this.bRetry.render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bRetry.handleEvent(event);
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}
