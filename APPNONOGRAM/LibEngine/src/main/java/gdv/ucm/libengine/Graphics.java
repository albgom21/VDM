package gdv.ucm.libengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public interface Graphics {
    Image newImage(String name);
    Font newFont(String filename,int size,boolean isBold);
    void clear(int color);
    void translate(int x, int y);
    void scale(int x, int y);
    void save();
    void restore();
    //void drawImage(Image image);
    void setColor(Color color);
    void fillSquare(int cx, int cy, int side); // puede que no sea int, mejor una tupla
    void drawSquare(int cx, int cy, int side);
    void drawLine(int initX, int initY, int endX, int endY);
    void drawText(String text, int x, int y);
    int getWidth();
    int getHeight();

}
