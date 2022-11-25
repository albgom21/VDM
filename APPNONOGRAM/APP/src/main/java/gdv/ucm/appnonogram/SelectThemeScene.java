package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectThemeScene implements StateA {
    private ButtonLvlLore bTheme1;
    private ButtonLvlLore bTheme2;
    private ButtonLvlLore bTheme3;
    private ButtonLvlLore bTheme4;
    private ButtonBack bBack;

    public SelectThemeScene(EngineA engine) {
        GraphicsA gr = engine.getGraphics();

        this.bTheme1 = new ButtonLvlLore("bosque.png",engine, gr.getWidthLogic()/4  ,(gr.getHeightLogic()/5)*2,150,150, "a");
        this.bTheme2 = new ButtonLvlLore("emoji.png",engine,(gr.getWidthLogic()/4)*3,(gr.getHeightLogic()/5)*2,  150,150, "b");
        this.bTheme3 = new ButtonLvlLore("comida.png",engine, gr.getWidthLogic()/4  ,(int)((gr.getHeightLogic()/5)*3.5),150,150, "c");
        this.bTheme4 = new ButtonLvlLore("navidad.png",engine,(gr.getWidthLogic()/4)*3,(int)((gr.getHeightLogic()/5)*3.5),  150,150, "d");
        this.bBack = new ButtonBack("back.png",engine,(gr.getWidthLogic()/5),gr.getBorderTop(),200/2,75/2);

        if(!engine.getAudio().isLoaded("back.wav"))
            engine.getAudio().newSound("back.wav", false);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(GraphicsA graphics) {
        String s = "Elige la categoria en la que quieras jugar";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/5), 0x442700,null, graphics.scaleToReal(20));
        this.bTheme1.render(graphics);
        this.bTheme2.render(graphics);
        this.bTheme3.render(graphics);
        this.bTheme4.render(graphics);
        this.bBack.render(graphics);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bTheme1.handleEvent(event);
            this.bTheme2.handleEvent(event);
            this.bTheme3.handleEvent(event);
            this.bTheme4.handleEvent(event);
            this.bBack.handleEvent(event);
        }
        input.clearEvents();
    }
}
