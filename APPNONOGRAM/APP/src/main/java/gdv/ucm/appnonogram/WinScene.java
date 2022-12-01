package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class WinScene implements StateA {
    private Board b;
    private RenderBoard renderBoard;
    private ButtonBack bBack;
    private ButtonNext bNext;
    private EngineA engine;
    private boolean random;

    public WinScene(EngineA engine, Board b, boolean random, int lvl, String type) {
        this.engine = engine;
        this.random = random;
        GraphicsA gr = engine.getGraphics();
        this.bBack = new ButtonBack("back.png", engine,gr.getWidthLogic()/2, (gr.getHeightLogic()/6)*5,200/2,75/2);
        this.b = b;
        this.renderBoard = new RenderBoard(this.b);

        if(!random) //Si no es una escena random, pasamos al siguiente nivel
            this.bNext = new ButtonNext("siguiente.png",engine,gr.getWidthLogic()/2, (int)((gr.getHeightLogic()/6)*5.75), 200/2, 75/2, lvl, type);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(GraphicsA graphics) {
        String s = "¡ENHORABUENA!";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/8),
                    0x06561e, null, graphics.scaleToReal(20));
        this.renderBoard.render(graphics, engine);
        this.bBack.render(graphics);
        if(!random)
            this.bNext.render(graphics);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bBack.handleEvent(event);
            if(!random)
                this.bNext.handleEvent(event);
        }
        input.clearEvents();
    }
}