package com.example.libenginea;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

public class GraphicsA {
    private SurfaceView myView;
    private Paint paint;
    private Canvas canvas;

    private AssetManager mgr;

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


    GraphicsA(SurfaceView myView, int logicWidth, int logicHeight, Canvas canvas, AssetManager mgr){
        this.myView = myView;
        this.paint = new Paint();
        this.canvas = canvas;

        this.logicWidth = logicWidth;
        this.logicHeight = logicHeight;

        this.borderTop = 30; // Valor para tener un borde superior
        this.mgr = mgr;
    }

    // METODOS COLOR
    public void setColor(int colorRGB) { //CAMBIA COLOR
        colorRGB += 0xFF000000;
        int colorARGB = colorRGB + 0xFF000000;
        this.paint.setColor(colorARGB);
    }

    public void clear(int color) {  //LIMPIA PANTALLA CON COLOR
        color+= 0xFF000000;
        this.canvas.drawColor(color);
    }

    // METODOS PARA CREAR RECURSOS
    public ImageA newImage(String name) {
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

    public FontA newFont(String filename, int size, boolean isBold) {   //Crea fuente normal/negrita
        Typeface tface = Typeface.createFromAsset(mgr, filename);
        this.paint.setTypeface(tface);
        this.paint.setTextSize(size);

        if(isBold)
            this.paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        FontA fontA= new FontA(tface);
        return fontA;
    }


    // METODOS PARA DIBUJAR
    public void drawImage(ImageA image, int x, int y, int w, int h) { //Crea imagen con sus medidas
        float newW = (scaleToReal(w));
        float newH = (scaleToReal(h));
        Bitmap aux = getResizedBitmap(image.getImg(),newW ,newH);
        float left = logicToRealX(x) - (aux.getWidth()/2);
        float top = logicToRealY(y)- (aux.getHeight()/2);
        this.canvas.drawBitmap(aux,left,top,this.paint);
    }

    public void drawImage(ImageA image, int x, int y) {
        this.canvas.drawBitmap(image.getImg(),logicToRealX(x) - (scaleToReal(((ImageA)image).getImg().getWidth())/2),logicToRealX(x) - (scaleToReal(((ImageA)image).getImg().getHeight())/2),this.paint); //CAMBIAR----------------------------
    }

    public void fillSquare(int cx, int cy, int side) {
        Rect rect = new Rect(cx,cy,cx+side,cy+side);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(rect, this.paint);
    }

    public void fillRectTransformed(int x, int y, int w, int h) {
        Rect rect = new Rect
                (logicToRealX(x) - (scaleToReal(w)/2),
                logicToRealY(y) - (scaleToReal(h)/2) ,
                logicToRealX(x) - (scaleToReal(w)/2) + scaleToReal(w),
                logicToRealY(y) - (scaleToReal(h)/2) + scaleToReal(h));

        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(rect, this.paint);
    }

    public void fillRect(int x, int y, int w, int h) {
        Rect rect = new Rect(x,y,x+w,y+h);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(rect, this.paint);
    }

    public void drawSquare(int cx, int cy, int side) {
        Rect rect = new Rect(cx,cy,cx+side,cy+side);
        this.paint.setStyle(Paint.Style.STROKE);
        this.canvas.drawRect(rect, this.paint);
    }

    public void drawLine(int initX, int initY, int endX, int endY) {
        this.canvas.drawLine(initX,initY,endX,endY, this.paint);
    }

    public void drawRect(int x, int y, int width, int height) {
        Rect rect = new Rect(x,y,x+width,y+height);
        this.paint.setStyle(Paint.Style.STROKE);
        this.canvas.drawRect(rect, this.paint);
    }

    public void drawText(String text, int x, int y, int color, FontA font, float tam) {
        color += 0xFF000000;
        if(font != null)
            setFont(font);
        this.paint.setColor(color);
        this.paint.setTextSize(tam);
        this.canvas.drawText(text,x - (getWidthString(text)/2), y-(getHeightString(text)/2),this.paint);
    }

    public void drawTextTransformed(String text, int x, int y, int color, FontA font, float tam) {
        color += 0xFF000000;
        if(font != null)
            setFont(font);
        this.paint.setColor(color);
        this.paint.setTextSize(tam);
        this.canvas.drawText(text,logicToRealX(x) - (getWidthString(text)/2), logicToRealY(y),this.paint);
    }

    // CAMBIAR TAM A UNA IMAGEN
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

    //CONVERSORES DE COORDENADAS
    public int logicToRealX(int x) { return (int)(x*(float)factorScale + borderWidth); }

    public int logicToRealY(int y) {
        return (int)(y*(float)factorScale + borderHeight);
    }

    public int scaleToReal(int s) {
        return (int)(s*(factorScale));
    }


    //RENDERIZADO
    public void lockCanvas(){
        this.canvas = this.myView.getHolder().lockCanvas();
    }

    public void unlockCanvas(){
        this.myView.getHolder().unlockCanvasAndPost(this.canvas);
    }

    public void prepareFrame() {

        this.factorX = (float)getWidth() / (float)this.logicWidth;
        this.factorY = (float)getHeight() / (float)this.logicHeight;
        this.factorScale = Math.min(this.factorX, this.factorY);

        if(((float)getWidth()/(float)getHeight())<((float)2/(float)3))
        {
            this.window = (int)(this.logicWidth * this.factorX);
            int a = (int) ((getHeight() - (this.logicHeight * this.factorX)) / 2);
            this.borderHeight = a;//a; //Bordes arriba y abajo
            this.borderWidth=0;
        }
        else {
            this.window = (int)(this.logicHeight*this.factorY);
            int a = (int) ((getWidth() - (this.logicWidth * this.factorY)) / 2);
            this.borderWidth = a;//a; //Bordes Laterales
            this.borderHeight=0;
        }
    }

    //GETTERS Y SETTERS
    public int getHeight() {
        return this.myView.getHeight();
    }

    public int getBorderTop() {
        return this.borderTop;
    }

    public int getWindow() {
        return this.window;
    }

    public void setFont(FontA font) {
        this.paint.setTypeface(font.getFont());
    }

    public int getWidth() {
        return this.myView.getWidth();
    }

    public int getWidthLogic() { return this.logicWidth; }

    public int getHeightLogic() {
        return this.logicHeight;
    }

    public int getWidthString(String text) {
        return (int)this.paint.measureText(text,0,text.length());
    }

    public int getHeightString(String text) {
        Rect bounds = new Rect();
        this.paint.getTextBounds(text,0,text.length(), bounds);
        return bounds.height();
    }

    public float getFactorScale() {
        return factorScale;
    }

    public void setLogicWidth(int logicWidth) {
        this.logicWidth = logicWidth;
    }

    public void setLogicHeight(int logicHeight) {
        this.logicHeight = logicHeight;
    }
}