/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author USER
 */
public abstract class Puzzle {
    
    protected int nivelActual;
    protected boolean juegoActivo;

    public abstract void cargarNivel(int nivel);

    public abstract void reiniciar();

    public abstract void iniciarCamino(int fila, int columna);

    public abstract void arrastrarCamino(int fila, int columna);

    public abstract void terminarCamino();

    public abstract boolean hayVictoria();

    public abstract boolean haySiguienteNivel();

    public abstract void avanzarNivel();

    public abstract int getNivelActual();

    public abstract int getTotalNiveles();

    public abstract int getFilas();

    public abstract int getColumnas();

    public abstract int getSegundosJugados();

    public abstract String getNombreGrupoNivel();

    public abstract String getDificultadNivel();
    
    
}
