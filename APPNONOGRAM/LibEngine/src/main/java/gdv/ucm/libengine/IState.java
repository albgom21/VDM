package gdv.ucm.libengine;

public interface IState {
    void update(double deltaTime);    // Actualizar elementos de la escena
    void render(IGraphics graphics);  // Renderizar elementos de la escena
    void handleInputs(IInput inputs); // Cada elemento de la escena maneja los eventos
}