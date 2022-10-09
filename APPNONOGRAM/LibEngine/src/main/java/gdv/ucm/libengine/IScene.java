package gdv.ucm.libengine;

public interface IScene {
    IEngine engine = null;
    void update(double deltaTime);
    void render(IEngine engine);
}
