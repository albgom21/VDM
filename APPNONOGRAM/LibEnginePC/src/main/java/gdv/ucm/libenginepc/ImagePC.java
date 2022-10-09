package gdv.ucm.libenginepc;

import java.awt.Image;

import gdv.ucm.libengine.IImage;

public class ImagePC implements IImage {
    private Image img;
    ImagePC(Image img){
        this.img = img;
    }
    @Override
    public int getWidth() {
        return this.img.getWidth(null);
    }

    @Override
    public int getHeight() {
        return this.img.getHeight(null);
    }
}
