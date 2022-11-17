package com.example.libenginea;

import android.graphics.Bitmap;

public class ImageA {
    private Bitmap img;

    ImageA(Bitmap img){this.img = img;}

    public Bitmap getImg(){return img;}

    public int getWidth() {
        return this.img.getWidth();
    }

    public int getHeight() {
        return this.img.getHeight();
    }
}