package gdv.ucm.libengine;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public interface IAudio {
    ISound newSound(String file);
    void playSound(String id);

}
