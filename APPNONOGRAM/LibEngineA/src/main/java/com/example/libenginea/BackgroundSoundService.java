//package com.example.libenginea;
//
//import android.app.Service;
//import android.content.Intent;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.AssetManager;
//import android.media.MediaPlayer;
//import android.os.IBinder;
//
//import java.io.IOException;
//
//public class BackgroundSoundService extends Service {
//    MediaPlayer mediaPlayer;
//    AssetManager mgr;
//    String file;
//    BackgroundSoundService(AssetManager mgr){
//        this.mgr = mgr;
//    }
//
//    public void setFile(String file) {
//        this.file = file;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//    @Override
//    public void onCreate() {
//        super.onCreate();
////        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
//        try {
//            AssetFileDescriptor afd = mgr.openFd(file);
//            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.setLooping(true); // Set looping
//        mediaPlayer.setVolume(100, 100);
//    }
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        mediaPlayer.start();
//        return startId;
//    }
//
//    @Override
//    public void onDestroy() {
//        mediaPlayer.stop();
//        mediaPlayer.release();
//    }
//    @Override
//    public void onLowMemory() {
//    }
//}