package gdv.ucm.libenginepc;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
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
    private Graphics2D graphics2D;

    GraphicsPC(JFrame myView,Graphics2D graphics2D){
        this.myView = myView;
        this.graphics2D = graphics2D;
    }

    @Override
    public int getHeight() {
        return this.myView.getHeight();
    }

    @Override
    public void setResolution(int w, int h) {
        this.myView.setSize(w, h);
    }

    @Override
    public void setFont(IFont font) {
        this.graphics2D.setFont((Font) font); // REVISAR
    }


    @Override
    public int getWidth() {
        return this.myView.getWidth();
    }

    @Override
    public IImage newImage(String name) {
        Image img = null;
        try {
            img = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImagePC imgPC = new ImagePC(img);
        return imgPC;
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold) {
        InputStream is = null;
        Font font = null;
        try {
            is = new FileInputStream(filename);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.BOLD, size);
        FontPC fontPC= new FontPC(font);
        return fontPC;
    }

    @Override
    public void translate(int x, int y) {
        this.graphics2D.translate(x,y); //REVISAR
    }

    @Override
    public void scale(double x, double y) {
        this.graphics2D.scale(x,y); //REVISAR
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
        this.graphics2D.drawImage((Image) image,x,y,null);
    }

    @Override
    public void setColor(Color color) {
        this.graphics2D.setColor(color);
    }

    @Override
    public void clear(Color color) {
        this.graphics2D.setColor(color);
        this.graphics2D.fillRect(0,0, this.getWidth(), this.getHeight());
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
        this.graphics2D.drawLine(initX,initY,endX,endY); //REVISAR
    }

    @Override
    public void drawText(String text, int x, int y) {
        this.graphics2D.drawString(text , x, y);
    }
}
