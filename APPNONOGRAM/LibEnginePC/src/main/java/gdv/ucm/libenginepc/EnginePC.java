package gdv.ucm.libenginepc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import gdv.ucm.libengine.IColor;
import gdv.ucm.libengine.IEngine;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IState;

public class EnginePC implements Runnable, IEngine {
    private BufferStrategy bufferStrategy;
    private JFrame myView;
    private Thread renderThread;
    private boolean running;
    private IState scene;

    private GraphicsPC graphics;

    public EnginePC(JFrame myView){
        this.myView = myView;
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
        this.graphics = new GraphicsPC(this.myView);
        //REFACTORIZAR BUFFER STRATEGY EN EL RUN
    }
    
    //bucle principal
    @Override
    public void run() {
        if (renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        // Si el Thread se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while(this.running && this.myView.getWidth() == 0);
        // Espera activa. Sería más elegante al menos dormir un poco.
        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle de juego principal.
        while(running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Actualizamos
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this.update(elapsedTime);

            // Pintamos el frame
            do {
                do {
                    Graphics graphics = this.bufferStrategy.getDrawGraphics();
                    try {
                        this.render();
                    }
                    finally {
                        graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
                    }
                } while(this.bufferStrategy.contentsRestored());
                this.bufferStrategy.show();
            } while(this.bufferStrategy.contentsLost());
        }
    }

    protected void update(double deltaTime) {
        this.scene.update(deltaTime);

    }

    protected void render() {
        // "Borramos" el fondo.
        this.getGraphics().clear(IColor.BLACK);
        // Pintamos la escena
        this.scene.render(this);
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
        return graphics;
    }

    @Override
    public void setScene(IState scene) {
        this.scene = scene;
    }
}