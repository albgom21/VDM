package gdv.ucm.libengine;

public interface IState {
//    IEngine engine = null;
    void update(double deltaTime);
    void render(IEngine engine);
}