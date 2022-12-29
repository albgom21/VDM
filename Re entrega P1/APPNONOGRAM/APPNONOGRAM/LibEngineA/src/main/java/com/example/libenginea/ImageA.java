package com.example.libenginea;

import android.graphics.Bitmap;
import gdv.ucm.libengine.IImage;

public class ImageA implements IImage {
    private Bitmap img;

    ImageA(Bitmap img){this.img = img;}

    public Bitmap getImg(){return img;}     //IMAGEN

    @Override
    public int getWidth() {
        return this.img.getWidth();
    }       //ANCHO IMAGEN

    @Override
    public int getHeight() {
        return this.img.getHeight();
    }       //ALTURA IMAGEN
}