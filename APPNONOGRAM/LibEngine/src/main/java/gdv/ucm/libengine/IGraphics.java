package gdv.ucm.libengine;

public interface IGraphics {
    // Crear recursos (fuentes, imagenes)
    IFont newFont(String filename, int size, boolean isBold);
    IImage newImage(String ruta);

    // Dibujar una imagen
    void drawImage(IImage image, int x, int y, int w, int h);
    void drawImage(IImage image, int x, int y);

    // Cambiar el color con el que se pintan
    void setColor(int color);

    // Limpiar la pantalla con un color
    void clear(int color);

    // Dibujar cuadrados y rectángulos rellenos
    void fillSquare(int cx, int cy, int side);
    void fillRect(int x, int y, int w, int h);

    // Dibujar cuadrados y rectángulos borde
    void drawSquare(int cx, int cy, int side);
    void drawRect(int x, int y, int width, int height);

    // Dibujar línea
    void drawLine(int initX, int initY, int endX, int endY);

    // Dibujar texto
    void drawText(String text, int x, int y, int color, IFont font, float tam);

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