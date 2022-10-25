package gdv.ucm.libenginepc;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import gdv.ucm.libengine.IInput;

public class InputPC implements IInput {
    public List<Event> eventos = new ArrayList<Event>();

    public void addEvent(MouseEvent evento){
        InputTouchType tipo = null;
        if(evento.getID() == MouseEvent.BUTTON1_DOWN_MASK ) //izq
            tipo = InputTouchType.TOUCH_DOWN;
        eventos.add(new Event(evento.getX(),evento.getY(),0,tipo));
    }

    public List<Event> getListaEvents(){
        return eventos;
    }
    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }
}
