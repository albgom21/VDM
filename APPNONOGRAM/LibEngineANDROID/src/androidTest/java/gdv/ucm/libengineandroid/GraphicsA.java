package gdv.ucm.libengineandroid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import gdv.ucm.libengine.IFont;
import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IImage;

public class GraphicsA implements IGraphics {
    private SurfaceView myView;
    private Paint paint;

    GraphicsA(SurfaceView myView, Paint paint){
        this.myView = myView;
        this.paint = paint;
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
        this.paint.setFont((Font) font); // REVISAR
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
        this.graphics2D.drawLine(initX,initY,endX,endY);
    }

    @Override
    public void drawText(String text, int x, int y) {
        this.graphics2D.drawString(text , x, y);
    }
}
