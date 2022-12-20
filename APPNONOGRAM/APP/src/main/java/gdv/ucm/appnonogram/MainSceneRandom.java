package gdv.ucm.appnonogram;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.FontA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.ImageA;
import com.example.libenginea.InputA;
import com.example.libenginea.StateA;

import java.util.ArrayList;
import java.util.Iterator;

public class MainSceneRandom implements StateA {
    private EngineA engine;

    private FontA font;
    private Board board;
    private Hints hints;
    private RenderBoard renderBoard;
    private RenderHints renderHints;
    private ButtonCheck bCheck;
    private ButtonSurrender bSurrender;
    private ButtonReward bReward;
    private int cols, fils;
    private ImageA coins;
    private ImageA bRewardLock;

    private GraphicsA gr;
    private int lives;

    public MainSceneRandom(EngineA engine, Board b) {
        this.engine = engine;
        this.engine.setSaveBoard(true);
        this.engine.setRandomBoard(true);

        this.gr = this.engine.getGraphics();
        this.cols = b.getWidth();
        this.fils = b.getHeight();
        this.board = new Board(b);
        this.lives = this.board.getLives();
        this.hints = new Hints(this.board);
        this.renderHints = new RenderHints(this.hints);
        this.renderBoard = new RenderBoard(this.board, this.gr);

        engine.getAudio().newSound("cell.wav", false);
        engine.getAudio().newSound("win.wav", false);
        engine.getAudio().newSound("lose.wav", false);
        engine.getAudio().newSound("wrong.wav", false);
        engine.getAudio().newSoundAmbient("ambiente.wav");
        engine.getAudio().playSound("ambiente");
        this.font = gr.newFont("coolvetica.otf", 20, false);
        gr.setFont(this.font);

        this.bCheck = new ButtonCheck("comprobar.png", this.engine, this.hints, (gr.getWidthLogic()/5)*4,(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bSurrender = new ButtonSurrender("rendirse.png", this.engine, (gr.getWidthLogic()/5),(gr.getHeightLogic()/10)*9,200/2,75/2);
        this.bReward = new ButtonReward("videoVida.png", this.engine, (gr.getWidthLogic()/5),gr.getHeightLogic()/15,200/2,75/2);
        this.coins = gr.newImage("moneda.png");
        this.bRewardLock = gr.newImage("videoVidaLock.png");
    }

    public MainSceneRandom(EngineA engine, int cols, int fils) {
        this.engine = engine;
        this.engine.setSaveBoard(true);
        this.engine.setRandomBoard(true);

        this.cols = cols;
        this.fils = fils;
        this.gr = this.engine.getGraphics();
        this.board = new Board(cols, fils);
        this.lives = this.board.getLives();
        this.hints = new Hints(this.board);
        this.renderHints = new RenderHints(this.hints);
        this.renderBoard = new RenderBoard(this.board, this.gr);

        engine.getAudio().newSound("cell.wav", false);
        engine.getAudio().newSound("check.wav", false);
        engine.getAudio().newSound("win.wav", false);
        engine.getAudio().newSound("lose.wav", false);
        engine.getAudio().newSound("wrong.wav", false);

        this.font = gr.newFont("coolvetica.otf", 20, false);
        gr.setFont(this.font);

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
            LoseScene scene = new LoseScene(this.engine,this.cols,this.fils,0,"");
            this.engine.setCurrentScene(scene);
        }
        if(this.hints.getEnd()) {
            this.engine.getAudio().playSound("win");
            WinScene scene = new WinScene(this.engine, this.board,true,0,"", (this.fils+this.cols)/2);
            this.engine.setCurrentScene(scene);
        }

        if(engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            this.bCheck.setPos(this.gr.getWidthLogic(),this.gr.getHeightLogic()-50);
            this.bSurrender.setPos(0,this.gr.getHeightLogic()-50);
            this.bReward.setPos(0,this.gr.getBorderTop());
        }
        else
        {
            this.bCheck.setPos((gr.getWidthLogic()/5)*4,(gr.getHeightLogic()/10)*9);
            this.bSurrender.setPos((gr.getWidthLogic()/5),(gr.getHeightLogic()/10)*9);
            this.bReward.setPos((gr.getWidthLogic()/5),gr.getHeightLogic()/15);
        }
    }

    @Override
    public void render(GraphicsA graphics) {
        if(engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            graphics.drawText(Integer.toString(engine.getStats().getMonedas()), graphics.logicToRealX(graphics.getWidthLogic() - 30), graphics.logicToRealY((int) (63)), 0x442700, null, graphics.scaleToReal(20));
            graphics.drawImage(this.coins, graphics.getWidthLogic(), 50, 20, 20);
        }
        else {
            graphics.drawText(Integer.toString(engine.getStats().getMonedas()), graphics.logicToRealX(((graphics.getWidthLogic() / 5) * 4) - 35), graphics.logicToRealY(graphics.getHeightLogic() / 11), 0x442700, null, graphics.scaleToReal(20));
            graphics.drawImage(this.coins, (graphics.getWidthLogic() / 5) * 4, graphics.getHeightLogic() / 15, 20, 20);
        }
        this.board.render(graphics);
        this.renderBoard.render(graphics,engine);
        this.renderBoard.renderLifes(graphics, this.engine);
        this.renderHints.render(graphics, this.engine);
        this.bCheck.render(graphics);
        this.bSurrender.render(graphics);
        if(this.board.getLives() < 3) // Si hay menos de 3 vidas se renderiza el boton
            this.bReward.render(graphics);
        else {
            if(engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                graphics.drawImage(this.bRewardLock, 0, this.gr.getBorderTop(), 200 / 2, 75 / 2);
            else
                graphics.drawImage(this.bRewardLock, (graphics.getWidthLogic() / 5), graphics.getHeightLogic() / 15, 200 / 2, 75 / 2);
        }
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
            if(this.board.getLives() != this.lives){
                this.engine.getAudio().playSound("wrong");
                this.lives = this.board.getLives();
            }
            if(this.board.getLives() < 3) // Si hay menos de 3 vidas puedes ver el video
                this.bReward.handleEvent(event);
        }
        input.clearEvents();
    }

    public Board getBoard() {
        return board;
    }
}