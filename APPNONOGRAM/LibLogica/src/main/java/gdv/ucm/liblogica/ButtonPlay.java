package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class ButtonPlay implements IInterface {
    private IImage img;
    private IEngine engine;
    private int x;
    private int y;
    private int w;
    private int h;

    ButtonPlay(String filename, IEngine engine, int x, int y, int w, int h){
        this.engine = engine;
        this.img = this.engine.getGraphics().newImage(filename);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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
        System.out.println("CLICKKKKKKKKKK");
        if(e.type == IInput.InputTouchType.PRESSED && //click
           e.index == 1 &&                            // boton izq
           (mX >= x && mX <= w + x && mY >= y && mY <= h + y)){ // dentro del cuadrado
            SelectLvlScene scene = new SelectLvlScene(engine);
            engine.setCurrentScene(scene);
        }
    }
}
