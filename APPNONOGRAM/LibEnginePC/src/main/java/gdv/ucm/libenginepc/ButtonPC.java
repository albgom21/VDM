package gdv.ucm.libenginepc;

import gdv.ucm.libengine.IButton;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IInput;
import gdv.ucm.liblogica.Hints;
import gdv.ucm.liblogica.MainScene;
import gdv.ucm.liblogica.Pair;
import gdv.ucm.liblogica.SelectLvlScene;
import gdv.ucm.liblogica.TitleScene;

public class ButtonPC implements IButton {
    private ImagePC img;
    private EnginePC engine;
    private int x;
    private int y;
    private int w;
    private int h;

    private Hints hints;

    ButtonPC(ImagePC img, EnginePC engine, int x, int y, int w, int h){
        this.img = img;
        this.engine = engine;
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
        if(e.type == IInput.InputTouchType.PRESSED && //click
           e.index == 1 &&                            // boton izq
          (mX >= x && mX <= w + x && mY >= y && mY <= h + y)){ // dentro del cuadrado
//            Pair aux = this.hints.check();
//            String s = "Te falta "+ aux.getFirst()+" casilla\nTienes mal "+ aux.getSecond()+" casillas";
//            this.engine.getGraphics().drawText(s,400,100,0x000000);
//            SelectLvlScene scene = new SelectLvlScene(engine);
//            engine.setCurrentScene(scene);
        }
    }
}
