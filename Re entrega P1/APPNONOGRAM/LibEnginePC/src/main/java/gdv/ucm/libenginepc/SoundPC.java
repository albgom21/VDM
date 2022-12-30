package gdv.ucm.libenginepc;

import javax.sound.sampled.Clip;

import gdv.ucm.libengine.ISound;

public class SoundPC implements ISound {
    private Clip clip;
    SoundPC(Clip clip){
        this.clip = clip;
    }

    public Clip getClip(){return this.clip;}        // OBTENER CLIP DE AUDIO

    @Override
    public void play() {
        this.clip.start();
    }       //REPRODUCIR SONIDO

    @Override
    public void stop() {                            //PARAR SONIDO
        if(this.clip.isRunning())
            this.clip.stop();
    }
}