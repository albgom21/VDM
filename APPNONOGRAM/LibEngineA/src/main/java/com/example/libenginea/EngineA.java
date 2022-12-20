package com.example.libenginea;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class EngineA implements Runnable, SensorEventListener {
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private AssetManager mgr;

    private Activity context;

    private StatsA stats;

    private Thread renderThread;
    private boolean running;

    private boolean saveBoard;
    private boolean randomBoard;

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

    public EngineA(SurfaceView myView, Activity c, AdRequest adRequest, String filenameStats) {
        this.myView = myView;
        this.context = c;
        this.input = new InputA();
        this.myView.setOnTouchListener(this.input);
        this.mgr = myView.getContext().getAssets();
        this.holder = this.myView.getHolder();
        this.canvas = new Canvas();
        this.graphics = new GraphicsA(this.myView, this.canvas, this.mgr);
        this.audio = new AudioA(this.mgr);
        this.read = new ReadA(this.mgr);
        this.adRequest = adRequest;
        this.rewardObtain = false;
        this.filenameStats = filenameStats;

        this.saveBoard = false;
        this.randomBoard = false;

        // SENSOR
        SensorManager sensorManager=(SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        // ANUNCIO
        preloadReward();
    }

    //BUCLE PRINCIPAL
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

        if(this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.graphics.setLogicWidth(600);
            this.graphics.setLogicHeight(400);
        }
        else
        {
            this.graphics.setLogicWidth(400);
            this.graphics.setLogicHeight(600);
        }
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

//-------------------------SERIALIZACIÓN Y DESERIALIZACIÓN---------------------------
    public void desSerialize(Serializable serializable, String file){
        try {
            // Creamos un FileInputStream para leer desde el archivo en el almacenamiento interno de la aplicación
            FileInputStream fis = this.context.openFileInput(file);

            // Creamos un ObjectInputStream a partir del FileInputStream
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Leemos el objeto serializado del archivo y lo asignamos a una variable de tipo Serializable
            serializable = (Serializable) ois.readObject();

            // Cerramos los streams
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void serialize(Serializable serializable, String file){
        try {
            // Creamos un FileOutputStream para escribir en un archivo en el almacenamiento interno de la aplicación
            FileOutputStream fos = this.context.openFileOutput(file, Context.MODE_PRIVATE);

            // Creamos un ObjectOutputStream a partir del FileOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Serializamos el objeto persona y lo escribimos en el archivo
            oos.writeObject(serializable);

            // Cerramos los streams
            oos.close();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


//----------Métodos sincronización (parar y reiniciar aplicación)--------------------
    public void resume() {
        desSerialize(stats,filenameStats);

        if (!this.running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.running = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.renderThread = new Thread(this);
            this.renderThread.start();
            this.audio.getmPlayer().start();
        }
    }

    public void pause() {
        serialize(stats,filenameStats);

        if (this.running) {
            this.running = false;
            this.audio.getmPlayer().pause();

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

//-----------------------------------------ANUNCIOS----------------------------------
    public void preloadReward()
    {
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

//-------------------------------------SENSOR GIROSCOPIO-------------------------------------
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float valorGiroscopio = 0;
        if(this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            valorGiroscopio = sensorEvent.values[1]; // EN EL EJE Y
        else
            valorGiroscopio = sensorEvent.values[0]; // EN EL EJE X

        if(valorGiroscopio>3.0){
            int p = this.stats.getPaleta()-1;
            if(p >= 0 && this.stats.isPaletaUnlock(p))
                this.stats.setPaleta(p);
        }
        else if(valorGiroscopio<-3.0){
            int p = this.stats.getPaleta()+1;
            if(p <= 3 && this.stats.isPaletaUnlock(p))
                this.stats.setPaleta(p);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


//------------------------------------GETTERS Y SETTERS------------------------------------
    public boolean getRandomBoard() { return randomBoard; }

    public boolean getSaveBoard() {
        return saveBoard;
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

    public void setCurrentScene(StateA currentScene) { this.currentScene = currentScene; }

    public void setStats(StatsA stats) { this.stats = stats;}

    public void setRandomBoard(boolean randomBoard) { this.randomBoard = randomBoard; }

    public void setSaveBoard(Boolean b){ saveBoard = b;}
}