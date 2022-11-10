package gdv.ucm.liblogica;
import gdv.ucm.libengine.IGraphics;

public class RenderHints {
    private Hints h;

    RenderHints(Hints h){
        this.h = h;
    }

    public void render(IGraphics gr){
        int depth[] = new int[h.getX()];
        int dH1=0;
        Cell cell = h.getB().getCell(0,0);
        for (int i = h.getY()-1; i >= 0; --i) {
            for (int j = h.getX()-1; j >= 0; --j) {
                if(h.getVerticalHints()[i][j]!=0) {
                    gr.drawText(Integer.toString(h.getVerticalHints()[i][j]),
                            gr.logicToRealX(gr.getWidthLogic()/2) //mitad de pantalla
                                    - (int)((cell.getOffsetX()/2) * cell.getSide())
                                    - (int)(((cell.getOffsetX()/2) - 1) * cell.getSeparacion())
                                    - 22 // Separación con el tablero
                                    - (dH1*(cell.getSide()/4)),
                            gr.logicToRealY(gr.getHeightLogic()/2) //mitad de pantalla
                                    - (int)((cell.getOffsetY()/2) * (cell.getSide()+cell.getSeparacion()))
                                    + gr.scaleToReal(cell.getSeparacion())
                                    + (i * (cell.getSeparacion() + cell.getSide())),
                            0x442700, null, cell.getSide()/4);
                    dH1++;
                }
                if(h.getHorizontalHints()[j][i]!=0) {
                    gr.drawText(Integer.toString(h.getHorizontalHints()[j][i]),
                            gr.logicToRealX(gr.getWidthLogic()/2)
                                    - (int)((cell.getOffsetX()/2) * cell.getSide())
                                    - (int)((cell.getOffsetX()/2 - 1) * cell.getSeparacion())
                                    + (cell.getSeparacion()*2)
                                    + (j * (cell.getSeparacion()+cell.getSide())),
                            gr.logicToRealY(gr.getHeightLogic()/2) //mitad de pantalla
                                    - (int)((cell.getOffsetY()/2) * (cell.getSide() + cell.getSeparacion()))
                                    - (cell.getSide()/2)*3
                                    + cell.getSide()
                                    - (depth[j]*(cell.getSide()/3)), 0x442700, null, cell.getSide()/4);
                    depth[j]++;
                }
            }
            dH1=0;
        }
    }
}
