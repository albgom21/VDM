package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectPaletaScene implements StateA {
    private ButtonColorPaleta b0;
    private ButtonColorPaleta b1;
    private ButtonColorPaleta b2;
    private ButtonColorPaleta b3;
    private ButtonBackTitle bBack;
    private EngineA engine;
    private ImageA coins;

    public SelectPaletaScene(EngineA engine) {
        GraphicsA gr = engine.getGraphics();
        this.engine = engine;

        if(!engine.getAudio().isLoaded("click.wav"))
            engine.getAudio().newSound("click.wav", false);
        if(!engine.getAudio().isLoaded("error.wav"))
            engine.getAudio().newSound("error.wav", false);
        this.b0 = new ButtonColorPaleta("paletaNormal",engine, gr.getWidthLogic()/2  ,(gr.getHeightLogic()/5)*2 -50,100,100,0, this.engine.getStats().isPaletaUnlock(0));
        this.b1 = new ButtonColorPaleta("paleta1",engine, gr.getWidthLogic()/2  ,(gr.getHeightLogic()/5)*3 -50,100,100,1,this.engine.getStats().isPaletaUnlock(1));
        this.b2 = new ButtonColorPaleta("paleta2",engine, gr.getWidthLogic()/2  ,(gr.getHeightLogic()/5)*4 -50,100,100,2,this.engine.getStats().isPaletaUnlock(2));
        this.b3 = new ButtonColorPaleta("paleta3",engine, gr.getWidthLogic()/2  ,(gr.getHeightLogic()/5)*5 -50,100,100,3,this.engine.getStats().isPaletaUnlock(3));

        this.bBack = new ButtonBackTitle("back.png",engine,(gr.getWidthLogic()/5),gr.getBorderTop(),200/2,75/2);
        this.coins = gr.newImage("moneda.png");
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(GraphicsA graphics) {
        graphics.drawText(Integer.toString(engine.getStats().getMonedas()),graphics.logicToRealX(((graphics.getWidthLogic()/5)*4)-35),graphics.logicToRealY(graphics.getHeightLogic()/11), 0x442700,null, graphics.scaleToReal(20));
        graphics.drawImage(this.coins,(graphics.getWidthLogic()/5)*4,graphics.getHeightLogic()/15, 20, 20);
        String s = "Selecciona la paleta usada en el juego";
        graphics.drawText(s,graphics.logicToRealX(graphics.getWidthLogic()/2),graphics.logicToRealY(graphics.getHeightLogic()/6), 0x442700,null, graphics.scaleToReal(20));
        this.bBack.render(graphics);
        this.b0.render(graphics);
        this.b1.render(graphics);
        this.b2.render(graphics);
        this.b3.render(graphics);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bBack.handleEvent(event);
            this.b0.handleEvent(event);
            this.b1.handleEvent(event);
            this.b2.handleEvent(event);
            this.b3.handleEvent(event);
        }
        input.clearEvents();
    }
}
