package gdv.ucm.libenginepc;

import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class EnginePC implements Runnable, IEngine {
    private JFrame myView;
    private Thread renderThread;
    private boolean running;
    private InputPC input;
    private IState currentScene;
    private GraphicsPC graphics;
    private AudioPC audio;

    public EnginePC(JFrame myView){
        this.myView = myView;
        this.input = new InputPC();
        this.myView.addMouseListener(this.input);

        this.graphics = new GraphicsPC(this.myView, 400,600);
        this.audio = new AudioPC();
    }

    // Bucle principal
    @Override
    public void run() {
        if (this.renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        while(this.running && this.myView.getWidth() == 0);
        long lastFrameTime = System.nanoTime();

        while(this.currentScene != null){ //this.running
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            //inputs
            this.handleInputs();

            //update
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this.update(elapsedTime);

            do{
                //render
                this.graphics.prepareFrame();
                this.render();
                this.graphics.show();
                //terminar Frame
                this.graphics.finishFrame();
            }
            while(!this.graphics.cambioBuffer());
        }
    }

    protected void update(double deltaTime) {
        this.currentScene.update(deltaTime);
    }

    protected void render() {
        // "Borramos" el fondo.
        this.graphics.clear(0xe7d6bd);

        // Pintamos la escena
        this.currentScene.render(this.graphics);

        //Border
        this.graphics.setColor(0x000000);

        // DIBUJAR BORDES LATERALES
        this.graphics.fillRect(0,0,this.graphics.borderWidth,this.graphics.getHeight());
        this.graphics.fillRect(this.graphics.getWidth()-this.graphics.borderWidth,0,this.graphics.borderWidth,this.graphics.getHeight());

        // DIBUJAR BORDES Up & Down
        this.graphics.fillRect(0,this.graphics.borderTop,this.graphics.getWidth(),this.graphics.borderHeight);
        this.graphics.fillRect(0,this.graphics.getHeight(),this.graphics.getWidth(),-this.graphics.borderHeight);

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