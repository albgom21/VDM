package gdv.ucm.libengine;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public interface IAudio {
    ISound newSound(String file, boolean loop);
    ISound newSoundAmbient(String file);
    void playSound(String id);
    boolean isLoaded(String id);
}
