package gdv.ucm.liblogica;

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

        if(!engine.getAudio().isLoaded("cell.wav"))
            engine.getAudio().newSound("cell.wav", false);
        if(!engine.getAudio().isLoaded("check.wav"))
            engine.getAudio().newSound("check.wav", false);

        this.bCheck = new ButtonCheck("comprobar.png", this.engine, this.hints, (gr.getWidthLogic()/5)*4,(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bSurrender = new ButtonSurrender("rendirse.png", this.engine, (gr.getWidthLogic()/5),(gr.getHeightLogic()/10)*9,200/2,75/2);
    }

    @Override
    public void update(double deltaTime) {
        this.bCheck.update(deltaTime);
        this.hints.update(deltaTime);
        if(this.hints.getEnd()) {
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
    public void handleInputs(IInput input) { //PONER INPUT PARAMETRO
        for(int i = 0; i < input.getEvents().size(); i++){
            if(this.board.handleEvent(input.getEvents().get(i)))
                this.engine.getAudio().playSound("cell");
            this.bCheck.handleEvent(input.getEvents().get(i));
            this.bSurrender.handleEvent(input.getEvents().get(i));
        }
    }
}