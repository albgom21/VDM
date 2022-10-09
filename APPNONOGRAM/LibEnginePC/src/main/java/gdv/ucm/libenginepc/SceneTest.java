package gdv.ucm.libenginepc;

import java.awt.Color;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IState;
import gdv.ucm.liblogica.Circulito;

public class SceneTest implements IState {
    private Circulito circulito;
    private Circulito circulote;
    private EnginePC engine;
    SceneTest(EnginePC engine){
        this.engine = engine;
        this.circulito = new Circulito(50,50,10,150,engine.getGraphics().getWidth());
        engine.getGraphics().setColor(Color.blue);

        this.circulote = new Circulito(50,50,50, 50, engine.getGraphics().getWidth());
    }
    @Override
    public void update(double deltaTime) {
        this.circulito.update(deltaTime);
        this.circulote.update(deltaTime);
    }

    @Override
    public void render(IEngine engine) {
        this.circulito.render(engine.getGraphics());
        this.circulote.render(engine.getGraphics());
    }
}
