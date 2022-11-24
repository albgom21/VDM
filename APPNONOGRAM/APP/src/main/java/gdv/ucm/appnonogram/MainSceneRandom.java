package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
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


    public MainSceneRandom(EngineA engine, int cols, int fils) {
        this.engine = engine;
        GraphicsA gr = this.engine.getGraphics();
        this.board = new Board(cols, fils);
        this.hints = new Hints(this.board);
        this.renderHints = new RenderHints(this.hints);
        this.renderBoard = new RenderBoard(this.board);

        if(!engine.getAudio().isLoaded("cell.wav"))
            engine.getAudio().newSound("cell.wav", false);
        if(!engine.getAudio().isLoaded("check.wav"))
            engine.getAudio().newSound("check.wav", false);
        if(!engine.getAudio().isLoaded("win.wav"))
            engine.getAudio().newSound("win.wav", false);
        this.bCheck = new ButtonCheck("comprobar.png", this.engine, this.hints, (gr.getWidthLogic()/5)*4,(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bSurrender = new ButtonSurrender("rendirse.png", this.engine, (gr.getWidthLogic()/5),(gr.getHeightLogic()/10)*9,200/2,75/2);
    }

    @Override
    public void update(double deltaTime) {
        this.bCheck.update(deltaTime);
        if(this.hints.getEnd()) {
            this.engine.getAudio().playSound("win");
            WinScene scene = new WinScene(this.engine, this.board);
            this.engine.setCurrentScene(scene);
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        this.board.render(graphics);
        this.renderBoard.render(graphics);
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