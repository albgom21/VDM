package gdv.ucm.appnonogram;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class LvlThemeScene implements StateA {
    private ButtonRead [] bLvls;
    private ButtonBack bBack;

    private EngineA engine;
    private GraphicsA gr;

    public LvlThemeScene(EngineA engine, String type) {
        this.engine = engine;
        this.gr = engine.getGraphics();
        this.bLvls = new ButtonRead[20];
        int y = 0;
        int x = 0;
        for(int i = 1; i <= 20; i++) {
            if(x>=4) {
                y++;
                x = 0;
            }
            //Si no está bloqueado
            if((type == "a" && engine.getStats().getBosque()[i-1]) ||
            (type == "b" && engine.getStats().getEmoji()[i-1]) ||
            (type == "c" && engine.getStats().getComida()[i-1]) ||
            (type == "d" && engine.getStats().getNavidad()[i-1]))
                this.bLvls[i - 1] = new ButtonRead(i + ".png", engine, ((gr.getWidthLogic() / 11) * (x + (x - 1))) + 125, ((gr.getHeightLogic() / 14) * (int)(y+(y-1))) + 200, 50, 50, i, type, false);
            else
                this.bLvls[i - 1] = new ButtonRead(i + "b.png", engine, ((gr.getWidthLogic() / 11) * (x + (x - 1))) + 125, ((gr.getHeightLogic() / 14) * (int)(y+(y-1))) + 200, 50, 50, i, type, true);
            x++;
        }

        this.bBack = new ButtonBack("back.png",engine,(gr.getWidthLogic()/5),gr.getBorderTop(),200/2,75/2);

        engine.getAudio().newSound("back.wav", false);
    }

    @Override
    public void update(double deltaTime) {
        if(this.engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.bBack.setPos(0,this.gr.getBorderTop());
            int y = 0;
            int x = 0;
            for(int i = 1; i <= 20; i++)
            {
                if(x>=5) {
                    y++;
                    x = 0;
                }
                this.bLvls[i-1].setPos(((gr.getWidthLogic() / 11) * (x + (x - 1))) + 125, ((gr.getHeightLogic() / 14) * (int)(y+(y-1))) + 150);
                x++;
            }
        }
        else
        {
            this.bBack.setPos((gr.getWidthLogic()/5),gr.getBorderTop());
            int y = 0;
            int x = 0;
            for(int i = 1; i <= 20; i++)
            {
                if(x>=4) {
                    y++;
                    x = 0;
                }
                this.bLvls[i-1].setPos(((gr.getWidthLogic() / 11) * (x + (x - 1))) + 125, ((gr.getHeightLogic() / 14) * (int)(y+(y-1))) + 200);
                x++;
            }
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        String s = "Selecciona el tamaño del puzzle";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/6), 0x442700,null, graphics.scaleToReal(15));
        for(int i = 0; i < 20; i++)
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
            for (int j = 0; j < 20; j++)
                this.bLvls[j].handleEvent(event);
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}
