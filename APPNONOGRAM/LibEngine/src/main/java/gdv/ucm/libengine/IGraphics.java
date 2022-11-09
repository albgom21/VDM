package gdv.ucm.libengine;

public interface IGraphics {
    //Crear recursos (fuentes, imagenes)
    IImage newImage(String ruta);
    IFont newFont(String filename, int size, boolean isBold);

    void drawImage(IImage image, int x, int y, int w, int h);
    void drawImage(IImage image, int x, int y);
    void setColor(int color);
    void clear(int color);

    void fillSquare(int cx, int cy, int side);
    void fillRect(int x, int y, int w, int h);
    void drawSquare(int cx, int cy, int side);
    void drawLine(int initX, int initY, int endX, int endY);
    void drawText(String text, int x, int y, int color, IFont font, float tam); //IFont font, int tamaño
    void drawRect(int x, int y, int width, int height);

    int realToLogicX(int x);
    int realToLogicY(int y);

    int logicToRealX(int x);
    int logicToRealY(int y);
    int scaleToReal(int s);

    int getWidthString(String text);
    int getHeightString(String text);

    //Getters
    int getWidth();
    int getWidthLogic();
    int getHeight();
    int getHeightLogic();
    int getBorderTop();
    int getWindow();

    //Setters
    void setResolution(int w, int h);
    void setFont(IFont font);

}