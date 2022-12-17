package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.ReadA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;


public class MainSceneRead implements StateA {
    private EngineA engine;
    private Board board;
    private Hints hints;
    private RenderBoard renderBoard;
    private RenderHints renderHints;
    private ButtonReward bReward;
    private ButtonCheck bCheck;
    private ButtonSurrender bSurrender;
    private String filename;
    private String type;
    private int lvl;
    private ImageA coins;
    private ImageA bRewardLock;

    public MainSceneRead(EngineA engine, String filename, String type, int lvl) {
        this.type = type;
        this.lvl = lvl;
        this.engine = engine;
        this.filename = filename;
        GraphicsA gr = this.engine.getGraphics();
        ReadA rd = this.engine.getRead();
        this.board = new Board(filename, rd, this.engine);
        this.hints = new Hints(this.board);
        this.renderHints = new RenderHints(this.hints);
        this.renderBoard = new RenderBoard(this.board);

        engine.getAudio().newSound("cell.wav", false);
        engine.getAudio().newSound("check.wav", false);
        engine.getAudio().newSound("win.wav", false);
        engine.getAudio().newSound("lose.wav", false);
        engine.getAudio().newSound("wrong.wav", false);

        this.bCheck = new ButtonCheck("comprobar.png", this.engine, this.hints, (gr.getWidthLogic()/5)*4,(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bSurrender = new ButtonSurrender("rendirse.png", this.engine, (gr.getWidthLogic()/5),(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bReward = new ButtonReward("videoVida.png", this.engine, (gr.getWidthLogic()/5),gr.getHeightLogic()/15,200/2,75/2);
        this.coins = gr.newImage("moneda.png");
        this.bRewardLock = gr.newImage("videoVidaLock.png");
    }

    @Override
    public void update(double deltaTime) {
        this.bCheck.update(deltaTime);
        this.hints.update(deltaTime);
        if(this.engine.getRewardObtain()){ //Si hemos visto el anuncio 1 vida mas
            this.board.addLife();
            this.engine.useRewardObtain();
        }
        if(this.board.getLives() <= 0) //Perdimos
        {
            LoseScene scene = new LoseScene(this.engine,0,0, this.lvl, this.type);
            this.engine.setCurrentScene(scene);
        }
        if(this.hints.getEnd()) {
            if(this.type == "a")
                this.engine.getStats().setBosqueDesbloqueado(this.lvl);
            else if(this.type == "b")
                this.engine.getStats().setEmojiDesbloqueado(this.lvl);
            else if(this.type == "c")
                this.engine.getStats().setComidaDesbloqueado(this.lvl);
            else if(this.type == "d")
                this.engine.getStats().setNavidadDesbloqueado(this.lvl);
            this.engine.getAudio().playSound("win");
            WinScene scene = new WinScene(this.engine, this.board,false,this.lvl+1,this.type,(this.board.getHeight()+this.board.getWidth())/2);
            this.engine.setCurrentScene(scene);
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        graphics.drawImage(this.coins,(graphics.getWidthLogic()/5)*4,graphics.getHeightLogic()/15, 20, 20);
        graphics.drawText(Integer.toString(engine.getStats().getMonedas()),graphics.logicToRealX(((graphics.getWidthLogic()/5)*4)-35),graphics.logicToRealY(graphics.getHeightLogic()/11), 0x442700,null, graphics.scaleToReal(20));
        this.board.render(graphics);
        this.renderBoard.render(graphics, engine);
        this.renderBoard.renderLifes(graphics);
        this.renderHints.render(graphics);
        this.bCheck.render(graphics);
        this.bSurrender.render(graphics);
        if(this.board.getLives() < 3) // Si hay menos de 3 vidas se renderiza el boton
            this.bReward.render(graphics);
        else
            graphics.drawImage(this.bRewardLock,(graphics.getWidthLogic()/5),graphics.getHeightLogic()/15,200/2,75/2);
    }

    @Override
    public void handleInputs(InputA input) {
        ArrayList<InputA.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<InputA.Event> it = eventList.iterator();
        while (it.hasNext()) {
            InputA.Event event = it.next();
            if(this.board.handleEvent(event))
                this.engine.getAudio().playSound("cell");
            this.bCheck.handleEvent(event);
            this.bSurrender.handleEvent(event);
            if(this.board.getLives() < 3) // Si hay menos de 3 vidas puedes ver el video
                this.bReward.handleEvent(event);
        }
        input.clearEvents();
    }
}