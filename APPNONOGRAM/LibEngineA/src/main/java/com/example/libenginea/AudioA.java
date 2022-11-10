package com.example.libenginea;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.ISound;

public class AudioA implements IAudio {
    private AssetManager mgr;
    private SoundPool soundPool;
    HashMap<String,SoundA> sounds = new HashMap<>();
    private MediaPlayer mPlayer;
    AudioA(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        }
        mPlayer = new MediaPlayer();
        mPlayer.reset();
    }
    @Override
    public ISound newSound(String file, boolean loop) {
        int soundId = -1;
        try {
            AssetFileDescriptor assetDescriptor = mgr.openFd(file);
            soundId = this.soundPool.load(assetDescriptor, 1);

        }catch (IOException e) {
            throw new RuntimeException("Couldn't load sound.");
        }
        int l = loop ? -1 : 0;
        SoundA sound = new SoundA(soundId, l);
        sounds.put(file,sound);
        return sound;
    }

    @Override
    public ISound newSoundAmbient(String file) {
        try {
            AssetFileDescriptor afd = mgr.openFd(file);
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mPlayer.prepare();
            mPlayer.setLooping(true);

        } catch (IOException e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }

        SoundA sound = new SoundA(mPlayer.getAudioSessionId(), 1);
        mPlayer.start();
        sounds.put(file,sound);
        return sound;
    }

    @Override
    public void playSound(String id) {
        soundPool.play(sounds.get(id+".wav").getId(), 1, 1,1, sounds.get(id+".wav").getLoop(), 1);
    }

    @Override
    public boolean isLoaded(String id) {
        return sounds.containsKey(id);
    }

    public void setAssetManager(AssetManager mgr){
        this.mgr = mgr;
    }

    public MediaPlayer getmPlayer() {
        return this.mPlayer;
    }
}