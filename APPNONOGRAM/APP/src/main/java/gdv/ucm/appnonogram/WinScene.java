package gdv.ucm.appnonogram;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class WinScene implements StateA {
    private Board b;
    private RenderBoard renderBoard;
    private ButtonBack bBack;
    private ButtonShare bShare;
    private ButtonNext bNext;
    private EngineA engine;
    private boolean random;
    private ImageA coins;
    private int reward;

    private GraphicsA gr;

    public WinScene(EngineA engine, Board b, boolean random, int lvl, String type, int coins) {
        this.engine = engine;
        this.engine.setSaveBoard(false);
        this.random = random;
        this.reward = coins;
        this.gr = engine.getGraphics();
        this.bBack = new ButtonBack("back.png", engine,(gr.getWidthLogic()/5),(int)((gr.getHeightLogic()/6)*5.75),200/2,75/2);
        this.bShare = new ButtonShare("compartir.png", engine,gr.getWidthLogic()/2, (gr.getHeightLogic()/6)*5,200/2,75/2, random,lvl-1, type);
        this.b = b;
        this.renderBoard = new RenderBoard(this.b, this.gr);
        this.engine.getStats().addMoneda(coins);
        this.coins = gr.newImage("moneda.png");

        if(!random) //Si no es una escena random, pasamos al siguiente nivel
            this.bNext = new ButtonNext("siguiente.png",engine,(gr.getWidthLogic()/5)*4, (int)((gr.getHeightLogic()/6)*5.75), 200/2, 75/2, lvl, type);
    }

    @Override
    public void update(double deltaTime) {
        if(this.engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.bBack.setPos(0,this.gr.getHeightLogic()/4);
            this.bShare.setPos(0,(this.gr.getHeightLogic()/4)*2);
            if(!random)
                this.bNext.setPos(0,(this.gr.getHeightLogic()/4)*3);
        }
        else
        {
            this.bBack.setPos((gr.getWidthLogic()/5),(int)((gr.getHeightLogic()/6)*5.75));
            this.bShare.setPos(gr.getWidthLogic()/2, (gr.getHeightLogic()/6)*5);
            if(!random)
                this.bNext.setPos((gr.getWidthLogic()/5)*4, (int)((gr.getHeightLogic()/6)*5.75));
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        String s = "¡ENHORABUENA!";
        if(this.engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            graphics.drawImage(this.coins, graphics.getWidthLogic() - 50, graphics.getHeightLogic() / 2 + 70, 20, 20);
            graphics.drawText("+ " + Integer.toString(this.reward), graphics.logicToRealX(graphics.getWidthLogic() - 50), graphics.logicToRealY((int) ((graphics.getHeightLogic() / 2) + 50)),
                    0x06561e, null, graphics.scaleToReal(20));
            graphics.drawText(s, graphics.logicToRealX(graphics.getWidthLogic() - 50), graphics.logicToRealY(graphics.getHeightLogic() / 2),
                    0x06561e, null, graphics.scaleToReal(20));
        }
        else
        {
            graphics.drawImage(this.coins, (graphics.getWidthLogic() / 2) + 30, graphics.getHeightLogic() / 7 - 50, 20, 20);
            graphics.drawText("+ " + Integer.toString(this.reward), graphics.logicToRealX(graphics.getWidthLogic() / 2), graphics.logicToRealY((int) ((graphics.getHeightLogic() / 9) * 1.5) - 50),
                    0x06561e, null, graphics.scaleToReal(20));
            graphics.drawText(s, graphics.logicToRealX(graphics.getWidthLogic() / 2), graphics.logicToRealY(graphics.getHeightLogic() / 9 - 50),
                    0x06561e, null, graphics.scaleToReal(20));
        }
        this.renderBoard.render(graphics, engine);
        this.bBack.render(graphics);
        if(!random)
            this.bNext.render(graphics);
        this.bShare.render(graphics);
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
            this.bShare.handleEvent(event);
        }
        input.clearEvents();
    }
}