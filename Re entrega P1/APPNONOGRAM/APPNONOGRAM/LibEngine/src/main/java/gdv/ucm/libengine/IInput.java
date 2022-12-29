package gdv.ucm.libengine;

import java.util.List;

public interface IInput {

    // Tipos de eventos
    public static enum InputTouchType{
        PRESSED,
        RELEASED,
        MOVE
    }

    // Evento común
    public static class Event{
        public int x;               // Pos x
        public int y;               // Pos y
        public InputTouchType type; // Tipo de evento
        public int index;           // Botón pulsado en PC / Nº de dedo en Android

        public Event(int x, int y, int index, InputTouchType type){
            this.x = x;
            this.y = y;
            this.index = index;
            this.type = type;
        }
    }

    public List<Event> getEvents();     // Obtener la lista de eventos
    public void clearEvents();          // Limpiar la lista de eventos
    public void clearIndexEvent(int i); // Eliminar un evento de la lista
}