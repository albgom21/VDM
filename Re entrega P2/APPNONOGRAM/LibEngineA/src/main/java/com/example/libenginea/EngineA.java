package com.example.libenginea;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.ads.AdRequest;

public class EngineA implements Runnable {
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


    private boolean rewardObtain;
    private ReadA read;
    private int logicWidth;
    private int logicHeight;

    private SensorManager sensorManager;
    private Serialization serialization;
    private AdManager adManager;
    private AdRequest adRequest;


    public EngineA(SurfaceView myView, int logicWidth, int logicHeight, Activity c, AdRequest adRequest, String filenameStats) {
        this.myView = myView;
        this.context = c;
        this.input = new InputA();
        this.myView.setOnTouchListener(this.input);
        this.mgr = myView.getContext().getAssets();
        this.holder = this.myView.getHolder();
        this.canvas = new Canvas();
        this.logicHeight = logicHeight;
        this.logicWidth = logicWidth;
        this.graphics = new GraphicsA(this.myView, this.logicWidth, this.logicHeight, this.canvas, this.mgr);
        this.audio = new AudioA(this.mgr);
        this.read = new ReadA(this.mgr);
        this.rewardObtain = false;
        this.filenameStats = filenameStats;

        this.saveBoard = false;
        this.randomBoard = false;
        this.adRequest = adRequest;
    }

    public void iniManagers(){
        // SERIALIZACION
        serialization = new Serialization(context);

        // SENSOR
        sensorManager = new SensorManager(this);

        // ANUNCIO
        adManager = new AdManager( this, context, adRequest);
        adManager.preloadReward();
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
            this.graphics.setLogicWidth(this.logicHeight);
            this.graphics.setLogicHeight(this.logicWidth);
        }
        else
        {
            this.graphics.setLogicWidth(this.logicWidth);
            this.graphics.setLogicHeight(this.logicHeight);
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


//----------Métodos sincronización (parar y reiniciar aplicación)--------------------
    public void resume() {
        serialization.desSerialize(stats,filenameStats);

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
        serialization.serialize(stats,filenameStats);

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

//------------------------------------GETTERS Y SETTERS------------------------------------
    public boolean getRandomBoard() { return randomBoard; }

    public boolean getSaveBoard() {
        return saveBoard;
    }

    public Context getContext() {return context; }

    public Boolean getRewardObtain() {return rewardObtain; }

    public void useRewardObtain() { rewardObtain = false; }

    public ReadA getRead() {
        return read;
    }

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

    public Serialization getSerialization() {
        return serialization;
    }
    public AdManager getAdManager() {
        return adManager;
    }
    public void setCurrentScene(StateA currentScene) { this.currentScene = currentScene; }

    public void setStats(StatsA stats) { this.stats = stats;}

    public void setRandomBoard(boolean randomBoard) { this.randomBoard = randomBoard; }

    public void setSaveBoard(Boolean b){ saveBoard = b;}

    public void setRewardObtain(boolean rewardObtain) {
        this.rewardObtain = rewardObtain;
    }
}