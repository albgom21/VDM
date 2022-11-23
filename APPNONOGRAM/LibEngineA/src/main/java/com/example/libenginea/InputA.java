package com.example.libenginea;

import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class InputA implements View.OnTouchListener, View.OnLongClickListener{
    private boolean longClicked = false;

    @Override
    public boolean onLongClick(View view) {
        System.out.println("long");
        longClicked = true;
        return true;
    }

    // Tipos de eventos
    public static enum InputTouchType{
        PRESSED,
        RELEASED,
        MOVE,
        LONG_PRESSED
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
        System.out.println("longClicked = " + longClicked);
        if(longClicked) {
            tipo = InputTouchType.LONG_PRESSED;
            System.out.println("AH");
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN) {
            tipo = InputTouchType.PRESSED;
            System.out.println("AH2");
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
            tipo = InputTouchType.RELEASED;
        else if(event.getAction() == MotionEvent.ACTION_MOVE)
            tipo = InputTouchType.MOVE;

        if(tipo!=null)
            eventos.add(new Event((int)event.getX(0),(int)event.getY(0),event.getPointerCount(),tipo));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        addEvent(event);
        if(longClicked)
            longClicked = false;
        return true;
    }
}