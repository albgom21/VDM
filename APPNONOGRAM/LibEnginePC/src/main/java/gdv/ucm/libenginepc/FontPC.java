package gdv.ucm.libenginepc;

import java.awt.Font;

import gdv.ucm.libengine.IFont;

public class FontPC implements IFont {
    Font font;

    FontPC(Font font){
        this.font = font;
    }

    @Override
    public int getSize() {
        return this.font.getSize();
    }

    @Override
    public boolean isBold() {
        return this.font.isBold();
    }
}
