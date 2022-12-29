package gdv.ucm.libenginepc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import gdv.ucm.libengine.IInput;

public class InputPC implements IInput, MouseListener {
    public ArrayList<Event> eventos;        //EVENTOS DE INPUT

    InputPC(){
        eventos = new ArrayList<>();
    }

    public void addEvent(MouseEvent evento){        //AÑADIR LOS DIFERENTES EVENTOS
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
    public ArrayList<Event> getEvents() {
        return eventos;
    }       //PILLAR LISTA DE EVENTOS

    @Override
    public void clearEvents() {
        eventos.clear();
    }       //LIMPIAR EVENTOS

    @Override
    public void clearIndexEvent(int i) {
        eventos.remove(i);
    }       //ELIMINAR UN EVENTO

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