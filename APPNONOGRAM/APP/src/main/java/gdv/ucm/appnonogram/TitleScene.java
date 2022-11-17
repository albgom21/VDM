package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.FontA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class TitleScene implements StateA {
    private FontA font;
    private ButtonPlay bPlay;
    private ImageA logo;

    public TitleScene(EngineA engine) {
        GraphicsA gr = engine.getGraphics();
        //CARGA DE RECURSOS
        if(!engine.getAudio().isLoaded("click.wav"))
            engine.getAudio().newSound("click.wav", false);
        if(!engine.getAudio().isLoaded("ambiente.wav")){
            engine.getAudio().newSoundAmbient("ambiente.wav");
            engine.getAudio().playSound("ambiente");
        }
        this.font = gr.newFont("coolvetica.otf", 20, false);
       gr.setFont(this.font);

        this.logo = gr.newImage("logo.png");
        this.bPlay = new ButtonPlay("jugar.png",engine, (gr.getWidthLogic()/2),(gr.getHeightLogic()/10)*6,200,75);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(GraphicsA graphics) {
        this.bPlay.render(graphics);
        graphics.drawImage(this.logo,(graphics.getWidthLogic()/2),graphics.getHeightLogic()/6, 365, 67);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bPlay.handleEvent(event);
        }

        input.clearEvents();
    }
}