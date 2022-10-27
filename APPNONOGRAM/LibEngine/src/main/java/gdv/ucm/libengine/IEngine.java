package gdv.ucm.libengine;

public interface IEngine {
      IGraphics getGraphics();
      IAudio getAudio();
      IState getState();
      IInput getInput();

      void setCurrentScene(IState currentScene);
}

