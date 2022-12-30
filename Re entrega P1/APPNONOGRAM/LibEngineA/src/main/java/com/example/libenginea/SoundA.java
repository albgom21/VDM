package com.example.libenginea;

import android.media.SoundPool;
import gdv.ucm.libengine.ISound;

public class SoundA implements ISound {
    private int id;             // Identificador del sonido
    private int loop;           // Sonido en bucle
    private SoundPool soundPool;

    SoundA(int id, int loop){
        this.id = id;
        this.loop = loop;
    }

    // REPRODUCIR SONIDO
    @Override
    public void play() {        //INICIA EL SONIDO
        soundPool.play(this.id, 1, 1,1, this.loop, 1);
    }

    // PARAR SONIDO
    @Override
    public void stop() {        //PARA EL SONIDO
        soundPool.stop(id);
    }

    // GETTERS Y SETTERS
    public int getLoop(){
        return this.loop;
    } // SABER SI EL SONIDO ESTÁ EN BUCLE
    public int getId(){
        return this.id;
    }     // OBTENER ID

    void setSoundPool(SoundPool soundPool){       //SETEA EL SOUNDPOOL
        this.soundPool = soundPool;
    }
}