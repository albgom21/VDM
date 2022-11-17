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

    public WinScene(EngineA engine, Board b) {
        GraphicsA gr = engine.getGraphics();
        this.bBack = new ButtonBack("back.png", engine,gr.getWidthLogic()/2, (gr.getHeightLogic()/6)*5,200/2,75/2);
        this.b = b;
        this.renderBoard = new RenderBoard(this.b);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(GraphicsA graphics) {
        String s = "¡ENHORABUENA!";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/8),
                    0x06561e, null, graphics.scaleToReal(20));
        this.renderBoard.render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}