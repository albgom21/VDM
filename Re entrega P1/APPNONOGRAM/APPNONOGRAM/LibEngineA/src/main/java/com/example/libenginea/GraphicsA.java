package com.example.libenginea;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;

public class GraphicsA implements IGraphics {
    private SurfaceView myView;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder holder;
    private AssetManager mgr;

    private int logicWidth;
    private int logicHeight;

    private int borderWidth;
    private int borderHeight;

    private int borderTop;

    private int window;

    private float factorScale;
    private float factorX;
    private float factorY;

    GraphicsA(SurfaceView myView, AssetManager mgr, int logicWidth, int logicHeight){
        this.myView = myView;
        this.paint = new Paint();
        this.canvas = new Canvas();
        this.holder = this.myView.getHolder();
        this.mgr = mgr;

        this.logicWidth = logicWidth;
        this.logicHeight = logicHeight;

        this.borderTop = 31;
    }

    public boolean isValid() { return this.holder.getSurface().isValid();}

    @Override
    public int getHeight() {
        return this.myView.getHeight();
    }       //ALTURA VENTANA

    @Override
    public int getBorderTop() {
        return this.borderTop;
    }       //BORDE SUPERIOR

    @Override
    public int getWindow() {
        return this.window;
    }       //VENTANA

    @Override
    public void setResolution(int w, int h) { //no deberia llamarse en Android
        this.myView.getHolder().setFixedSize(w,h);
    }       //CAMBIA LA RESOLUCION

    @Override
    public void setFont(IFont font) {           //CAMBIA LA FUENTE
        this.paint.setTypeface(((FontA) font).getFont());
    }

    @Override
    public int getWidth() {     //ANCHO VENTANA
        return this.myView.getWidth();
    }

    @Override
    public int getWidthLogic() {        //ANCHO LOGICO
        return this.logicWidth;
    }
    @Override
    public int getHeightLogic() {       //ALTURA LOGICA
        return this.logicHeight;
    }

    @Override
    public IImage newImage(String name) {       //NUEVA IMAGEN
        Bitmap bitmap = null;
        try {
            InputStream is = this.mgr.open(name);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        ImageA imgA = new ImageA(bitmap);
        return imgA;
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold) {       //NUEVA FUENTE
        Typeface tface = Typeface.createFromAsset(mgr, filename);
        this.paint.setTypeface(tface);
        this.paint.setTextSize(size);

        if(isBold)
            this.paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        FontA fontA= new FontA(tface);
        return fontA;
    }

    public Bitmap getResizedBitmap(Bitmap bm, float newWidth, float newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    @Override
    public void drawImage(IImage image, int x, int y, int w, int h) {       //DIBUJA IMAGEN CON POSICION Y TAMAÑO
        float newW = (scaleToReal(w));
        float newH = (scaleToReal(h));
        Bitmap aux = getResizedBitmap(((ImageA)image).getImg(),newW ,newH);
        this.canvas.drawBitmap(aux,logicToRealX(x) - (aux.getWidth()/2),logicToRealY(y)- (aux.getHeight()/2),this.paint);
    }

    @Override
    public void drawImage(IImage image, int x, int y) {     //DIBUJA IMAGEN CON POSICION
        this.canvas.drawBitmap(((ImageA)image).getImg(),logicToRealX(x) - (scaleToReal(((ImageA)image).getImg().getWidth())/2),logicToRealX(x) - (scaleToReal(((ImageA)image).getImg().getHeight())/2),this.paint); //CAMBIAR----------------------------
    }

    @Override
    public void setColor(int colorRGB) {        //CAMBIA COLOR
        colorRGB += 0xFF000000;
        int colorARGB = colorRGB + 0xFF000000;
        this.paint.setColor(colorARGB);
    }

    @Override
    public void clear(int color) {      //LIMPIA COLOR
        color+= 0xFF000000;
        this.canvas.drawColor(color);
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {      //RELLENA CUADRADO
        Rect rect = new Rect(cx,cy,cx+side,cy+side);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(rect, this.paint);
    }

    @Override
    public void fillRect(int x, int y, int w, int h) {      //RELLENA RECTANGULO VARIABLE
        Rect rect = new Rect(x,y,x+w,y+h);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(rect, this.paint);
    }

    @Override
    public void drawSquare(int cx, int cy, int side) {      //DIBUJA CUADRADO
        Rect rect = new Rect(cx,cy,cx+side,cy+side);
        this.paint.setStyle(Paint.Style.STROKE);
        this.canvas.drawRect(rect, this.paint);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {        //DIBUJA LINEA
        this.canvas.drawLine(initX,initY,endX,endY, this.paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {     //DIBUJA RECTANGULO VARIABLE
        Rect rect = new Rect(x,y,x+width,y+height);
        this.paint.setStyle(Paint.Style.STROKE);
        this.canvas.drawRect(rect, this.paint);
    }

    @Override
    public void drawText(String text, int x, int y, int color, IFont font, float tam) {     //DIBUJA TEXTO
        color += 0xFF000000;
        if(font != null)
            setFont(font);
        this.paint.setColor(color);
        this.paint.setTextSize(tam);
        this.canvas.drawText(text,x - (getWidthString(text)/2), y-(getHeightString(text)/2),this.paint);
    }

    @Override
    public int logicToRealX(int x) { return (int)(x*(float)factorScale + borderWidth); }            //CONVERSOR DE LOGICO A REAL EN X

    @Override
    public int logicToRealY(int y) {            //CONVERSOR DE LOGICO A REAL EN Y
        return (int)(y*(float)factorScale + borderHeight);
    }

    @Override
    public int scaleToReal(int s) {     //CONVERSOR DE ESCALA A REAL
        return (int)(s*(factorScale));
    }

    @Override
    public int getWidthString(String text) {
       return (int)this.paint.measureText(text,0,text.length());
    }

    @Override
    public int getHeightString(String text) {
        Rect bounds = new Rect();
        this.paint.getTextBounds(text,0,text.length(), bounds);
        return bounds.height();
    }

    public void lockCanvas(){       //BLOQUEA CANVAS
        this.canvas = this.myView.getHolder().lockCanvas();
    }
    public void unlockCanvas(){     //DESBLOQUEA CANVAS
        this.myView.getHolder().unlockCanvasAndPost(this.canvas);
    }

    public void prepareFrame() {        //PREPARA EL NUEVO FRAME

        this.factorX = (float)getWidth() / (float)this.logicWidth;
        this.factorY = (float)getHeight() / (float)this.logicHeight;
        this.factorScale = Math.min(this.factorX, this.factorY);

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
}