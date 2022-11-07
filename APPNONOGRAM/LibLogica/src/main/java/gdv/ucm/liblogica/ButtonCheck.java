package gdv.ucm.liblogica;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class ButtonCheck implements IInterface {
    private final IGraphics gr;
    private IImage img;
    private IEngine engine;
    private int x;
    private int y;
    private int w;
    private int h;
    private String s;
    private boolean check;
    private long timer;
    private int onScreen;

    private Hints hints;
    private IAudio audio;

    ButtonCheck(String filename, IEngine engine, Hints hints, int x, int y, int w, int h){
        this.engine = engine;
        this.gr = engine.getGraphics();
        this.img = this.engine.getGraphics().newImage(filename);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.hints = hints;
        this.check = false;
        this.onScreen = 3000;
        this.audio = this.engine.getAudio();
    }

    @Override
    public void render(IGraphics g) {
        g.drawImage(this.img,x,y,w,h);
        if(check)
            this.engine.getGraphics().drawText(s, 400, 100, 0xFF0000,null );
    }

    @Override
    public void update(Double deltaTime) {
        if(System.currentTimeMillis()>this.timer+this.onScreen) {
            check = false;
            this.hints.clearWrongs();
        }
    }

    @Override
    public void handleEvent(IInput.Event e) {
        int mX = e.x;
        int mY = e.y;
        if(e.type == IInput.InputTouchType.PRESSED && //click
                e.index == 1 &&                            // boton izq
                (mX >= this.gr.logicToRealX(x) - (w/2) && mX <= w + this.gr.logicToRealX(x) - (w/2) && mY >= this.gr.logicToRealY(y) && mY <= h + this.gr.logicToRealY(y))){ // dentro del cuadrado
            Pair aux = this.hints.check();
            this.s = "Te falta "+ aux.getFirst()+" casilla\nTienes mal "+ aux.getSecond()+" casillas";
            this.timer = System.currentTimeMillis();
            this.check = true;
            this.audio.playSound("click");
        }
    }
}
