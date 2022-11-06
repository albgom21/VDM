package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class ButtonCheck implements IInterface {
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

    ButtonCheck(String filename, IEngine engine, Hints hints, int x, int y, int w, int h){
        this.engine = engine;
        this.img = this.engine.getGraphics().newImage(filename);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.hints = hints;
        this.check = false;
        this.onScreen = 3000;
    }

    @Override
    public void render(IGraphics g) {
        g.drawImage(this.img,x,y,w,h);
        if(check)
            this.engine.getGraphics().drawText(s, 400, 100, 0xFF0000);
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
                (mX >= x && mX <= w + x && mY >= y && mY <= h + y)){ // dentro del cuadrado
            Pair aux = this.hints.check();
            this.s = "Te falta "+ aux.getFirst()+" casilla\nTienes mal "+ aux.getSecond()+" casillas";
//            this.engine.getGraphics().drawText(s,400,100,0xFF0000);
//            SelectLvlScene scene = new SelectLvlScene(engine);
//            engine.setCurrentScene(scene);
            this.timer = System.currentTimeMillis();
            this.check = true;
        }
    }
}
