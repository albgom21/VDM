package com.example.libenginea;

import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

import gdv.ucm.libengine.IInput;

public class InputA implements IInput, View.OnTouchListener{
    public ArrayList<Event> eventos;        //ARRAY DE EVENTOS

    InputA(){
        eventos = new ArrayList<>();
    }

    @Override
    public ArrayList<Event>getEvents() {
        return eventos;
    }       //COGE EL ARRAY DE EVENTOS

    @Override
    public void clearEvents() {
        eventos.clear();
    }       //LIMPIA EL ARRAY DE EVENTOS

    @Override
    public void clearIndexEvent(int i) {
        eventos.remove(i);
    }       //LIMPIA UN ELEMENTO DEL ARRAY

    public void addEvent(MotionEvent event){        //AÑADE EVENTO
        InputTouchType tipo = null;
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            tipo = InputTouchType.PRESSED;
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
        return true;
    }
}