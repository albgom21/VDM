package gdv.ucm.liblogica;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.IButton;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.ISound;
import gdv.ucm.libengine.IState;
import gdv.ucm.libengine.IInput;

public class MainScene implements IState {
    private IImage imagen;
    private IFont textoJugar;
    private IGraphics gr;
    private IInput input;
//    private IAudio audio;
//    private ISound sonidoClick;
    private Board board;
    private Hints hints;
//    private IButton botonPrueba;

    public MainScene(IEngine engine) {
        this.board = new Board(5,5);
        this.hints = new Hints(this.board);
        this.gr = engine.getGraphics();
        this.input = engine.getInput();
        this.textoJugar = this.gr.newFont("zero.ttf", 20 , false);
//        this.imagen = this.gr.newImage("perroTriste.jpg");
        this.gr.setFont(this.textoJugar);

//        this.audio = engine.getAudio();
        //this.audio.newSound("click.wav");
        //this.audio.playsound("click");
//        this.sonidoClick = this.audio.newSound("click.wav");
        //this.audio.playSound("click"); // 2 formas de reproducir un sonido
        //this.sonidoClick.play();

//        this.botonPrueba = this.gr.newButton();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        //this.gr.drawImage(this.imagen,100,100);
        //this.gr.drawText("JUGAR",500,500, 0x000000);
        this.board.render(this.gr);
        this.hints.render(this.gr);
    }

    @Override
    public void handleInputs() {
        for(int i = 0; i < this.input.getEvents().size(); i++)
            this.board.handleEvent(this.input.getEvents().get(i));
    }
}
