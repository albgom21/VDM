package gdv.ucm.liblogica;

import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;
import gdv.ucm.libengine.IState;

public class Scene implements IState {
    private IImage imagen;
    private IGraphics gr;
    private int xImage;

    public Scene(IEngine engine){
        this.gr = engine.getGraphics();
        this.imagen = engine.getGraphics().newImage("/data/start.png");
    }
    @Override
    public void update(double deltaTime) {
        this.xImage *= 1*deltaTime;
    }

    @Override
    public void render() {
        this.gr.drawImage(this.imagen,0,0);
    }

    @Override
    public void handleInputs(/*List<Events> eventos*/) {
    //X, Y
        // if(evento)
        // this.tabler.action()
    }
}
