package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IState;

public class MainScene implements IState {
    private IImage imagen;
    private IFont textoJugar;
    private IGraphics gr;
    private int xImage;
    private Board board;

    public MainScene(IEngine engine){
        this.board = new Board(9,9);
        this.gr = engine.getGraphics();
        this.textoJugar = engine.getGraphics().newFont("zero.ttf", 200 , false);
        this.imagen = engine.getGraphics().newImage("perroTriste.jpg");
        this.gr.setFont(this.textoJugar);
    }

    @Override
    public void update(double deltaTime) {
        //this.xImage *= 1*deltaTime;
    }

    @Override
    public void render() {
        this.gr.drawImage(this.imagen,100,100);
        this.gr.drawText("JUGAR",500,500, 0x000000);
        //this.board.render(this.gr);
    }

    @Override
    public void handleInputs(/*List<Events> eventos*/) {
        // X, Y
        // if(evento)
        // this.tabler.action()
    }
}
