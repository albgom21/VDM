package com.example.libenginea;

import android.graphics.Typeface;

import gdv.ucm.libengine.IFont;

public class FontA implements IFont {
    Typeface font;

    FontA(Typeface font){
        this.font = font;
    }

    public Typeface getFont(){return this.font;}
    @Override
    public int getSize() {
        //TextView myTextView = (TextView) findViewById(yourTextViewId);
        //return this.font.getSize();
        return 0;
    }

    @Override
    public boolean isBold() {
        return this.font.isBold();
    }
}
