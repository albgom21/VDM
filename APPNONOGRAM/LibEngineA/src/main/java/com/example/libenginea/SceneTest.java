package com.example.libenginea;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IState;
import gdv.ucm.liblogica.Board;

public class SceneTest implements IState {
    private EngineA engine;
    private Board board;
    public SceneTest(EngineA engine){
        this.engine = engine;
        this.board = new Board(9,9);

    }
    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render(IEngine engine) {
        this.board.render(engine.getGraphics());
    }

    @Override
    public void handleInputs() {

    }
}
