package gdv.ucm.appnonogram.RENDER;

import android.content.res.Configuration;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;

import gdv.ucm.appnonogram.LOGIC.Cell;
import gdv.ucm.appnonogram.LOGIC.CellState;

public class RenderCell {
    public void render(GraphicsA gr, Cell c, EngineA engine) {
        int side;
        if(engine.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)    //dependiendo de la orientacion
            side = (int)(((gr.getWindow()/4)*2)/c.getMedia());
        else
            side = (int)(((gr.getWindow()/3)*2)/c.getMedia());

        int sep = side/10;

        int tr_x;
        int tr_y;

        tr_x = gr.logicToRealX(gr.getWidthLogic() / 2) // mitad de la pantalla
                - (int) ((c.getOffsetX() / 2) * (side))        // mitad casillas izq
                - (int) ((c.getOffsetX() / 2 - 1) * sep)       // mitad offsets (uno menos que las casillas)
                + sep * 3                                     // offset
                + (c.getX() * ((side) + (sep)));                // pos de cada casilla

        tr_y = gr.logicToRealY(gr.getHeightLogic() / 2) // mitad pantalla
                - ((int) ((c.getOffsetY() / 2) * (side + sep)))   // mitad casillas arriba
                + sep * 3                                      // offset
                + (c.getY() * (side + sep));                     // pos de cada casilla

        c.setTr_x(tr_x);
        c.setTr_y(tr_y);
        c.setSide(side);
        c.setSeparacion(sep);

        int color = 0xffffff;
        if(c.getState().equals(CellState.GRAY)){
            if(engine.getStats().getPaleta() == 0)
                color=0x7f7a7a; //paleta normal
            else if(engine.getStats().getPaleta() == 1)
                color=0x687d88; //paleta 1
            else if(engine.getStats().getPaleta() == 2)
                color=0x81617a; //paleta 2
            else if(engine.getStats().getPaleta() == 3)
                color=0x4e5951; //paleta 3
        }

        else if(c.getState().equals(CellState.BLUE)){
            if(engine.getStats().getPaleta() == 0)
                color=0x5b6ee1; //paleta normal
            else if(engine.getStats().getPaleta() == 1)
                color=0x3399; //paleta 1
            else if(engine.getStats().getPaleta() == 2)
                color=0xa4c897; //paleta 2
            else if(engine.getStats().getPaleta() == 3)
                 color=0x339966; //paleta 3
        }
        else if(c.getState().equals(CellState.RED)) {
            if (engine.getStats().getPaleta() == 0)
                color = 0xac3232;  //paleta normal
            else if (engine.getStats().getPaleta() == 1)
                color=0xee736d;  //paleta 1
            else if (engine.getStats().getPaleta() == 2)
                color=0xec5592;  //paleta 2
            else if (engine.getStats().getPaleta() == 3)
                color = 0x6f003f;  //paleta 3
        }
        else { //WHITE
            if (engine.getStats().getPaleta() == 0)
                color=0xececec;  //paleta normal
            else if (engine.getStats().getPaleta() == 1)
                color=0xffffff;  //paleta 1
            else if (engine.getStats().getPaleta() == 2)
                color=0xffddf1;  //paleta 2
            else if (engine.getStats().getPaleta() == 3)
                color=0xd0ffe8;  //paleta 3
        }
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
