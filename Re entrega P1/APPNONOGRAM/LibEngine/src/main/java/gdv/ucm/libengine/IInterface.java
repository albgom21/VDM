package gdv.ucm.libengine;

public interface IInterface {
    void render (IGraphics g);              // Renderizar elemento de interfaz
    void update (Double deltaTime);         // Actualizar elemento de interfaz
    boolean handleEvent (IInput.Event e);   // Manejar evento
}