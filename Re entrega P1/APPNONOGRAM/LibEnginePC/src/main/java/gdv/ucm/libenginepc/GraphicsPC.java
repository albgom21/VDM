package gdv.ucm.libenginepc;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;

public class GraphicsPC implements IGraphics {
    private JFrame myView;
    private BufferStrategy bufferStrategy;

    private FontPC font;
    private Graphics2D graphics2D;

    // Tam logico
    private int logicWidth;
    private int logicHeight;

    // Medidas de bordes
    private int borderWidth;
    private int borderHeight;
    private int borderTop;

    private int window;

    // Factores de escala
    private float factorScale;
    private float factorX;
    private float factorY;

    GraphicsPC(JFrame myView, int logicWidth, int logicHeight){
        this.myView = myView;
        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                this.myView.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        this.bufferStrategy = this.myView.getBufferStrategy();
        this.graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();

        this.logicWidth = logicWidth;
        this.logicHeight = logicHeight;
        setResolution(this.logicWidth,this.logicHeight);

        this.factorX = (float)getWidth() / (float)this.logicWidth;
        this.factorY = (float)getHeight() / (float)this.logicHeight;
        this.factorScale = Math.min(this.factorX, this.factorY);

        // Establecer bordes
        if(((float)getWidth()/(float)getHeight())<((float)2/(float)3))
        {
            this.window = (int)(this.logicWidth * this.factorX);
            int a = (int) ((getHeight() - this.window) / 2);
            this.borderHeight = a; //Bordes arriba y abajo
        }
        else {
            this.window = (int)(this.logicWidth*this.factorY);
            int a = (int) ((getWidth() - this.window) / 2);
            this.borderWidth = a; //Bordes Laterales
        }
        this.borderTop = this.myView.getInsets().top;            // TOMANDO EL INSET SUPERIOR
    }

    // METODOS COLOR
    @Override
    public void setColor(int color) { this.graphics2D.setColor(new Color(color)); } // CAMBIA COLOR

    @Override
    public void clear(int color) {                                       //LIMPIA PANTALLA CON COLOR
        this.graphics2D.setColor(new Color(color));
        this.graphics2D.fillRect(0,0, this.getWidth(), this.getHeight());
        this.graphics2D.setPaintMode();
    }

    //METODOS PARA GESTION DE FRAME
    public void show() { this.bufferStrategy.show(); }           // MUESTRA EL BUFFER STRATEGY

    public void prepareFrame() {                                // ACTUALIZA LA NUEVA RESOLUCION EN CADA FRAME
        setResolution(getWidth(),getHeight());
        this.graphics2D = (Graphics2D)this.bufferStrategy.getDrawGraphics();
    }

    public void finishFrame() {
        this.graphics2D.dispose();
    }    // LIBERA EL GRAPHICS

    public boolean cambioBuffer(){                              // CAMBIA EL BUFFER
        if(bufferStrategy.contentsRestored()){
            return false; // se ha restaurado en algun momento el bufer
        }
        return !this.bufferStrategy.contentsLost();
    }

    // METODOS PARA CREAR RECURSOS
    @Override
    public IImage newImage(String filename) { //ruta nombreproyecto/  //CREA UNA NUEVA IMAGEN

        Image img = null;
        try {
            img = ImageIO.read(new File("data/"+filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImagePC imgPC = new ImagePC(img);
        return imgPC; //"/data/start.png"
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold) {   //CREA UNA NUEVA FUENTE
        InputStream is = null;
        Font font = null;
        try {
            is = new FileInputStream("data/" +filename);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isBold)
            font = font.deriveFont(Font.BOLD, size);

        font = font.deriveFont(font.getStyle(), size);
        FontPC fontPC= new FontPC(font);
        this.font = fontPC;

        return fontPC;
    }

    // METODOS PARA DIBUJAR
    @Override
    public void drawImage(IImage image, int x, int y, int w, int h) {       //DIBUJA LA IMAGEN CON POSICION Y TAMAÑO
        this.graphics2D.drawImage(((ImagePC) image).getImg(),
                logicToRealX(x) - (scaleToReal(w)/2),logicToRealY(y) - (scaleToReal(h)/2) + borderTop,
                (scaleToReal(w)),(scaleToReal(h)),null);
    }

    public void drawImage(IImage image, int x, int y) {     //DIBUJA LA IMAGEN CON UNICAMENTE LA POSICION
        this.graphics2D.drawImage(((ImagePC) image).getImg(),logicToRealX(x),logicToRealY(y) + borderTop,null);
    }

    @Override                                               //DIBUJA CUADRADO RELLENO
    public void fillSquare(int cx, int cy, int side) {      //RELLENAR CUADRADO
        this.graphics2D.fillRect(cx,cy + borderTop,side,side);
    }

    @Override                                               // DIBUJA RECTANGULO RELLENO
    public void fillRect(int x, int y, int w, int h) {      //RELLENAR RECTANGULO
        this.graphics2D.fillRect(x,y + borderTop,w,h);
    }

    @Override
    public void drawSquare(int cx, int cy, int side) {      //DIBUJA CUADRADO
        this.graphics2D.drawRect(cx,cy + borderTop,side,side);
        this.graphics2D.setPaintMode();
    }

    @Override                                                //DIBUJA LINEA
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.graphics2D.drawLine(initX,initY + borderTop,endX,endY + borderTop);
    }

    @Override                                                //DIBUJA TEXTO
    public void drawText(String text, int x, int y, int color,IFont font, float tam) {
        FontPC f2 = null;
        if(tam!=-1) {
            Font f = this.font.getFont().deriveFont(tam);
            f2 = new FontPC(f);
        }
        if(font != null)
            setFont(font);
        else if(f2 != null)
            setFont(f2);
        this.graphics2D.setColor(new Color (color));
        this.graphics2D.drawString(text, x - (getWidthString(text)/2), y - (getHeightString(text)/2) + borderTop);
    }

    @Override                                               //DIBUJA RECTANGULO
    public void drawRect(int x, int y, int width, int height) {
        this.graphics2D.drawRect(x,y + borderTop, width,height);
    }

    //CONVERSORES DE COORDENADAS
    @Override
    public int logicToRealX(int x) { return (int)(x*factorScale + borderWidth); }

    @Override
    public int logicToRealY(int y) {        //CONVERSOR DE TAMAÑO LOGICO A REAL EN Y
        return (int)(y*factorScale + borderHeight);
    }

    @Override
    public int scaleToReal(int s) {
        return (int)(s*(factorScale));
    }

    //GETTERS
    @Override
    public int getWidth() { return this.myView.getWidth();}      // ANCHO DE LA VENTANA
    @Override
    public int getHeight() {
        return this.myView.getHeight();
    }   // ALTO DE LA VENTANA

    @Override
    public int getHeightLogic() { return this.logicHeight; }     // ALTURA LOGICA

    @Override
    public int getWidthLogic() { return this.logicWidth; }       //ANCHO LOGICO

    @Override
    public int getBorderTop() {
        return this.borderTop;
    }         // BORDE SUPERIOR


    @Override                                                    // ANCHO DE UNA CADENA DE TEXTO
    public int getWidthString(String text) {
        return (int)this.graphics2D.getFont().getStringBounds(text,this.graphics2D.getFontRenderContext()).getWidth();
    }

    @Override                                                    // ALTO DE UNA CADENA DE TEXTO
    public int getHeightString(String text) {
        return (int)this.graphics2D.getFont().getStringBounds(text,this.graphics2D.getFontRenderContext()).getHeight();
    }

    @Override
    public int getWindow() {
        return window;
    }                   //VENTANA

    //SETTERS
    @Override
    public void setResolution(int w, int h) {                    // ACTUALIZA LA RESOLUCION
        this.myView.setSize(w, h);

        this.factorX = (float)w / (float)this.logicWidth;
        this.factorY = (float)h / (float)this.logicHeight;
        this.factorScale = Math.min(this.factorX, this.factorY);

        //DEPENDIENDO DE LA RESOLUCION DE LA VENTANA SE CREAN LOS BORDES POR ARRIBA Y ABAJO O LOS LADOS
        if(((float)getWidth()/(float)getHeight())<((float)2/(float)3))
        {
            this.window = (int)(this.logicWidth * this.factorX);
            int a = (int) ((getHeight() - (this.logicHeight * this.factorX)) / 2);
            this.borderHeight = a; //Bordes arriba y abajo
            this.borderWidth=0;
        }
        else {
            this.window = (int)(this.logicWidth*this.factorY);
            int a = (int) ((getWidth() - (this.logicWidth * this.factorY)) / 2);
            this.borderWidth = a; //Bordes Laterales
            this.borderHeight=0;
        }
    }

    @Override
    public void setFont(IFont font) {                           // PONE UNA FUENTE
        this.font = (FontPC)font;
        this.graphics2D.setFont(((FontPC) font).getFont());
    }
}