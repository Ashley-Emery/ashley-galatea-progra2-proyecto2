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
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChallengePartida implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String jugador1;
    private String jugador2;
    private String dificultad;
    private int nivel;

    private int tiempoJugador1 = -1;
    private int tiempoJugador2 = -1;

    private int scoreJugador1 = 0;
    private int scoreJugador2 = 0;

    private String ganador = "";
    private boolean finalizado = false;

    private Date fechaCreacion;
    private boolean declinado = false;

    public ChallengePartida(String jugador1, String jugador2, String dificultad, int nivel) {
        this.id = "CH-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.dificultad = dificultad;
        this.nivel = nivel;
        this.fechaCreacion = new Date();
    }

    public String getId() { 
        return id; 
    }

    public String getJugador1() { 
        return jugador1; 
    }

    public String getJugador2() { 
        return jugador2; 
    }

    public String getDificultad() { 
        return dificultad; 
    }

    public int getNivel() { 
        return nivel; 
    }

    public int getTiempoJugador1() { 
        return tiempoJugador1; 
    }

    public int getTiempoJugador2() { 
        return tiempoJugador2; 
    }

    public int getScoreJugador1() { 
        return scoreJugador1; 
    }

    public int getScoreJugador2() { 
        return scoreJugador2; 
    }

    public String getGanador() { 
        return ganador; 
    }

    public boolean isFinalizado() { 
        return finalizado; 
    }

    public Date getFechaCreacion() { 
        return fechaCreacion; 
    }

    public boolean isDeclinado() { 
        return declinado; 
    }

    public boolean registrarTiempo(String username, int tiempo, int scoreBase) {
        if (username.equals(jugador1) && tiempoJugador1 == -1) {
            tiempoJugador1 = tiempo;
            scoreJugador1 = scoreBase;
            return true;
        }

        if (username.equals(jugador2) && tiempoJugador2 == -1) {
            tiempoJugador2 = tiempo;
            scoreJugador2 = scoreBase;
            return true;
        }

        return false;
    }

    public boolean ambosCompletaron() {
        return tiempoJugador1 >= 0 && tiempoJugador2 >= 0;
    }

    public void calcularGanador() {
        if (!ambosCompletaron()) {
            return;
        }

        if (tiempoJugador1 <= tiempoJugador2) {
            ganador = jugador1;
            scoreJugador1 += 50;
        } else {
            ganador = jugador2;
            scoreJugador2 += 50;
        }

        finalizado = true;
    }

    public void declinar(String username) {
        if (!username.equals(jugador2)) {
            return;
        }

        declinado = true;
        finalizado = true;
        ganador = jugador1;
        scoreJugador1 = 50;
    }
}
