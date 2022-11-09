package gdv.ucm.libengine;

public interface IState {
//    IEngine engine = null; poner en una clase y heredar
    void update(double deltaTime);
    void render();//IEngine engine;
    void handleInputs(IInput inputs);
}