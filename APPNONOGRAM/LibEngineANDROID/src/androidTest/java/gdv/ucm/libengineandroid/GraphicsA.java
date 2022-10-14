package gdv.ucm.libengineandroid;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.media.Image;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import gdv.ucm.libengine.IColor;
import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;

public class GraphicsA implements IGraphics {
    private SurfaceView myView;
    private Paint paint;
    private Canvas canvas;
    private AssetManager mgr;

    GraphicsA(SurfaceView myView, Paint paint){
        this.myView = myView;
        this.paint = paint;
        //this.mgr = getAssets();
    }

    @Override
    public int getHeight() {
        return this.myView.getHeight();
    }

    @Override
    public void setResolution(int w, int h) {
        this.myView.getHolder().setFixedSize(w,h); //No seguros si equivale a setSize
    }

    @Override
    public void setFont(IFont font) {
        //this.paint.setFont((Font) font); // REVISAR
    }


    @Override
    public int getWidth() {
        return this.myView.getWidth();
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
    public void scale(double x, double y) {
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

    @Override
    public void drawImage(IImage image, int x, int y) {
        //Paint paintTemp = new Paint();
        //this.canvas.drawBitmap((Bitmap) image,x,y,paintTemp);
    }

    @Override
    public void setColor(IColor color) {
        this.paint.setColor(getColor(color));
    }

    int getColor(IColor color)
    {
        if(color == IColor.GRAY)
            return 0xDCDCDC;
        else if(color == IColor.WHITE)
            return 0xFFFFFF;
        else if(color == IColor.BLACK)
            return 0x000000;
        else if(color == IColor.BLUE)
            return 0x0000FF;
        else //RED
            return 0xFF0000;
    }

    @Override
    public void clear(IColor color) {
        this.paint.setColor(getColor(color));
        Rect rect = new Rect(0,0, this.getWidth(), this.getHeight());
        this.canvas.drawRect(rect,this.paint);
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {
        Rect rect = new Rect(cx,cy,side,side);
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
    public void drawText(String text, int x, int y) {
        this.canvas.drawText(text,x,y,this.paint);
    }
}
