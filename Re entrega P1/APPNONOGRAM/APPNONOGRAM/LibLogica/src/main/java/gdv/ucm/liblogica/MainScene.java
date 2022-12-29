package gdv.ucm.liblogica;

import java.util.ArrayList;
import java.util.Iterator;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IState;
import gdv.ucm.libengine.IInput;

public class MainScene implements IState {
    private IEngine engine;
    private Board board;
    private Hints hints;
    private RenderBoard renderBoard;
    private RenderHints renderHints;
    private ButtonCheck bCheck;
    private ButtonSurrender bSurrender;


    public MainScene(IEngine engine, int cols, int fils) {
        this.engine = engine;
        IGraphics gr = this.engine.getGraphics();
        this.board = new Board(cols, fils);
        this.hints = new Hints(this.board);
        this.renderHints = new RenderHints(this.hints);
        this.renderBoard = new RenderBoard(this.board);

        engine.getAudio().newSound("cell.wav", false);
        engine.getAudio().newSound("check.wav", false);
        engine.getAudio().newSound("win.wav", false);
        this.bCheck = new ButtonCheck("comprobar.png", this.engine, this.hints, (gr.getWidthLogic()/5)*4,(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bSurrender = new ButtonSurrender("rendirse.png", this.engine, (gr.getWidthLogic()/5),(gr.getHeightLogic()/10)*9,200/2,75/2);
    }

    @Override
    public void update(double deltaTime) {
        this.bCheck.update(deltaTime);
        this.hints.update(deltaTime);
        if(this.hints.getEnd()) {
            this.engine.getAudio().playSound("win");
            WinScene scene = new WinScene(this.engine, this.board);
            this.engine.setCurrentScene(scene);
        }
    }

    @Override
    public void render(IGraphics graphics) {
        this.board.render(graphics);
        this.renderBoard.render(graphics);
        this.renderHints.render(graphics);
        this.bCheck.render(graphics);
        this.bSurrender.render(graphics);
    }

    @Override
    public void handleInputs(IInput input) {
        ArrayList<IInput.Event> eventList =  new ArrayList<>();
        eventList.addAll(input.getEvents());

        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            if(this.board.handleEvent(event))
                this.engine.getAudio().playSound("cell");
            this.bCheck.handleEvent(event);
            this.bSurrender.handleEvent(event);
        }
        input.clearEvents();
    }
}