package com.example.libenginea;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EngineA implements Runnable, SensorEventListener {
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private AssetManager mgr;

    private Activity context;
    private StatsA stats;

    private Thread renderThread;
    private boolean running;

    private StateA currentScene;
    private InputA input;
    private GraphicsA graphics;
    private AudioA audio;
    private String filenameStats;
    public ReadA getRead() {
        return read;
    }

    private RewardedAd mRewardedAd;
    private AdRequest adRequest;
    private final String TAG = "MainActivity";
    private boolean rewardObtain;
    private ReadA read;
//    private IntentSystemAndroid intentSystemAndroid;

    public EngineA(SurfaceView myView, StatsA statsA, Activity c, AdRequest adRequest) {
        this.myView = myView;
        this.context = c;
        this.input = new InputA();
        this.myView.setOnTouchListener((View.OnTouchListener) this.input);
        this.mgr = myView.getContext().getAssets();
        this.holder = this.myView.getHolder();
        this.canvas = new Canvas();
        this.graphics = new GraphicsA(this.myView, this.canvas);
        this.audio = new AudioA();
        this.graphics.setAssetManager(this.mgr);
        this.audio.setAssetManager(this.mgr);
        this.read = new ReadA(this.mgr);
        this.stats = statsA;
        this.adRequest = adRequest;
        if(this.stats == null)
            this.stats = new StatsA();
        this.rewardObtain = false;
        this.filenameStats = "stats.ser";

//        this.intentSystemAndroid = new IntentSystemAndroid(this.context);

        // SENSOR
        SensorManager sensorManager=(SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filenameStats);
            ObjectInputStream in = new ObjectInputStream(file);
            // Method for deserialization of object
            stats = (StatsA)in.readObject();
            in.close();
            file.close();
            System.out.println("Object has been deserialized ");
        } catch(Exception ex) {
            System.out.println("Exception is caught");
        }
        preloadReward();
    }

    //bucle principal
    @Override
    public void run() {
        if (renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        while(this.running && this.myView.getWidth() == 0);
        long lastFrameTime = System.nanoTime();

        // Bucle de juego principal
        while(running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            //inputs
            this.handleInputs();

            //update
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this.update(elapsedTime);

            // Pintamos el frame
            while (!this.holder.getSurface().isValid());
            this.graphics.lockCanvas();
            this.graphics.prepareFrame();
            this.render();
            this.graphics.unlockCanvas();
        }
    }

    protected void update(double deltaTime) {
        this.currentScene.update(deltaTime);
    }

    protected void render() {
        // "Borramos" el fondo.
        if(this.stats.getPaleta() == 0)
            this.getGraphics().clear(0xe7d6bd); //paleta normal
        else if(this.stats.getPaleta() == 1)
            this.getGraphics().clear(0xc1d8e2); //paleta 1
        else if(this.stats.getPaleta() == 2)
            this.getGraphics().clear(0xb06c92); //paleta 2
        else if(this.stats.getPaleta() == 3)
            this.getGraphics().clear(0x9bbdaa); //paleta 3

        // Pintamos la escena
        this.currentScene.render(this.graphics);
    }

    protected void handleInputs() {
        this.currentScene.handleInputs(this.input);
    }
    protected void clearInputs() {
        this.input.clearEvents();
    }

    //Métodos sincronización (parar y reiniciar aplicación)
    public void resume() {
        if (!this.running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.running = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.renderThread = new Thread(this);
            this.renderThread.start();
            this.audio.getmPlayer().start();

            // Deserialization
            try
            {
                // Reading the object from a file
                FileInputStream file = new FileInputStream(filenameStats);
                ObjectInputStream in = new ObjectInputStream(file);
                // Method for deserialization of object
                stats = (StatsA)in.readObject();
                in.close();
                file.close();
                System.out.println("Object has been deserialized ");
            } catch(Exception ex) {
                System.out.println("Exception is caught");
            }
        }
    }

    public void preloadReward()
    {
        // LOAD ----------------------------------------------------------------------------------
        RewardedAd.load(this.context, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        System.out.println("Error adLoad");
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        System.out.println("Reward added");
                    }
                });

    }

    public void showReward() {
        Activity act = this.context;
        this.context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // LOAD ----------------------------------------------------------------------------------
                if (mRewardedAd != null) {
                    // SET_CALLBACK -----------------------------------------------------------------------------
                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            Log.d(TAG, "Ad dismissed fullscreen content.");
                            //mRewardedAd = null;
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.
//                           preloadReward();
                            Log.e(TAG, "Ad failed to show fullscreen content.");
                            //mRewardedAd = null;
                        }

                        @Override
                        public void onAdImpression() {
                            // Called when an impression is recorded for an ad.
                            Log.d(TAG, "Ad recorded an impression.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
//                            preloadReward();
                            // Called when ad is shown.
                            Log.d(TAG, "Ad showed fullscreen content.");
                        }
                    });

                    // SHOW ----------------------------------------------------------------------
                    mRewardedAd.show(act, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
//                            currentScene.
                            rewardObtain = true;
                        }
                    });

                    //mRewardedAd = null;
                    preloadReward();
                } else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });
    }

    public void pause() {
        if (this.running) {
            this.running = false;
            this.audio.getmPlayer().pause();
            // Serialization
            try
            {
                //Saving of object in a file
                FileOutputStream file = new FileOutputStream(filenameStats) ;
                ObjectOutputStream out = new ObjectOutputStream(file) ;
                // Method for serialization of object
                out.writeObject(stats) ;
                out.close() ;
                file.close() ;
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    this.renderThread.join();
                    this.renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
        }
    }
    public Context getContext() {return context; }

    public Boolean getRewardObtain() {return rewardObtain; }
    public void useRewardObtain() { rewardObtain = false; }

    public StatsA getStats() { return stats; }

    public GraphicsA getGraphics() {
        return this.graphics;
    }

    public AudioA getAudio() {
        return this.audio;
    }

    public StateA getState() {
        return this.currentScene;
    }

    public InputA getInput() {
        return this.input;
    }

    public void setCurrentScene(StateA currentScene) {
        this.currentScene = currentScene;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float i = sensorEvent.values[0]; // EN EL EJE X
        if(i>3.0){
            int p = this.stats.getPaleta()-1;
            if(p >= 0 && this.stats.isPaletaUnlock(p))
                this.stats.setPaleta(p);
        }
        else if(i<-3.0){
            int p = this.stats.getPaleta()+1;
            if(p <= 3 && this.stats.isPaletaUnlock(p))
                this.stats.setPaleta(p);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class IntentSystemAndroid {
        // activity context
        private Context context;
        private Activity activity;
        private String channel_name;
        private String channel_description;
        private String channel_id;

        public IntentSystemAndroid(Context cont) {


//            createChannel();
            //createNotification();
        }



        public void createNotification() {
            Intent notifyIntent = new Intent(this.context, this.context.getClass());
            notifyIntent.putExtra("Monedas",10);
            // Set the Activity to start in a new, empty task
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Create the PendingIntent
            PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                    this.context, 0, notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, this.channel_id) //@mipmap/ic_launcher
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setColor(Color.RED)
                    .setContentTitle( "Entra ahora para conseguir 10 monedas gratis" )
                    .setContentText( "¡Llevas tiempo sin jugar, entra ahora!" )
                    .setStyle( new NotificationCompat.BigTextStyle()
                            .bigText( "¡Llevas tiempo sin jugar, entra ahora!" ))
                    .setPriority(NotificationCompat. PRIORITY_DEFAULT)
                    .setContentIntent(notifyPendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(1, builder.build());
        }
    }
}