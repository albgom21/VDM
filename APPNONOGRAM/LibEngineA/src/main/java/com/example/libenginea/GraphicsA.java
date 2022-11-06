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

import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;

public class GraphicsA implements IGraphics {
    private SurfaceView myView;
//    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;
    private AssetManager mgr;

    GraphicsA(SurfaceView myView, Canvas canvas){
        this.myView = myView;
//        this.holder = this.myView.getHolder();
        this.paint = new Paint();
//        this.paint.setColor(0xFFFFFF);
        this.canvas = canvas;

    }

    public void setAssetManager(AssetManager mgr){
        this.mgr = mgr;
    }

    @Override
    public int getHeight() {
        return this.myView.getHeight();
    }

    @Override
    public int getHeightLogic() {
        return 0;
    }

    @Override
    public void setResolution(int w, int h) {
        this.myView.getHolder().setFixedSize(w,h); //No seguros si equivale a setSize
    }

    @Override
    public void setFont(IFont font) {
        this.paint.setTypeface(((FontA)font).getFont());
    }


    @Override
    public int getWidth() {
        return this.myView.getWidth();
    }

    @Override
    public int getWidthLogic() {
        return 0;
    }

    @Override
    public IImage newImage(String name) {
        Bitmap bitmap = null;
        try {
            InputStream is = this.mgr.open(name);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        //canvas = holder.lockCanvas();
        ImageA imgA = new ImageA(bitmap);
        return imgA;
        //Image img = null;
        //try {
        //    img = ImageIO.read(new File(name));
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //ImagePC imgPC = new ImagePC(img);
        //return imgPC;
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold) {
        Typeface tface = Typeface.createFromAsset(mgr, filename);
        //Paint paint = new Paint();
        this.paint.setTypeface(tface);
        this.paint.setTextSize(size);

        if(isBold)
            this.paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        FontA fontA= new FontA(tface);
        return fontA;
    }

    @Override
    public void translate(int x, int y) {
        //this.graphics2D.translate(x,y); //REVISAR
    }

    @Override
    public void scale(float x, float y) {
        //this.graphics2D.scale(x,y); //REVISAR
    }

    @Override
    public void save() {
        //COMPLETAR
    }

    @Override
    public void restore() {
        //COMPLETAR
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
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
    public void drawImage(IImage image, int x, int y, int w, int h) {
        Paint paintTemp = new Paint();
        this.canvas.drawBitmap(getResizedBitmap(((ImageA)image).getImg(),w,h),x,y,paintTemp);
    }

    @Override
    public void drawImage(IImage image, int x, int y) {
        Paint paintTemp = new Paint();
        this.canvas.drawBitmap(((ImageA)image).getImg(),x,y,paintTemp);
    }

    @Override
    public void setColor(int colorRGB) {
        colorRGB += 0xFF000000;
        int colorARGB = colorRGB + 0xFF000000;
        this.paint.setColor(colorARGB);
    }

//    int getColor(IColor color) { //ARGB
//        if(color == IColor.GRAY)
//            return 0xFFDCDCDC;
//        else if(color == IColor.WHITE)
//            return 0xFFFFFFFF;
//        else if(color == IColor.BLACK)
//            return 0xFF000000;
//        else if(color == IColor.BLUE)
//            return 0xFF0000FF;
//        else //RED
//            return 0xFFFF0000;
//    }


    @Override
    public void clear(int color) {
        this.canvas.drawColor(color);
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {
        Rect rect = new Rect(cx,cy,cx+side,cy+side);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawRect(rect, this.paint);
    }

    @Override
    public void drawSquare(int cx, int cy, int side) {
        Rect rect = new Rect(cx,cy,side,side);
        this.paint.setStyle(Paint.Style.STROKE);
        this.canvas.drawRect(rect, this.paint);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.canvas.drawLine(initX,initY,endX,endY, this.paint);
    }

    @Override
    public void drawText(String text, int x, int y, int color) {
        color += 0xFF000000;
        int colorARGB = color + 0xFF000000;
        this.paint.setColor(colorARGB);

        this.canvas.drawText(text,x,y,this.paint);
    }

    @Override
    public int realToLogicX(int x) {
        return 0;
    }

    @Override
    public int realToLogicY(int y) {
        return 0;
    }

    @Override
    public int getWidthString(String text) {
       return (int)this.paint.measureText(text,0,text.length());
    }

    public void lockCanvas(){
        this.canvas = this.myView.getHolder().lockCanvas();
    }
    public void unlockCanvas(){
        this.myView.getHolder().unlockCanvasAndPost(this.canvas);
    }
}
