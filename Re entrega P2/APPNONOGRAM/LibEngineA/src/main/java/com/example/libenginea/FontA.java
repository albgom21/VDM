package com.example.libenginea;

import android.graphics.Typeface;
import android.os.Build;

public class FontA {
    Typeface font;

    FontA(Typeface font){
        this.font = font;
    }

    public Typeface getFont(){return this.font;}


    public int getSize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            return font.getWeight();
        return -1;
    }

    public boolean isBold() {
        return this.font.isBold();
    }
}