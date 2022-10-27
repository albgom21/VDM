package gdv.ucm.libenginepc;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gdv.ucm.libengine.IImage;

public class ImagePC implements IImage {
    private Image img;
    ImagePC(Image img){
        this.img = img;
    }
/*
    ImagePC(String path){
        this.img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    Image getImg(){return this.img;}
    @Override
    public int getWidth() {
        return this.img.getWidth(null);
    }

    @Override
    public int getHeight() {
        return this.img.getHeight(null);
    }
}
