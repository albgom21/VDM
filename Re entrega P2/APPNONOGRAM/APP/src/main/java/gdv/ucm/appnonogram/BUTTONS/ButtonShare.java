package gdv.ucm.appnonogram.BUTTONS;

import android.content.Intent;
import android.net.Uri;

import com.example.libenginea.AudioA;
import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InterfaceA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;

public class ButtonShare implements InterfaceA {
    private final AudioA audio;
    private ImageA img;
    private EngineA engine;
    private GraphicsA gr;
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean random;
    private int lvl;
    private String type;

    public ButtonShare(String filename, EngineA engine, int x, int y, int w, int h, boolean random, int lvl, String type){
        this.engine = engine;
        this.gr = engine.getGraphics();
        this.img = this.engine.getGraphics().newImage(filename);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.random = random;
        this.lvl = lvl;
        this.type = type;
        this.audio = this.engine.getAudio();
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
            // INTENT PARA TWEET
            String msg;
            if(random)
                msg = "¡Otro nivel rápido completado en APPNONOGRAM!";
            else{
                String categoria = null;
                if(type == "a")
                    categoria = "bosque";
                else if(type == "b")
                    categoria = "emoji";
                else if(type == "c")
                    categoria = "comida";
                else if(type == "d")
                    categoria = "navidad";

                msg = "¡Nivel " + Integer.toString(lvl) + " de la categoría " + categoria + " completado en APPNONOGRAM!";
            }

            Uri builtURI = Uri.parse("https://twitter.com/intent/tweet").buildUpon().appendQueryParameter( "text", msg).build() ; //Genera la URl
            Intent intent = new Intent(Intent.ACTION_VIEW, builtURI);

            this.engine.getContext().startActivity(intent);

            return true;
        }
        return false;
    }
}