package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IState;
import gdv.ucm.libengine.IInput;


public class MainScene implements IState {
    private IImage imagen;
    private IFont textoJugar;
    private IGraphics gr;
    private IInput input;
    private IEngine engine;
//    private IAudio audio;
//    private ISound sonidoClick;
    private Board board;
    public Hints hints;
    private ButtonCheck bCheck;
    private ButtonSurrender bSurrender;


    public MainScene(IEngine engine, int cols, int fils) {
        this.engine = engine;
        this.gr = this.engine.getGraphics();
        this.input = this.engine.getInput();
        this.board = new Board(cols, fils, this.gr);
        this.hints = new Hints(this.board, this.gr);
        //this.bCheck = this.gr.newButton("perroTriste.jpg",(this.gr.getWidthLogic()/2) - 25,this.gr.getHeightLogic()/2,200,200);
        this.textoJugar = this.gr.newFont("coolvetica.otf", 20 , false);
        //this.imagen = this.gr.newImage("perroTriste.jpg");
        this.gr.setFont(this.textoJugar);
        this.bCheck = new ButtonCheck("comprobar.png", this.engine, this.hints, (this.gr.getWidth()/3)*2 - 100,this.gr.getBorderTop(),200/2,75/2);
        this.bSurrender = new ButtonSurrender("rendirse.png", this.engine, (this.gr.getWidth()/3),this.gr.getBorderTop(),200/2,75/2);

        //        this.audio = engine.getAudio();
        //this.audio.newSound("click.wav");
        //this.audio.playsound("click");
//        this.sonidoClick = this.audio.newSound("click.wav");
        //this.audio.playSound("click"); // 2 formas de reproducir un sonido
        //this.sonidoClick.play();
    }

    @Override
    public void update(double deltaTime) {
        this.bCheck.update(deltaTime);
        this.hints.update(deltaTime);
        if(this.hints.getEnd()) {
            WinScene scene = new WinScene(this.engine, this.board);
            this.engine.setCurrentScene(scene);
        }
    }

    @Override
    public void render() {
        //this.gr.drawImage(this.imagen,100,100, 500, 500);
        //this.gr.drawText("JUGAR",500,500, 0x000000);
        this.board.render(this.gr);
        this.hints.render(this.gr);
        this.bCheck.render(this.gr);
        this.bSurrender.render(this.gr);

        //Pair aux = this.hints.check();
        //String s = "Te falta "+ aux.getFirst()+" casilla Tienes mal "+ aux.getSecond()+" casillas";
        //this.gr.drawText(s,600,600,0xFF0000);
    }

    @Override
    public void handleInputs() {
        for(int i = 0; i < this.input.getEvents().size(); i++){
            this.board.handleEvent(this.input.getEvents().get(i));
            this.bCheck.handleEvent(this.input.getEvents().get(i));
            this.bSurrender.handleEvent(this.input.getEvents().get(i));
        }
    }
}
