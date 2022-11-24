package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectGamemodeScene implements StateA {
    private ButtonFast bFast;
    private ButtonLore bLore;
    private ButtonBack bBack;

    public SelectGamemodeScene(EngineA engine) {
        GraphicsA gr = engine.getGraphics();

        this.bFast = new ButtonFast("rapido.png",engine, gr.getWidthLogic()/2  ,(gr.getHeightLogic()/5)*2,200,75);
        this.bLore = new ButtonLore("historia.png",engine,gr.getWidthLogic()/2,(int)((gr.getHeightLogic()/5)*3.5),  200,75);

        if(!engine.getAudio().isLoaded("back.wav"))
            engine.getAudio().newSound("back.wav", false);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(GraphicsA graphics) {
        String s = "Selecciona el modo de juego";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/6), 0x442700,null, graphics.scaleToReal(20));
        this.bFast.render(graphics);
        this.bLore.render(graphics);
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
        }
        input.clearEvents();
    }
}