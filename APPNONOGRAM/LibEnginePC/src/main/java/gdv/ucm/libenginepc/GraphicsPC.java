package gdv.ucm.libenginepc;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
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
    private EnginePC engine;
    private BufferStrategy bufferStrategy;

    private IFont font;
    private Graphics2D graphics2D;

    public int logicWidth;
    public int logicHeight;

    private int borderTop;

    private float factorScale;
    private float factorX;
    private float factorY;

    private int ofx;
    private int ofy;

    GraphicsPC(JFrame myView, EnginePC engine){
        this.myView = myView;
        this.engine = engine;
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

        this.logicWidth = 400;
        this.logicHeight = 600;
        setResolution(this.logicWidth,this.logicHeight);

        this.factorX = (float)getWidth() / (float)this.logicWidth;
        this.factorY = (float)getHeight() / (float)this.logicHeight;
        this.factorScale = Math.min(this.factorX, this.factorY);
        this.borderTop = 35;
    }

    public BufferStrategy getbufferStrategy() {
        return this.bufferStrategy;
    }

    @Override
    public int getWidth() { return this.myView.getWidth();}
    @Override
    public int getHeight() {
        return this.myView.getHeight();
    }

    @Override
    public int getHeightLogic() { return this.logicHeight; }

    @Override
    public int getBorderTop() {
        return this.borderTop;
    }

    @Override
    public void setResolution(int w, int h) {
        this.myView.setSize(w, h);

        this.factorX = (float)w / (float)this.logicWidth;
        this.factorY = (float)h / (float)this.logicHeight;
        this.factorScale = Math.min(this.factorX, this.factorY);
//        this.borderTop = h - this.myView.getContentPane().getHeight();
    }

    @Override
    public void setFont(IFont font) {
        this.font = font;
        this.graphics2D.setFont(((FontPC) font).getFont()); // REVISAR
    }

    public void prepareFrame() {
        setResolution(getWidth(),getHeight());

        this.graphics2D = (Graphics2D)this.bufferStrategy.getDrawGraphics();
//        this.myView.scale();
//        this.myView.translate();
        //this.clear(0xFFFFFF);
    }

    public void finishFrame() {
        this.graphics2D.dispose();
    }

    public boolean cambioBuffer(){
        if(bufferStrategy.contentsRestored()){
            return false; // se ha restaurado en algun momento el bufer
        }
        return !this.bufferStrategy.contentsLost();
    }



    @Override
    public int getWidthLogic() { return this.logicWidth; }

    @Override
    public IImage newImage(String filename) { //ruta nombreproyecto/data

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
    public IFont newFont(String filename, int size, boolean isBold) {
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

        return fontPC;
    }

    @Override
    public void translate(int x, int y) {
        this.graphics2D.translate(x,y); //REVISAR
    }

    @Override
    public void scale(float x, float y) {
        this.graphics2D.scale(x,y); //REVISAR
    }

    @Override
    public void setOffsetX(int ofx) {
        this.ofx = ofx;
    }

    @Override
    public void setOffsetY(int ofy) {
        this.ofy = ofy;
    }

    @Override
    public int getOffsetX() {
        return this.ofx;
    }

    @Override
    public int getOffsetY() {
        return this.ofy;
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
    public void drawImage(IImage image, int x, int y, int w, int h) {
        this.graphics2D.drawImage(((ImagePC) image).getImg(),
                                logicToRealX(x) - (scaleToReal(w)/2),logicToRealY(y)- (scaleToReal(h)/2),
                                   (scaleToReal(w)),(scaleToReal(h)),null);
    }

    public void drawImage(IImage image, int x, int y) {
        this.graphics2D.drawImage(((ImagePC) image).getImg(),logicToRealX(x),logicToRealY(y),null); //(int) w, (int)h
    }

    @Override
    public void setColor(int color) {
        this.graphics2D.setColor(new Color(color)); //new Color
    }

    @Override
    public void clear(int color) {
        this.graphics2D.setColor(new Color(color)); //new Color
        this.graphics2D.fillRect(0,0, this.getWidth(), this.getHeight());
        this.graphics2D.setPaintMode();
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {
        this.graphics2D.fillRect(cx,cy,side,side);
    }

    @Override
    public void drawSquare(int cx, int cy, int side) {
        this.graphics2D.drawRect(cx,cy,side,side);
        this.graphics2D.setPaintMode();
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.graphics2D.drawLine(initX,initY,endX,endY);
    }

    @Override
    public void drawText(String text, int x, int y, int color,IFont font) {
        if(font != null)
            setFont(font);
        else
            setFont(this.font);

        this.graphics2D.setColor(new Color (color));
        this.graphics2D.drawString(text, x - (getWidthString(text)/2), y - (getHeightString(text)/2));
    }

    @Override
    public int realToLogicX(int x) {
        return (int)(x*(getWidth()*this.logicWidth));
    }

    @Override
    public int realToLogicY(int y) {
        return (int)(y*(getHeight()*this.logicHeight));
    }

    @Override
    public int logicToRealX(int x) { return (int)(x*(float)factorX); }

    @Override
    public int logicToRealY(int y) {
        return (int)(y*(float)factorY);
    }

    @Override
    public int scaleToReal(int s) {
        return (int)(s*(float)(factorScale));
    }

    @Override
    public int getWidthString(String text) {
        return (int)this.graphics2D.getFont().getStringBounds(text,this.graphics2D.getFontRenderContext()).getWidth();
    }

    @Override
    public int getHeightString(String text) {
        return (int)this.graphics2D.getFont().getStringBounds(text,this.graphics2D.getFontRenderContext()).getHeight();
    }
}
