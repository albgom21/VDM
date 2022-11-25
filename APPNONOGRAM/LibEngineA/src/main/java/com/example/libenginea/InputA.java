package com.example.libenginea;

import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class InputA implements View.OnTouchListener{
    private long startTime = 0;
    private long endTime = 0;

    // Tipos de eventos
    public static enum InputTouchType{
        PRESSED,
        RELEASED,
        MOVE,
        LONG_PRESSED,
        NORMAL_PRESSED
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

    public ArrayList<Event> eventos;

    InputA(){
        eventos = new ArrayList<>();
    }

    public ArrayList<Event>getEvents() {
        return eventos;
    }

    public void clearEvents() {
        eventos.clear();
    }

    public void clearIndexEvent(int i) {
        eventos.remove(i);
    }

    public synchronized void addEvent(MotionEvent event){
        InputTouchType tipo = null;
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            tipo = InputTouchType.PRESSED;
            startTime = event.getEventTime();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            tipo = InputTouchType.RELEASED;
            endTime = event.getEventTime();
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE)
            tipo = InputTouchType.MOVE;

        if(endTime-startTime > 500) //500ms => 0.5s
            tipo = InputTouchType.LONG_PRESSED;
        else if(tipo == InputTouchType.RELEASED)
            tipo = InputTouchType.NORMAL_PRESSED; //Ojo nos estamos comiendo el MOVE

        if(tipo!=null)
            eventos.add(new Event((int)event.getX(0),(int)event.getY(0),event.getPointerCount(),tipo));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        addEvent(event);
        return true;
    }
}