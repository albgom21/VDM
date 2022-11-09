package com.example.libenginea;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class EngineA implements Runnable, IEngine {
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private AssetManager mgr;

//    private Paint paint;

    private Thread renderThread;
    private boolean running;
    private IState currentScene;
    private IInput input;

    private GraphicsA graphics;
    private AudioA audio;

    public EngineA(SurfaceView myView){
        this.myView = myView;
        this.input = new InputA();
        this.myView.setOnTouchListener((View.OnTouchListener) this.input);
        this.mgr = myView.getContext().getAssets();
        this.holder = this.myView.getHolder();
//        this.paint = new Paint();
//        this.paint.setColor(0xFF0000);
        this.canvas = new Canvas();
//        this.canvas = this.holder.lockCanvas();
        this.graphics = new GraphicsA(this.myView, this.canvas); //Pasar Canvas?
        this.audio = new AudioA();
        this.graphics.setAssetManager(this.mgr);
        this.audio.setAssetManager(this.mgr);
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

        // Bucle de juego principal.
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
            this.clearInputs();
            this.graphics.unlockCanvas();
        }
    }

    protected void update(double deltaTime) {
        this.currentScene.update(deltaTime);
    }

    protected void render() {
        // "Borramos" el fondo.
        this.getGraphics().clear(0xe7d6bd);

        // Pintamos la escena
        this.currentScene.render();

//        this.graphics.setColor(0xFFFFFF);
//        this.graphics.fillRect(0,0,this.graphics.borderWidth,this.graphics.getHeight());
//        this.graphics.fillRect(this.graphics.getWidth()-this.graphics.borderWidth,0,this.graphics.borderWidth,this.graphics.getHeight());
//
//        this.graphics.fillRect(0,this.graphics.borderTop,this.graphics.getWidth(),this.graphics.borderHeight);
//        this.graphics.fillRect(0,this.graphics.getHeight(),this.graphics.getWidth(),-this.graphics.borderHeight);

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
        }
    }

    public void pause() {
        if (this.running) {
            this.running = false;
            //this.audio.getmPlayer().pause();---------------------------------------------
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

    @Override
    public IGraphics getGraphics() {
        return this.graphics;
    }

    @Override
    public IAudio getAudio() {
        return this.audio;
    }

    @Override
    public IState getState() {
        return this.currentScene;
    }

    @Override
    public IInput getInput() {
        return this.input;
    }

    @Override
    public void setCurrentScene(IState currentScene) {
        this.currentScene = currentScene;
    }
}
