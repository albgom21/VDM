package gdv.ucm.libenginepc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import gdv.ucm.libengine.IButton;

public class ButtonPC extends JFrame implements IButton, ActionListener {
    JButton boton;
    public ButtonPC(JButton boton) {
        setLayout(null);
        this.boton = boton;
        this.boton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.boton) {
            System.exit(0);
        }
    }
}
