package com.example.libenginea;

public interface StateA {
    void update(double deltaTime);    // Actualizar elementos de la escena
    void render(GraphicsA graphics);  // Renderizar elementos de la escena
    void handleInputs(InputA inputs); // Cada elemento de la escena maneja los eventos
}