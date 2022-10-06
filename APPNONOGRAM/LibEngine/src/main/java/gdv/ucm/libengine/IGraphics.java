package gdv.ucm.libengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public interface IGraphics {
    Image newImage(String name);
    IFont newFont(String filename,int size,boolean isBold);

    void translate(int x, int y);
    void scale(int x, int y);

    void save();
    void restore();

    //void drawImage(Image image);
    void setColor(Color color);
    void clear(int color);

    void fillSquare(int cx, int cy, int side);
    void drawSquare(int cx, int cy, int side);
    void drawLine(int initX, int initY, int endX, int endY);
    void drawText(String text, int x, int y);

    //Getters
    int getWidth();
    int getHeight();
    //Setters
    void setResolution();
    void setFont();
}