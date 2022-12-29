package com.example.libenginea;

import android.media.SoundPool;
import gdv.ucm.libengine.ISound;

public class SoundA implements ISound {
    private int id;
    private int loop;
    private SoundPool soundPool;

    SoundA(int id, int loop){
        this.id = id;
        this.loop = loop;
    }

    void setSoundPool(SoundPool soundPool){       //SETEA EL SOUNDPOOL
        this.soundPool = soundPool;
    }
    public int getLoop(){
        return this.loop;
    }
    public int getId(){
        return this.id;
    }
    @Override
    public void play() {        //INICIA EL SONIDO
        soundPool.play(this.id, 1, 1,1, this.loop, 1);
    }

    @Override
    public void stop() {        //PARA EL SONIDO
        soundPool.stop(id);
    }
}