package gdv.ucm.appnonogram;


import com.example.libenginea.AudioA;
import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.InterfaceA;

public class ButtonColorPaleta implements InterfaceA {
    private final GraphicsA gr;
    private ImageA img;
    private EngineA engine;
    private String file;
    private String filename;
    private String filenameLock;
    private int x;
    private int y;
    private int w;
    private int h;
    private int color;
    private AudioA audio;
    private boolean desbloqueado;

    ButtonColorPaleta(String file, EngineA engine, int x, int y, int w, int h, int color, boolean desbloqueado){
        this.engine = engine;
        this.file = file;
        this.filename = this.file + ".png";
        this.filenameLock = this.file + "b.png";
        this.file = file;
        this.gr = engine.getGraphics();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        this.audio = this.engine.getAudio();
        this.engine.getAudio().newSound("click.wav", false);

        this.desbloqueado = desbloqueado;
        if(this.desbloqueado)
            this.img = this.engine.getGraphics().newImage(this.filename);
        else
            this.img = this.engine.getGraphics().newImage(this.filenameLock);
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
            if(!this.desbloqueado && this.engine.getStats().getMonedas() < 100){    //Si no tienes monedas para comprar
                this.audio.playSound("error");
            }
            else if(!this.desbloqueado && this.engine.getStats().getMonedas() >= 100){  //Si tienes monedas para comprar
                this.audio.playSound("click");
                this.engine.getStats().subMoneda(100);
                this.engine.getStats().setPaletaDesbloqueada(this.color);
                this.engine.getStats().setPaleta(this.color);
                this.img = this.engine.getGraphics().newImage(this.filename);
                this.desbloqueado = true;
            }
            else if(this.desbloqueado){ //Si ya se tiene la paleta, se selecciona
                this.audio.playSound("click");
                this.engine.getStats().setPaleta(this.color);
            }
            return true;
        }
        return false;
    }
}