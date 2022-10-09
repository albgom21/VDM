package gdv.ucm.libdesktop;

import javax.swing.JFrame;

import gdv.ucm.libenginepc.EnginePC;
import gdv.ucm.libenginepc.SceneTest;

public class Desktop {
    public static void main(String[] args) {
        JFrame renderView = new JFrame("Mi aplicación");

        renderView.setSize(600, 400);
        renderView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renderView.setIgnoreRepaint(true);

        renderView.setVisible(true);
        EnginePC engine = new EnginePC(renderView);

        SceneTest scene = new SceneTest(engine);
        engine.setScene(scene);
        engine.resume();
    }
}