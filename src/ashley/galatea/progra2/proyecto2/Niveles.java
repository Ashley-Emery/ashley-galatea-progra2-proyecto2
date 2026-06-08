/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author ashley
 */

import java.io.Serializable;

public class Niveles implements Serializable {
 
    private static final long serialVersionUID = 1L;

    private int nivel;
    private String dificultad;
    private boolean completado;
    private int puntaje;
    private long tiempoMinutos;

    public Niveles(int nivel, String dificultad) {
        this.nivel = nivel;
        this.dificultad = dificultad;
        this.completado = false;
        this.puntaje = 0;
        this.tiempoMinutos = 0;
    }

    public int getNivel() {
        return nivel;
    }

    public String getDificultad() {
        return dificultad;
    }

    public boolean isCompletado() {
        return completado;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public long getTiempoMinutos() {
        return tiempoMinutos;
    }

    public void completarNivel(int puntaje, long tiempoMinutos) {
        this.completado = true;
        this.puntaje = puntaje;
        this.tiempoMinutos = tiempoMinutos;
    }
}