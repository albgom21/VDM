package gdv.ucm.liblogica;


import java.awt.Color;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInterface;

public class Circulito implements IInterface {

    private int x;
    private int y;
    private int side;
    private int speed;

    private Color color;

    private int maxX;
    public Circulito(int x, int y, int r, int speed, int maxX){
        this.x=x;
        this.y=y;
        this.side = r;
        this.speed = speed;
        this.maxX = maxX;
    }


    @Override
    public void render(IGraphics g) {
        g.setColor(this.color);
        g.drawSquare(this.x,this.y,this.side);
    }

    @Override
    public void update(Double deltaTime) {
        int maxX = this.maxX-this.side;

        this.x += this.speed * deltaTime;
        this.y += 2*deltaTime;
        while(this.x < 0 || this.x > maxX-this.side) {
            // Vamos a pintar fuera de la pantalla. Rectificamos.
            if (this.x < 0) {
                // Nos salimos por la izquierda. Rebotamos.
                this.x = -this.x;
                this.speed *= -1;
            } else if (this.x > maxX-this.side) {
                // Nos salimos por la derecha. Rebotamos
                this.x = 2 * (maxX-this.side) - this.x;
                this.speed *= -1;
            }
        }
    }
}
