package gdv.ucm.appnonogram.SCENES;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.FontA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

import gdv.ucm.appnonogram.BUTTONS.ButtonPaleta;
import gdv.ucm.appnonogram.BUTTONS.ButtonPlay;

public class TitleScene implements StateA {
    private FontA font;
    private ButtonPlay bPlay;
    private ButtonPaleta bPaleta;
    private ImageA logo;

    private EngineA engine;
    private GraphicsA gr;

    public TitleScene(EngineA engine) {
        this.engine = engine;
        this.engine.setSaveBoard(false);
        this.gr = engine.getGraphics();
        //CARGA DE RECURSOS
        engine.getAudio().newSound("click.wav", false);
        engine.getAudio().newSound("back.wav", false);
        engine.getAudio().newSoundAmbient("ambiente.wav");
        engine.getAudio().playSound("ambiente");
        this.font = gr.newFont("coolvetica.otf", 20, false);
        gr.setFont(this.font);

        this.logo = gr.newImage("logo.png");
        this.bPlay = new ButtonPlay("jugar.png",engine, (gr.getWidthLogic()/2),(gr.getHeightLogic()/5)*2,200,75);
        this.bPaleta = new ButtonPaleta("paletas.png",engine, (gr.getWidthLogic()/2),(int)((gr.getHeightLogic()/5)*3.5),200,75);
    }

    @Override
    public void update(double deltaTime) {
        if(this.engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            this.bPaleta.setPos((this.gr.getWidthLogic()/2),(int)((this.gr.getHeightLogic()/5)*3.5));
            this.bPlay.setPos((this.gr.getWidthLogic()/2),(this.gr.getHeightLogic()/5)*2);
        }
        else
        {
            this.bPaleta.setPos((this.gr.getWidthLogic()/2),(int)((this.gr.getHeightLogic()/5)*3.5));
            this.bPlay.setPos((this.gr.getWidthLogic()/2),(this.gr.getHeightLogic()/5)*2);
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        this.bPlay.render(graphics);
        this.bPaleta.render(graphics);
        graphics.drawImage(this.logo,(graphics.getWidthLogic()/2),graphics.getHeightLogic()/8, 365, 67);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            this.bPlay.handleEvent(event);
            this.bPaleta.handleEvent(event);
        }

        input.clearEvents();
    }
}