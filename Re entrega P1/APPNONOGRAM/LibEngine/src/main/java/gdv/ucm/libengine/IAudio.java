package gdv.ucm.libengine;

public interface IAudio {
    ISound newSound(String file, boolean loop); // Crear nuevo sonido
    ISound newSoundAmbient(String file);        // Crear un nuevo sonido ambiente
    void playSound(String id);                  // Reproducir un sonido
    boolean isLoaded(String id);                // Saber si un sonido ya está cargado
}