package gdv.ucm.liblogica;


import java.awt.Color;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInterface;

public class Circulito implements IInterface {

    private float x;
    private float y;
    private int side;
    private int speed;

    private Color color;

    private int maxX;
    public Circulito(int x, int y, int side, int speed, int maxX, Color color){
        this.x=x;
        this.y=y;
        this.side = side;
        this.speed = speed;
        this.maxX = maxX;
        this.color = color;
    }


    @Override
    public void render(IGraphics g) {
        g.setColor(this.color);

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
              g.fillSquare((int)this.x +(i*52),(int)this.y+(j*52),this.side);
            }
        }

    }

    @Override
    public void update(Double deltaTime) {

    }
}
