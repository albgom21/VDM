package gdv.ucm.libenginepc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import gdv.ucm.libengine.IInput;

public class InputPC implements IInput, MouseListener {
    public List<Event> eventos;

    InputPC(){
        eventos = new ArrayList<>();
    }

    public void addEvent(MouseEvent evento){
        InputTouchType tipo = null;
        if(evento.getID() == MouseEvent.MOUSE_MOVED) //MOUSE_DRAGGED
            tipo = InputTouchType.MOVE;
        else if(evento.getID() == MouseEvent.MOUSE_PRESSED)
            tipo = InputTouchType.PRESSED;
        else if(evento.getID() == MouseEvent.MOUSE_RELEASED)
            tipo = InputTouchType.RELEASED;
//        else if(evento.getID() == MouseEvent.MOUSE_DRAGGED)
//            tipo = InputTouchType.MOVE;

        eventos.add(new Event(evento.getX(),evento.getY(),evento.getButton(),tipo));
    }

    @Override
    public List<Event> getEvents() {
        return eventos;
    }

    @Override
    public void clearEvents() {
        eventos.clear();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        addEvent(mouseEvent);  //Añadir a la lista
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        addEvent(mouseEvent);  //Añadir a la lista
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        addEvent(mouseEvent);  //Añadir a la lista
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {  }

    @Override
    public void mouseExited(MouseEvent mouseEvent) { }
}
