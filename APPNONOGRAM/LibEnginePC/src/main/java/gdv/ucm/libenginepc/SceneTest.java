package gdv.ucm.libenginepc;

import java.awt.Color;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IState;
import gdv.ucm.liblogica.Board;

public class SceneTest implements IState {
    private EnginePC engine;
    private Board board;
    public SceneTest(EnginePC engine){
        this.engine = engine;
        //this.circulito = new Circulito(1920/3,1080/3,50,150,engine.getGraphics().getWidth(), Color.blue);
        this.board = new Board(9,9);

    }
    @Override
    public void update(double deltaTime) {
        //this.circulito.update(deltaTime);
    }

    @Override
    public void render(IEngine engine) {
        //this.circulito.render(engine.getGraphics());
        this.board.render(engine.getGraphics());
    }
}
