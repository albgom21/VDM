package gdv.ucm.libenginepc;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import gdv.ucm.libengine.IFont;

public class FontPC implements IFont {
    Font font;
    String ruta = ""; //Ponerlo en el de ANDROID igual

    FontPC(Font font){
        this.font = font;
    }
    /*FontPC(String name, int size, boolean isBold){
        InputStream is = null;
        try {
            is = new FileInputStream(name);
            this.font = Font.createFont(Font.TRUETYPE_FONT, is); //Elevar la excepcion
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        if(isBold)
            font = font.deriveFont(Font.BOLD);
        font = font.deriveFont(size);
    }*/
    public Font getFont() {return this.font;}
    @Override
    public int getSize() {
        return this.font.getSize();
    }

    @Override
    public boolean isBold() {
        return this.font.isBold();
    }

}
