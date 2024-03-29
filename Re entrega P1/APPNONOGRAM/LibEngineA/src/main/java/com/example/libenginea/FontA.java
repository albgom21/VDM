package com.example.libenginea;

import android.graphics.Typeface;
import android.os.Build;
import gdv.ucm.libengine.IFont;

public class FontA implements IFont {
    Typeface font;

    FontA(Typeface font){
        this.font = font;
    }

    // GETTERS
    public Typeface getFont(){ return this.font; }          // OBTENER FUENTE

    @Override
    public int getSize() {                                  //TAMAÑO FUENTE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            return font.getWeight();
        return -1;
    }

    @Override
    public boolean isBold() {
        return this.font.isBold();
    }  //COMPROBAR SI ES NEGRITA
}