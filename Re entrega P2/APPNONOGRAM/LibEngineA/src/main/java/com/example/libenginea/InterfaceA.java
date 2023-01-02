package com.example.libenginea;

public interface InterfaceA {
    void render (GraphicsA g);              // Renderizar elemento de interfaz
    void update (Double deltaTime);         // Actualizar elemento de interfaz
    boolean handleEvent (InputA.Event e);   // Manejar evento
}