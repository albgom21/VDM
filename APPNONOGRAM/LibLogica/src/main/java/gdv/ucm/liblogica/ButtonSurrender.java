package gdv.ucm.liblogica;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class ButtonSurrender implements IInterface {
    private final IAudio audio;
    private final IGraphics gr;
    private IImage img;
    private IEngine engine;
    private int x;
    private int y;
    private int w;
    private int h;

    ButtonSurrender(String filename, IEngine engine, int x, int y, int w, int h){
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
    public void update(Double deltaTime) {
    }

    @Override
    public void handleEvent(IInput.Event e) {
        int mX = e.x;
        int mY = e.y;
        if(e.type == IInput.InputTouchType.PRESSED && //click
           e.index == 1 &&                            // boton izq
           (mX >= this.gr.logicToRealX(x) - (w/2) && mX <= w + this.gr.logicToRealX(x) - (w/2) && mY >= this.gr.logicToRealY(y) && mY <= h + this.gr.logicToRealY(y))){ // dentro del cuadrado

            this.audio.playSound("click");this.audio.playSound("click");
            SelectLvlScene scene = new SelectLvlScene(engine);
            engine.setCurrentScene(scene);
        }
    }
}
