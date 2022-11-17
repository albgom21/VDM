package com.example.libenginea;

import android.media.SoundPool;

public class SoundA {
    private int id;
    private int loop;
    private SoundPool soundPool;

    SoundA(int id, int loop){
        this.id = id;
        this.loop = loop;
    }

    void setSoundPool(SoundPool soundPool){
        this.soundPool = soundPool;
    }
    public int getLoop(){
        return this.loop;
    }
    public int getId(){
        return this.id;
    }
    public void play() {
        soundPool.play(this.id, 1, 1,1, this.loop, 1);
    }
    public void stop() {
        soundPool.stop(id);
    }
}