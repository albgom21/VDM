package gdv.ucm.libengine;

public interface IEngine {
      IGraphics getGraphics(); // Obtener el módulo de los gráficos
      IAudio getAudio();       // Obtener el módulo del audio
      IState getState();       // Obtener la escena actual
      IInput getInput();       // Obtener el módulo del input

      void setCurrentScene(IState currentScene);
}