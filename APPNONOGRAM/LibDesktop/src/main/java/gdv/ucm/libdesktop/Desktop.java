package gdv.ucm.libdesktop;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.Timer;

import gdv.ucm.libenginepc.EnginePC;
import gdv.ucm.liblogica.MainScene;
import gdv.ucm.liblogica.SelectLvlScene;
import gdv.ucm.liblogica.TitleScene;

public class Desktop {
    public static void main(String[] args) throws LineUnavailableException, IOException {
        JFrame renderView = new JFrame("NONOGRAM PC");

//        renderView.setSize(1920, 1080);
        renderView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renderView.setIgnoreRepaint(true);
        renderView.setVisible(true);
        EnginePC engine = new EnginePC(renderView);

        TitleScene scene = new TitleScene(engine);
        //SelectLvlScene scene = new SelectLvlScene(engine);

        engine.setCurrentScene(scene);
        engine.resume();
    }
}