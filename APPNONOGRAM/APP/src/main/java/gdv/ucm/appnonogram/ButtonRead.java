package gdv.ucm.appnonogram;

import com.example.libenginea.AudioA;
import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.InterfaceA;

public class ButtonRead implements InterfaceA {
    private final AudioA audio;
    private ImageA img;
    private EngineA engine;
    private GraphicsA gr;
    private String type;
    private int lvl;
    private boolean bloqueado;
    private int x;
    private int y;
    private int w;
    private int h;

    ButtonRead(String filename, EngineA engine, int x, int y, int w, int h,int lvl, String type, boolean bloqueado){
        this.engine = engine;
        this.gr = engine.getGraphics();
        this.img = this.engine.getGraphics().newImage(filename);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.type = type;
        this.lvl = lvl;
        this.audio = this.engine.getAudio();
        this.bloqueado = bloqueado;
        this.engine.getAudio().newSound("click.wav", false);
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
            this.audio.playSound("click");
            if(!bloqueado){
                MainSceneRead scene = new MainSceneRead(engine, "Lvl"+Integer.toString(lvl)+type+".txt", type, lvl);
                engine.setCurrentScene(scene);
            }
            return true;
        }
        return false;
    }
}
