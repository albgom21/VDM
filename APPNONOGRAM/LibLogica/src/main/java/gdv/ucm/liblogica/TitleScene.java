package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class TitleScene implements IState {
    private IFont font;
    private IGraphics gr;
    private ButtonPlay bPlay;
    private IInput input;
    private IImage logo;

    public TitleScene(IEngine engine) {
        this.gr = engine.getGraphics();
        this.input = engine.getInput();
        //CARGA DE RECURSOS
        if(!engine.getAudio().isLoaded("click.wav"))
            engine.getAudio().newSound("click.wav", false);
        if(!engine.getAudio().isLoaded("ambiente.wav")){
            engine.getAudio().newSoundAmbient("ambiente.wav");
            engine.getAudio().playSound("ambiente");
        }
        this.font = this.gr.newFont("coolvetica.otf", 25, false);
        this.gr.setFont(this.font);

        this.logo = this.gr.newImage("logo.png");

        this.bPlay = new ButtonPlay("jugar.png",engine, (this.gr.getWidthLogic()/2),(this.gr.getHeightLogic()/10)*6,200,75);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.bPlay.render(this.gr);
        this.gr.drawImage(this.logo,(this.gr.getWidthLogic()/2),this.gr.getHeightLogic()/10, 365, 67);
    }

    @Override
    public void handleInputs() {
        for(int i = 0; i < this.input.getEvents().size(); i++)
            this.bPlay.handleEvent(this.input.getEvents().get(i));
    }
}


