package gdv.ucm.libenginepc;

import java.awt.Color;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IState;
import gdv.ucm.liblogica.Board;

public class SceneTest implements IState {
    private IGraphics gr;

    private EnginePC engine;
    private Board board;
    public SceneTest(EnginePC engine){
        this.engine = engine;
        this.board = new Board(9,9);
        this.gr = engine.getGraphics();

    }
    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        this.board.render(this.gr);
    }

    @Override
    public void handleInputs() {

    }
}
