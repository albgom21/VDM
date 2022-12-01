package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;



public class MainSceneRandom implements StateA {
    private EngineA engine;
    private Board board;
    private Hints hints;
    private RenderBoard renderBoard;
    private RenderHints renderHints;
    private ButtonCheck bCheck;
    private ButtonSurrender bSurrender;
    private int cols, fils;
    private ImageA coins;


    public MainSceneRandom(EngineA engine, int cols, int fils) {
        this.engine = engine;
        this.cols = cols;
        this.fils = fils;
        GraphicsA gr = this.engine.getGraphics();
        this.board = new Board(cols, fils, this.engine);
        this.hints = new Hints(this.board);
        this.renderHints = new RenderHints(this.hints);
        this.renderBoard = new RenderBoard(this.board);

        if(!engine.getAudio().isLoaded("cell.wav"))
            engine.getAudio().newSound("cell.wav", false);
        if(!engine.getAudio().isLoaded("check.wav"))
            engine.getAudio().newSound("check.wav", false);
        if(!engine.getAudio().isLoaded("win.wav"))
            engine.getAudio().newSound("win.wav", false);
        if(!engine.getAudio().isLoaded("wrong.wav"))
            engine.getAudio().newSound("wrong.wav", false);
        this.bCheck = new ButtonCheck("comprobar.png", this.engine, this.hints, (gr.getWidthLogic()/5)*4,(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bSurrender = new ButtonSurrender("rendirse.png", this.engine, (gr.getWidthLogic()/5),(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.coins = gr.newImage("moneda.png");

    }

    @Override
    public void update(double deltaTime) {
        this.bCheck.update(deltaTime);
        this.hints.update(deltaTime);
        if(this.board.getLifes() <= 0) //Perdimos
        {
            LoseScene scene = new LoseScene(this.engine,this.cols,this.fils,0,"");
            this.engine.setCurrentScene(scene);
        }
        if(this.hints.getEnd()) {
            this.engine.getAudio().playSound("win");
            WinScene scene = new WinScene(this.engine, this.board,true,0,"");
            this.engine.setCurrentScene(scene);
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        graphics.drawImage(this.coins,(graphics.getWidthLogic()/5)*4,graphics.getHeightLogic()/15, 30, 30);
        this.board.render(graphics);
        this.renderBoard.render(graphics,engine);
        this.renderBoard.renderLifes(graphics);
        this.renderHints.render(graphics);
        this.bCheck.render(graphics);
        this.bSurrender.render(graphics);
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
        }
        input.clearEvents();
    }
}