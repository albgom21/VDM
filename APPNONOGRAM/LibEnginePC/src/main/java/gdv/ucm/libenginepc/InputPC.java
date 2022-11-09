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

    public synchronized void addEvent(MouseEvent evento){
        InputTouchType tipo = null;

        if(evento.getID() == MouseEvent.MOUSE_PRESSED)
            tipo = InputTouchType.PRESSED;
        else if(evento.getID() == MouseEvent.MOUSE_RELEASED)
            tipo = InputTouchType.RELEASED;
//        else if(evento.getID() == MouseEvent.MOUSE_MOVED) //MOUSE_DRAGGED
//            tipo = InputTouchType.MOVE;


        if(tipo!=null)
            eventos.add(new Event(evento.getX(),evento.getY(),evento.getButton(),tipo));
    }

    @Override
    public synchronized List<Event> getEvents() { //PASAR UNA COPIA DE LA LISTA borrar de la original los ele que se usan
        List<Event> aux = new ArrayList<>(eventos);
//        eventos.clear();
        return aux;
    }

    @Override
    public void clearEvents() {
        eventos.clear();
    }

    @Override
    public void clearIndexEvent(int i) {
        eventos.remove(i);
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
    public void mouseClicked(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
