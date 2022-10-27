package gdv.ucm.libengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public interface IGraphics {
    //Crear recursos (fuentes, imagenes)
    IImage newImage(String ruta);
    IFont newFont(String filename, int size, boolean isBold);

    void translate(int x, int y);
    void scale(float x, float y);

    void save();
    void restore();

    void drawImage(IImage image, int x, int y); // float w, float h para la escala double rotate para rotación
//    void setColor(IColor color);
    void setColor(int color);
//    void clear(IColor color);
    void clear(int color);

    void fillSquare(int cx, int cy, int side);
    void drawSquare(int cx, int cy, int side);
    void drawLine(int initX, int initY, int endX, int endY);
    void drawText(String text, int x, int y, int color); //IFont font, int tamaño, IColor color

    int realToLogicX(int x);
    int realToLogicY(int y);

    //Getters
    int getWidth();
    int getWidthLogic();
    int getHeight();
    int getHeightLogic();

    //Setters
    void setResolution(int w, int h);
    void setFont(IFont font);

}