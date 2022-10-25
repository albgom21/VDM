package gdv.ucm.libdesktop;

import javax.swing.JFrame;

import gdv.ucm.libenginepc.EnginePC;
import gdv.ucm.libenginepc.SceneTest;
import gdv.ucm.liblogica.Scene;

public class Desktop {
    public static void main(String[] args) {
        JFrame renderView = new JFrame("NONOGRAM PC");

        renderView.setSize(1920, 1080);
        renderView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renderView.setIgnoreRepaint(true);

        renderView.setVisible(true);
        EnginePC engine = new EnginePC(renderView);

        SceneTest scene = new SceneTest(engine);
        //---------------------------------------------------
//        Scene s = new Scene(engine);
        //---------------------------------------------------

        engine.setCurrentScene(scene);
        engine.resume();
    }
}