package gdv.ucm.libengineandroid;

import gdv.ucm.libengine.IFont;
import android.graphics.Typeface;
import android.view.textservice.TextInfo;
import android.widget.TextView;

public class FontA implements IFont {
    Typeface font;

    FontA(Typeface font){
        this.font = font;
    }

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
