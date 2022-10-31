package gdv.ucm.libdesktop;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

import gdv.ucm.libenginepc.EnginePC;
import gdv.ucm.liblogica.MainScene;

public class Desktop {
    public static void main(String[] args) throws LineUnavailableException, IOException {
        JFrame renderView = new JFrame("NONOGRAM PC");

        renderView.setSize(1920, 1080);
        renderView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renderView.setIgnoreRepaint(true);
        renderView.setVisible(true);
        EnginePC engine = new EnginePC(renderView);

        MainScene scene = new MainScene(engine);

        engine.setCurrentScene(scene);
        engine.resume();
    }
}