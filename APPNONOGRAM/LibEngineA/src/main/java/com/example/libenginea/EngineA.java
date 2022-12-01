package com.example.libenginea;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class EngineA implements Runnable {
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private AssetManager mgr;

    private StatsA stats;

    private Thread renderThread;
    private boolean running;

    private StateA currentScene;
    private InputA input;
    private GraphicsA graphics;
    private AudioA audio;

    public ReadA getRead() {
        return read;
    }

    private ReadA read;

    public EngineA(SurfaceView myView){
        this.myView = myView;
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
        this.stats = new StatsA();
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
        }
    }

    public void pause() {
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
}