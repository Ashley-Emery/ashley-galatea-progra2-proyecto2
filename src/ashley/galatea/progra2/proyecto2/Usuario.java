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
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String passwordHash;
    private String nombreCompleto;

    private Date fechaRegistro;
    private Date ultimaSesion;
    private Date ultimoLogout;

    private String avatar;
    private int volumen;
    private int volumenSFX;
    private int volumenMusica;
    private boolean sfxActivo;
    private boolean musicaActiva;
    private double posicionMusicaSegundos;
    private String idioma;
    private String controles;

    private int nivelDesbloqueado;
    private int nivelesCompletados;
    private int partidasJugadas;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int puntuacionGeneral;

    private long tiempoTotalJugado;
    private long sumaTiempoNiveles;

    private ArrayList<String> historialPartidas;
    private ArrayList<String> amigosRivales;
    private ArrayList<String> retos;

    private boolean cuentaActiva;
    private int retosGanados;
    private String avatarColorHex;

    public Usuario(String username, String passwordHash, String nombreCompleto) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.nombreCompleto = nombreCompleto;

        this.fechaRegistro = new Date();
        this.ultimaSesion = null;
        this.ultimoLogout = null;

        this.volumen = 50;
        this.volumenSFX = 80;
        this.volumenMusica = 60;
        this.sfxActivo = true;
        this.musicaActiva = true;
        this.posicionMusicaSegundos = 0;
        this.idioma = "Español";
        this.controles = "Mouse";

        this.nivelDesbloqueado = 1;
        this.nivelesCompletados = 0;
        this.partidasJugadas = 0;
        this.partidasGanadas = 0;
        this.partidasPerdidas = 0;
        this.puntuacionGeneral = 0;

        this.tiempoTotalJugado = 0;
        this.sumaTiempoNiveles = 0;

        this.historialPartidas = new ArrayList<String>();
        this.amigosRivales = new ArrayList<String>();
        this.retos = new ArrayList<String>();

        this.avatar = "avatar_" + ((int)(Math.random() * 21) + 1) + ".png";

        String[] colores = {"#a2b794", "#c893c9", "#d99b18", "#b4b4b4", "#205c97"};
        this.avatarColorHex = colores[(int)(Math.random() * colores.length)];

        this.cuentaActiva = true;
        this.retosGanados = 0;


    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public Date getUltimaSesion() {
        return ultimaSesion;
    }

    public Date getUltimoLogout() {
        return ultimoLogout;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getVolumen() {
        return volumen;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getControles() {
        return controles;
    }

    public int getNivelDesbloqueado() {
        return nivelDesbloqueado;
    }

    public int getNivelesCompletados() {
        return nivelesCompletados;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public int getPuntuacionGeneral() {
        return puntuacionGeneral;
    }

    public long getTiempoTotalJugado() {
        return tiempoTotalJugado;
    }

    public long getTiempoPromedioPorNivel() {
        if (nivelesCompletados == 0) {
            return 0;
        }
        return sumaTiempoNiveles / nivelesCompletados;
    }

    public ArrayList<String> getHistorialPartidas() {
        return historialPartidas;
    }

    public ArrayList<String> getAmigosRivales() {
        return amigosRivales;
    }

    public ArrayList<String> getRetos() {
        return retos;
    }

    public boolean isCuentaActiva() {
        return cuentaActiva;
    }

    public int getRetosGanados() {
        return retosGanados;
    }

    public String getAvatarColorHex() {
        return avatarColorHex;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAvatarColorHex(String avatarColorHex) {
        this.avatarColorHex = avatarColorHex;
    }

    public void iniciarSesion() {
        ultimaSesion = new Date();
    }

    public void cerrarSesion() {
        ultimoLogout = new Date();
    }

    public void actualizarPerfil(String avatar, int volumen, String idioma, String controles) {
        this.avatar = avatar;
        this.volumen = volumen;
        this.idioma = idioma;
        this.controles = controles;
    }

    public void registrarPartida(boolean gano, int nivel, int puntos, long tiempoJugado, String detalle) {
        partidasJugadas++;
        tiempoTotalJugado += tiempoJugado;
        puntuacionGeneral += puntos;

        if (gano) {
            partidasGanadas++;

            if (nivel > nivelesCompletados) {
                nivelesCompletados = nivel;
            }

            if (nivel == nivelDesbloqueado && nivelDesbloqueado < 10) {
                nivelDesbloqueado++;
            }

            sumaTiempoNiveles += tiempoJugado;
        } else {
            partidasPerdidas++;
        }

        historialPartidas.add(new Date() + " | Nivel: " + nivel + " | Puntos: " + puntos + " | " + detalle);
    }

    public void agregarAmigoRival(String username) {
        if (!amigosRivales.contains(username)) {
            amigosRivales.add(username);
        }
    }

    public void agregarReto(String reto) {
        retos.add(new Date() + " | " + reto);
    }

    public void eliminarAmigoRival(String username) {
        amigosRivales.remove(username);
    }

    public int getVolumenSFX() {
        return volumenSFX;
    }

    public int getVolumenMusica() {
        return volumenMusica;
    }

    public boolean isSfxActivo() {
        return sfxActivo;
    }

    public boolean isMusicaActiva() {
        return musicaActiva;
    }

    public double getPosicionMusicaSegundos() {
        return posicionMusicaSegundos;
    }

    public void actualizarConfigAudio(int volumenSFX, int volumenMusica, boolean sfxActivo, boolean musicaActiva, double posicionMusicaSegundos) {
        this.volumenSFX = volumenSFX;
        this.volumenMusica = volumenMusica;
        this.sfxActivo = sfxActivo;
        this.musicaActiva = musicaActiva;
        this.posicionMusicaSegundos = posicionMusicaSegundos;
    }
}