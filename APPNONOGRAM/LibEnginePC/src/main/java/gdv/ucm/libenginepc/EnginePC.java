package gdv.ucm.libenginepc;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import gdv.ucm.libengine.IAudio;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IState;

public class EnginePC implements Runnable, IEngine {
    private BufferStrategy bufferStrategy;
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
//        int intentos = 100;
//        while(intentos-- > 0) {
//            try {
//                this.myView.createBufferStrategy(2);
//                break;
//            }
//            catch(Exception e) {
//            }
//        } // while pidiendo la creación de la buffeStrategy
//        if (intentos == 0) {
//            System.err.println("No pude crear la BufferStrategy");
//            return;
//        }
//
//        this.bufferStrategy = this.myView.getBufferStrategy();
//        this.graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        this.graphics = new GraphicsPC(this.myView, this);
        this.audio = new AudioPC();

    }

    //hasmap al crear una imagen nombre-IImage

    //bucle principal
    @Override
    public void run() {
        if (this.renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        while(this.running && this.myView.getWidth() == 0);
        long lastFrameTime = System.nanoTime();

        //--------------------------------------------------------------------------------------------
        while(this.currentScene != null){ //this.running
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            //inputs
            this.handleInputs(); //            this.currentScene.handleInputs(); //getInput().getlist

            //update
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this.update(elapsedTime);

            do{
                //render
                this.graphics.prepareFrame();
                this.render();
                this.clearInputs();
                this.graphics.getbufferStrategy().show();

                //terminar Frame
                this.graphics.finishFrame();
            }
            while(!this.graphics.cambioBuffer());
        }
        //--------------------------------------------------------------------------------------------

//        // Si el Thread se pone en marcha
//        // muy rápido, la vista podría todavía no estar inicializada.
//        while(this.running && this.myView.getWidth() == 0);
//        // Espera activa. Sería más elegante al menos dormir un poco.
//        long lastFrameTime = System.nanoTime();
//        long informePrevio = lastFrameTime; // Informes de FPS
//        int frames = 0;
//
//        // Bucle de juego principal.
//        while(this.running) {
//            long currentTime = System.nanoTime();
//            long nanoElapsedTime = currentTime - lastFrameTime;
//            lastFrameTime = currentTime;
//
//            //INPUT
//            this.handleInputs();
//            // Actualizamos
//            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
//            this.update(elapsedTime);
//
//            // Pintamos el frame
//            do {
//                do {
////                    this.graphics.prepareFrame();
//                    Graphics graphics = this.graphics.getbufferStrategy().getDrawGraphics();
//                    try {
//                        this.render();
//                        this.clearInputs();
//                    }
//                    finally {
////                        this.graphics.finishFrame();
//                        graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
//                    }
//                } while( this.graphics.getbufferStrategy().contentsRestored());
//                this.graphics.getbufferStrategy().show();
//            } while(this.graphics.getbufferStrategy().contentsLost());

    }

    protected void update(double deltaTime) {
        this.currentScene.update(deltaTime);
    }

    protected void render() {
        // "Borramos" el fondo.
        this.graphics.clear(0xe7d6bd);

        // Pintamos la escena
        this.currentScene.render();

        //Border
        this.graphics.setColor(0x000000);
        this.graphics.fillRect(0,0,this.graphics.borderWidth,this.graphics.getHeight());

        this.graphics.fillRect(this.graphics.borderWidth+this.graphics.logicWidth,0,this.graphics.borderWidth,this.graphics.getHeight());

    }

    protected void handleInputs() {
        this.currentScene.handleInputs();
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
    public JFrame getCanvas() {return this.myView;}
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
    public IState getCurrentScene() {
        return currentScene;
    }
}