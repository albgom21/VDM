package gdv.ucm.libengine;

public interface IInterface {
    void render (IGraphics g);
    void update (Double deltaTime);
    void handleEvent (IInput.Event e);
}
