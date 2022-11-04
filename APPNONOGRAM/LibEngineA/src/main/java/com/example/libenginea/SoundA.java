//package com.example.libenginea;
//
//import android.media.SoundPool;
//
//import gdv.ucm.libengine.ISound;
//
//public class SoundA implements ISound {
//    private int id;
//    private SoundPool soundPool;
//
//    SoundA(int id){
//        this.id = id;
//    }
//
//    void setSoundPool(SoundPool soundPool){
//        this.soundPool = soundPool;
//    }
//
//    @Override
//    public void play() {
//        soundPool.play(id, 1, 1,1, 0, 1);
//    }
//
//    @Override
//    public void stop() {
//        soundPool.stop(id);
//    }
//}
