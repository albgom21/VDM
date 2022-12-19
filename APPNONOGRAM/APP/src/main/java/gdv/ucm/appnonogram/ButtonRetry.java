package gdv.ucm.appnonogram;

import com.example.libenginea.AudioA;
import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.InterfaceA;

public class ButtonRetry implements InterfaceA {
    private final GraphicsA gr;
    private ImageA img;
    private EngineA engine;
    private int x;
    private int y;
    private int w;
    private int h;
    private AudioA audio;
    private String type;
    private int lvl;
    private int cols,fils;

    ButtonRetry(String filename, EngineA engine, int x, int y, int w, int h, int cols, int fils, int lvl, String type){
        this.engine = engine;
        this.lvl = lvl;
        this.type = type;
        this.cols = cols;
        this.fils = fils;
        this.gr = engine.getGraphics();
        this.img = this.engine.getGraphics().newImage(filename);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.audio = this.engine.getAudio();
    }

    public void setPos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(GraphicsA g) {
        g.drawImage(this.img,x,y,w,h);
    }

    @Override
    public void update(Double deltaTime) { }

    @Override
    public boolean handleEvent(InputA.Event e) {
        int mX = e.x;
        int mY = e.y;
        if(e.type == InputA.InputTouchType.NORMAL_PRESSED && //click
                e.index == 1 &&                            // boton izq
                (mX >= this.gr.logicToRealX(x) - (this.gr.scaleToReal(w)/2) && mX <= this.gr.scaleToReal(w) + this.gr.logicToRealX(x) - (this.gr.scaleToReal(w)/2)
                        && mY >= this.gr.logicToRealY(y) - (this.gr.scaleToReal(h)/2) && mY <= this.gr.scaleToReal(h) + this.gr.logicToRealY(y) - (this.gr.scaleToReal(h)/2))){ // dentro del cuadrado
            this.audio.playSound("back");
            if(this.cols != 0)
            {
                MainSceneRandom scene = new MainSceneRandom(this.engine, this.cols, this.fils);
                engine.setCurrentScene(scene);
            }
            else {
                MainSceneRead scene = new MainSceneRead(this.engine,"Lvl" + lvl + type + ".txt", type, lvl);
                engine.setCurrentScene(scene);
            }

            return true;
        }
        return false;
    }
}
