package gdv.ucm.libenginepc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import gdv.ucm.libengine.IInput;

public class InputPC implements IInput, MouseListener {
    public ArrayList<Event> eventos;

    InputPC(){
        eventos = new ArrayList<>();
    }

    public synchronized void addEvent(MouseEvent evento){
        InputTouchType tipo = null;

        if(evento.getID() == MouseEvent.MOUSE_PRESSED)
            tipo = InputTouchType.PRESSED;
        else if(evento.getID() == MouseEvent.MOUSE_RELEASED)
            tipo = InputTouchType.RELEASED;
        else if(evento.getID() == MouseEvent.MOUSE_MOVED) //MOUSE_DRAGGED
            tipo = InputTouchType.MOVE;

        if(tipo != null)
            eventos.add(new Event(evento.getX(),evento.getY(),evento.getButton(),tipo));
    }

    @Override
    public synchronized ArrayList<Event> getEvents() {
        return eventos;
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
    public void mousePressed(MouseEvent mouseEvent) {addEvent(mouseEvent);} //Añadir a la lista

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {addEvent(mouseEvent);} // Añadir a la lista

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}