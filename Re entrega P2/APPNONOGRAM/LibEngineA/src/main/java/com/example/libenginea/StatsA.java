package com.example.libenginea;

import java.io.Serializable;

public class StatsA implements Serializable {

    private int paleta;
    private int monedas;
    private boolean[] paletas = new boolean[4]; // true desbloqueado false bloqueado
    private boolean[] bosque = new boolean[20];
    private boolean[] emoji = new boolean[20];
    private boolean[] comida = new boolean[20];
    private boolean[] navidad = new boolean[20];

    public StatsA(){
        paletas[0] = true;
        paletas[1] = true;
        bosque[0] = true;
        emoji[0] = true;
        comida[0] = true;
        navidad[0] = true;
        monedas = 0;
    }

    public void setPaleta(int p){
        if(paletas[p])
            paleta = p;
    }

    public boolean isPaletaUnlock(int p){ return paletas[p];} //true desbloqueada false bloqueada

    public void addMoneda(int m){
        monedas += m;
    }

    public void subMoneda(int m){ monedas -= m; }

    public int getPaleta() {
        return paleta;
    }

    public int getMonedas() {
        return monedas;
    }

    public boolean[] getBosque() {
        return bosque;
    }

    public boolean[] getEmoji() {
        return emoji;
    }

    public boolean[] getComida() {
        return comida;
    }

    public boolean[] getNavidad() {
        return navidad;
    }

    public void setPaletaDesbloqueada(int i) {
        paletas[i] = true;
    }
    public void setBosqueDesbloqueado(int i) {
        bosque[i] = true;
    }
    public void setEmojiDesbloqueado(int i) {
        emoji[i] = true;
    }
    public void setComidaDesbloqueado(int i) {
        comida[i] = true;
    }
    public void setNavidadDesbloqueado(int i) {
        navidad[i] = true;
    }
}