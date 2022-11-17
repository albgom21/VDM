package gdv.ucm.appnonogram;


import com.example.libenginea.GraphicsA;

public class RenderCell {

    public void render(GraphicsA gr, Cell c) {
        int side = (int)(((gr.getWindow()/3)*2)/c.getMedia());
        int sep = side/8;

        int tr_x = gr.logicToRealX(gr.getWidthLogic()/2) // mitad de la pantalla
                - (int)((c.getOffsetX()/2) * (side))        // mitad casillas izq
                - (int)((c.getOffsetX()/2 - 1) * sep)       // mitad offsets (uno menos que las casillas)
                + sep*3                                     // offset
                + (c.getX()*((side)+(sep)));                // pos de cada casilla

        int tr_y = gr.logicToRealY(gr.getHeightLogic()/2) // mitad pantalla
                - ((int)((c.getOffsetY()/2)*(side + sep)))   // mitad casillas arriba
                + (c.getY()*(side+sep));                     // pos de cada casilla

        c.setTr_x(tr_x);
        c.setTr_y(tr_y);
        c.setSide(side);
        c.setSeparacion(sep);

        int color;
        if(c.getState().equals(CellState.GRAY))
            color=0x7f7a7a;
        else if(c.getState().equals(CellState.BLUE))
            color=0x5b6ee1;
        else if(c.getState().equals(CellState.RED))
            color=0xac3232;
        else //WHITE
            color=0xececec;
        gr.setColor(color);

        if(!c.getState().equals(CellState.NORENDER)) { // Si se renderiza
            gr.fillSquare((int)tr_x - (side/2), (int)tr_y - (side/2), side);
            if (c.getState().equals(CellState.WHITE)) { // Celda blanca especial
                gr.setColor(0x000000);
                gr.drawSquare((int)tr_x - (int)((side/2)), (int)tr_y - (int)(side/2), side);
                gr.drawLine((int)tr_x - (side/2), (int)tr_y - (side/2), (int)tr_x + (side/2), (int)tr_y + (side/2));
            }
        }
    }
}
