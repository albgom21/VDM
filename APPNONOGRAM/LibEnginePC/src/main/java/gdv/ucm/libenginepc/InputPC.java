package gdv.ucm.libenginepc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import gdv.ucm.libengine.IInput;

public class InputPC implements IInput, MouseListener {
    public List<Event> eventos = new ArrayList<Event>();

    public void addEvent(MouseEvent evento){
        InputTouchType tipo = null;
        if(evento.getID() == MouseEvent.BUTTON1_DOWN_MASK) //izq
            tipo = InputTouchType.TOUCH_DOWN;
        eventos.add(new Event(evento.getX(),evento.getY(),0,tipo));
    }

    @Override
    public List<Event> getEvents() {
        return eventos;
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Event e = new Event(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getID(), InputTouchType.TOUCH_DOWN);
        addEvent(mouseEvent);  //Añadir a la lista

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
