package gdv.ucm.liblogica;

import java.util.ArrayList;
import java.util.Iterator;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class TitleScene implements IState {
    private IFont font;
    private ButtonPlay bPlay;
    private IImage logo;

    public TitleScene(IEngine engine) {
        IGraphics gr = engine.getGraphics();
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
    public void render(IGraphics graphics) {
        this.bPlay.render(graphics);
        graphics.drawImage(this.logo,(graphics.getWidthLogic()/2),graphics.getHeightLogic()/6, 365, 67);
    }

    @Override
    public void handleInputs(IInput input) {
        ArrayList<IInput.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            this.bPlay.handleEvent(event);
        }

        input.clearEvents();
    }
}