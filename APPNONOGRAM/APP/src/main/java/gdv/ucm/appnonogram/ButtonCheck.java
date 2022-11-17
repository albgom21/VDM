package gdv.ucm.appnonogram;


import com.example.libenginea.AudioA;
import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InterfaceA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;

public class ButtonCheck implements InterfaceA {
    private final GraphicsA gr;
    private ImageA img;
    private EngineA engine;
    private int x;
    private int y;
    private int w;
    private int h;
    private String s1;
    private String s2;
    private boolean check;
    private long timer;
    private int onScreen;

    private Hints hints;
    private AudioA audio;

    ButtonCheck(String filename, EngineA engine, Hints hints, int x, int y, int w, int h){
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
    public void render(GraphicsA g) {
        g.drawImage(this.img,x,y,w,h);
        if(check) {
            this.engine.getGraphics().drawText(s1, this.gr.logicToRealX(this.gr.getWidthLogic() / 2), (this.gr.logicToRealY((this.gr.getHeightLogic() / 11)*10)), 0xac3232, null, this.gr.scaleToReal(15));
            this.engine.getGraphics().drawText(s2, this.gr.logicToRealX(this.gr.getWidthLogic() / 2), (this.gr.logicToRealY((int)((this.gr.getHeightLogic() / 11)*10.5))), 0xac3232, null, this.gr.scaleToReal(15));
        }
    }

    @Override
    public void update(Double deltaTime) {
        if(System.currentTimeMillis()>this.timer+this.onScreen) {
            check = false;
            this.hints.clearWrongs();
        }
    }

    @Override
    public boolean handleEvent(InputA.Event e) {
        int mX = e.x;
        int mY = e.y;
        if(e.type == InputA.InputTouchType.PRESSED && //click
          e.index == 1 &&                            // boton izq
          (mX >= this.gr.logicToRealX(x) - (this.gr.scaleToReal(w)/2) && mX <= this.gr.scaleToReal(w) + this.gr.logicToRealX(x) - (this.gr.scaleToReal(w)/2)
          && mY >= this.gr.logicToRealY(y) - (this.gr.scaleToReal(h)/2) && mY <= this.gr.scaleToReal(h) + this.gr.logicToRealY(y) - (this.gr.scaleToReal(h)/2))){ // dentro del cuadrado
            Pair aux = this.hints.check();
            this.s1 = "Te faltan "+ aux.getFirst()+" casillas";
            this.s2 = "Tienes mal "+ aux.getSecond()+" casillas";
            this.timer = System.currentTimeMillis();
            this.check = true;
            this.audio.playSound("check");
            return true;
        }
        return false;
    }
}