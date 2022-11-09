package gdv.ucm.libdesktop;

import javax.swing.JFrame;
import gdv.ucm.libenginepc.EnginePC;
import gdv.ucm.liblogica.TitleScene;

public class Desktop {
    public static void main(String[] args) {
        // Creación de la ventana
        JFrame renderView = new JFrame("NONOGRAM PC");
        renderView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renderView.setIgnoreRepaint(true);
        renderView.setVisible(true);

        // Creación del motor de PC y la escena inicial
        EnginePC engine = new EnginePC(renderView);
        TitleScene scene = new TitleScene(engine);
        engine.setCurrentScene(scene);
        engine.resume();
    }
}