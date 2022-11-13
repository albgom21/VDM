package gdv.ucm.liblogica;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class ButtonPlay implements IInterface {
    private final IAudio audio;
    private IImage img;
    private IEngine engine;
    private IGraphics gr;
    private int x;
    private int y;
    private int w;
    private int h;

    ButtonPlay(String filename, IEngine engine, int x, int y, int w, int h ){
        this.engine = engine;
        this.gr = engine.getGraphics();
        this.img = this.engine.getGraphics().newImage(filename);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.audio = this.engine.getAudio();
    }

    @Override
    public void render(IGraphics g) {
        g.drawImage(this.img,x,y,w,h);
    }

    @Override
    public void update(Double deltaTime) { }

    @Override
    public boolean handleEvent(IInput.Event e) {
        int mX = e.x;
        int mY = e.y;
        if(e.type == IInput.InputTouchType.PRESSED && //click
           e.index == 1 &&                            // boton izq
           (mX >= this.gr.logicToRealX(x) - (this.gr.scaleToReal(w)/2) && mX <= this.gr.scaleToReal(w) + this.gr.logicToRealX(x) - (this.gr.scaleToReal(w)/2)
           && mY >= this.gr.logicToRealY(y) - (this.gr.scaleToReal(h)/2) && mY <= this.gr.scaleToReal(h) + this.gr.logicToRealY(y) - (this.gr.scaleToReal(h)/2))){ // dentro del cuadrado
            this.audio.playSound("click");
            SelectGamemodeScene scene = new SelectGamemodeScene(engine);
            engine.setCurrentScene(scene);
            return true;
        }
        return false;
    }
}