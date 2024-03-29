package gdv.ucm.libenginepc;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.ISound;

import java.util.HashMap;

public class AudioPC implements IAudio {
    HashMap<String,SoundPC> sounds = new HashMap<>();   //MAPA DE CLAVE-VALOR NOMBRE-SONIDO

    @Override
    public ISound newSound(String file, boolean loop) {     //NUEVO SONIDO
        AudioInputStream audioStream = null;
        Clip clip = null;
        if (!isLoaded(file)){ // Si no esta cargado el sonido
            try {
            File audioFile = new File("data/"+file);
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            } catch (Exception e) {
                System.err.println("Couldn't load audio file");
                e.printStackTrace();
            }
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(audioStream);
                if(loop)
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SoundPC sound = new SoundPC(clip);
            sounds.put(file,sound);
            return sound;
        }
        else
            return null;
    }

    @Override
    public ISound newSoundAmbient(String file) {        //NUEVA MUSICA
        AudioInputStream audioStream = null;
        Clip clip = null;
        if(!isLoaded(file)){ // Si no esta cargado la musica
            try {
                File audioFile = new File("data/"+file);
                audioStream = AudioSystem.getAudioInputStream(audioFile);
            } catch (Exception e) {
                System.err.println("Couldn't load audio file");
                e.printStackTrace();
            }
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            try {
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SoundPC sound = new SoundPC(clip);
            sounds.put(file,sound);

            return sound;}
        else
            return null;
    }

    @Override
    public void playSound(String id) {     //REPRODUCIR EL SONIDO DESDE EL PRINCIPIO
        sounds.get(id+".wav").getClip().setFramePosition(0);
        sounds.get(id+".wav").play();
    }

    @Override
    public boolean isLoaded(String id) {
        return sounds.containsKey(id);
    }  //SI EL SONIDO ESTA CARGADO
}