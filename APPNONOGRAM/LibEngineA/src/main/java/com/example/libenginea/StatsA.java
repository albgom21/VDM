package com.example.libenginea;

public class StatsA {

    private int paleta = 0; // 0 1 2 3
    private int monedas = 0; // 0 1 2 3
    private boolean[] bosque = new boolean[20]; // true desbloqueado false bloqueado
    private boolean[] emoji = new boolean[20];; // 1 desbloqueado 0 bloqueado
    private boolean[] comida = new boolean[20];; // 1 desbloqueado 0 bloqueado
    private boolean[] navidad = new boolean[20];; // 1 desbloqueado 0 bloqueado

    StatsA(){
        bosque[0] = true;
        emoji[0] = true;
        comida[0] = true;
        navidad[0] = true;
    }

    public void setPaleta(int p){
        paleta = p;
    }

    public void addMoneda(int m){
        monedas += m;
    }

    public void subMoneda(int m){
        monedas -= m;
    }

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