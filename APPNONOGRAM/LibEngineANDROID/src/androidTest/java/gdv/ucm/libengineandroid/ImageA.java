package gdv.ucm.libengineandroid;

import android.graphics.Bitmap;
import android.media.Image; //Preguntar si Image o Bitmap

import gdv.ucm.libengine.IImage;

public class ImageA implements IImage {
    private Bitmap img;
    ImageA(Bitmap img){     this.img = img;    }
    @Override
    public int getWidth() {
        return this.img.getWidth();
    }

    @Override
    public int getHeight() {
        return this.img.getHeight();
    }
}
