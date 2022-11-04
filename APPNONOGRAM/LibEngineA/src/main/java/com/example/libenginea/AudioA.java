//package com.example.libenginea;
//
//import android.content.res.AssetFileDescriptor;
//import android.content.res.AssetManager;
//import android.media.SoundPool;
//import android.os.Build;
//
//import androidx.annotation.RequiresApi;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//import gdv.ucm.libengine.IAudio;
//import gdv.ucm.libengine.ISound;
//
//public class AudioA implements IAudio {
//    private AssetManager mgr;
//    private SoundPool soundPool;
//    HashMap<String,Integer> sounds = new HashMap<String,Integer>();
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    AudioA(){
//        this.soundPool = new SoundPool.Builder().setMaxStreams(10).build();
//    }
//    @Override
//    public ISound newSound(String file) {
//
//        int soundId = -1;
//        try {
//            AssetFileDescriptor assetDescriptor = mgr.openFd(file);
//            soundId = this.soundPool.load(assetDescriptor, 1);
//            sounds.put(file,soundId);
//        }catch (IOException e) {
//            throw new RuntimeException("Couldn't load sound.");
//        }
//
//        SoundA sound = new SoundA(soundId);
//
//        return sound;
//    }
//
//    @Override
//    public void playSound(String id) {
//        soundPool.play(sounds.get(id+".mp3"), 1, 1,1, 0, 1);
//    }
//    public void setAssetManager(AssetManager mgr){
//        this.mgr = mgr;
//    }
//}
