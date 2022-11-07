package gdv.ucm.libengine;

public interface IGraphics {
    //Crear recursos (fuentes, imagenes)
    IImage newImage(String ruta);
    IFont newFont(String filename, int size, boolean isBold);

    void translate(int x, int y);
    void scale(float x, float y);

    void setOffsetX(int ofx);
    void setOffsetY(int ofy);
    int getOffsetX();
    int getOffsetY();

    void save();
    void restore();

    void drawImage(IImage image, int x, int y, int w, int h);
    void drawImage(IImage image, int x, int y);
    void setColor(int color);
    void clear(int color);

    void fillSquare(int cx, int cy, int side);
    void drawSquare(int cx, int cy, int side);
    void drawLine(int initX, int initY, int endX, int endY);
    void drawText(String text, int x, int y, int color, IFont font); //IFont font, int tamaño

    int realToLogicX(int x);
    int realToLogicY(int y);

    int logicToRealX(int x);
    int logicToRealY(int y);
    int realToScale(int s);

    int getWidthString(String text);

    //Getters
    int getWidth();
    int getWidthLogic();
    int getHeight();
    int getHeightLogic();
    int getBorderTop();

    //Setters
    void setResolution(int w, int h);
    void setFont(IFont font);

}