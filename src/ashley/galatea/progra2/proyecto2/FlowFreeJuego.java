/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author USER
 */
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class FlowFreeJuego extends Puzzle {

    private int filas = 5;
    private int columnas = 5;

    public static final int VACIO = 0;
    public static final int ROJO = 1;
    public static final int GRIS = 2;
    public static final int NARANJA = 3;
    public static final int CYAN = 4;
    public static final int AMARILLO = 5;
    public static final int MORADO = 6;
    public static final int VERDE_LIMA = 7;
    public static final int TURQUESA = 8;
    public static final int ROSA = 9;
    public static final int CELESTE = 10;
    public static final int CAFE = 11;
    public static final int FUCSIA = 12;

    private int[][] puntos;
    private int[][] ocupadas;

    private HashMap<Integer, ArrayList<Point>> caminos = new HashMap<>();

    private int colorActivo = VACIO;
    private boolean arrastrando = false;
    private boolean victoria = false;

    private final int TOTAL_NIVELES = 10;

    private HiloTiempo hiloTiempo;
    private int segundosJugados = 0;

    public FlowFreeJuego(int nivelInicial) {
        nivelActual = nivelInicial;
        juegoActivo = true;
        cargarNivel(nivelActual);
    }

    public FlowFreeJuego() {
        this(1);
    }

    private abstract class GrupoNiveles {

        abstract String getNombreGrupo();

        abstract String getDificultad();

        abstract boolean contieneNivel(int nivel);

        abstract void definirTamano(int nivel);

        abstract void cargar(int nivel);
    }

    private class NeonCircuit extends GrupoNiveles {

        @Override
        String getNombreGrupo() {
            return "Neon Circuit";
        }

        String getDificultad() {
            return "Fácil";
        }

        boolean contieneNivel(int nivel) {
            return nivel == 1 || nivel == 2;
        }

        void definirTamano(int nivel) {
            filas = 5;
            columnas = 5;
        }

        void cargar(int nivel) {
            if (nivel == 1) {
                crearNivel1();
            } else {
                crearNivel2();
            }
        }
    }

    private class PowerGrid extends GrupoNiveles {

        String getNombreGrupo() {
            return "Power Grid";
        }

        String getDificultad() {
            return "Medio";
        }

        boolean contieneNivel(int nivel) {
            return nivel == 3 || nivel == 4;
        }

        void definirTamano(int nivel) {
            filas = 7;
            columnas = 7;
        }

        void cargar(int nivel) {
            if (nivel == 3) {
                crearNivel3();
            } else {
                crearNivel4();
            }
        }
    }

    private class VoltageRun extends GrupoNiveles {

        String getNombreGrupo() {
            return "Voltage Run";
        }

        String getDificultad() {
            return "Avanzado";
        }

        boolean contieneNivel(int nivel) {
            return nivel == 5 || nivel == 6;
        }

        void definirTamano(int nivel) {
            filas = 8;
            columnas = 8;
        }

        void cargar(int nivel) {
            if (nivel == 5) {
                crearNivel5();
            } else {
                crearNivel6();
            }
        }
    }

    private class ElectricDrift extends GrupoNiveles {

        String getNombreGrupo() {
            return "Electric Drift";
        }

        String getDificultad() {
            return "Difícil";
        }

        boolean contieneNivel(int nivel) {
            return nivel == 7 || nivel == 8;
        }

        void definirTamano(int nivel) {
            filas = 9;
            columnas = 9;
        }

        void cargar(int nivel) {
            if (nivel == 7) {
                crearNivel7();
            } else {
                crearNivel8();
            }
        }
    }

    private class Overload extends GrupoNiveles {

        String getNombreGrupo() {
            return "Overload";
        }

        String getDificultad() {
            return "Experto";
        }

        boolean contieneNivel(int nivel) {
            return nivel == 9 || nivel == 10;
        }

        void definirTamano(int nivel) {
            filas = 10;
            columnas = 10;
        }

        void cargar(int nivel) {
            if (nivel == 9) {
                crearNivel9();
            } else {
                crearNivel10();
            }
        }
    }

    private GrupoNiveles obtenerGrupoNivel(int nivel) {
        GrupoNiveles[] grupos = {
            new NeonCircuit(),
            new PowerGrid(),
            new VoltageRun(),
            new ElectricDrift(),
            new Overload()
        };

        for (GrupoNiveles grupo : grupos) {
            if (grupo.contieneNivel(nivel)) {
                return grupo;
            }
        }

        return new NeonCircuit();
    }

    private class HiloTiempo extends Thread {

        private boolean corriendo = true;

        public void run() {
            while (corriendo) {
                try {
                    Thread.sleep(1000);
                    segundosJugados++;
                } catch (InterruptedException e) {
                    corriendo = false;
                }
            }
        }

        public void detener() {
            corriendo = false;
            interrupt();
        }
    }

    private void iniciarHiloTiempo() {
        detenerHiloTiempo();
        segundosJugados = 0;
        hiloTiempo = new HiloTiempo();
        hiloTiempo.start();
    }

    private void detenerHiloTiempo() {
        if (hiloTiempo != null) {
            hiloTiempo.detener();
            hiloTiempo = null;
        }
    }

    public void cargarNivel(int nivel) {
        GrupoNiveles grupo = obtenerGrupoNivel(nivel);

        grupo.definirTamano(nivel);
        limpiarDatosNivel();
        grupo.cargar(nivel);

        iniciarHiloTiempo();
    }

    private void limpiarDatosNivel() {
        puntos = new int[filas][columnas];
        ocupadas = new int[filas][columnas];
        caminos.clear();
        colorActivo = VACIO;
        arrastrando = false;
        victoria = false;
    }


    // =========================================================
    // PUZZLE - NIVEL 1
    // =========================================================
    private void crearNivel1() {
        puntos[0][0] = MORADO;
        puntos[2][2] = MORADO;

        puntos[0][3] = ROJO;
        puntos[1][1] = ROJO;

        puntos[1][2] = CYAN;
        puntos[3][1] = CYAN;

        puntos[0][4] = VERDE_LIMA;
        puntos[3][4] = VERDE_LIMA;

        puntos[3][0] = TURQUESA;
        puntos[4][4] = TURQUESA;

        caminos.put(MORADO, new ArrayList<>());
        caminos.put(ROJO, new ArrayList<>());
        caminos.put(CYAN, new ArrayList<>());
        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(TURQUESA, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 2
    // =========================================================
    private void crearNivel2() {
        puntos[1][0] = ROJO;
        puntos[2][2] = ROJO;

        puntos[1][1] = GRIS;
        puntos[4][0] = GRIS;

        puntos[0][3] = NARANJA;
        puntos[2][4] = NARANJA;

        puntos[1][3] = CYAN;
        puntos[3][2] = CYAN;

        puntos[3][1] = AMARILLO;
        puntos[3][4] = AMARILLO;

        caminos.put(ROJO, new ArrayList<>());
        caminos.put(GRIS, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(CYAN, new ArrayList<>());
        caminos.put(AMARILLO, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 3
    // =========================================================
    private void crearNivel3() {
        puntos[0][1] = TURQUESA;
        puntos[4][3] = TURQUESA;

        puntos[0][2] = AMARILLO;
        puntos[3][2] = AMARILLO;

        puntos[1][1] = NARANJA;
        puntos[3][1] = NARANJA;

        puntos[1][4] = VERDE_LIMA;
        puntos[2][4] = VERDE_LIMA;

        puntos[1][5] = ROSA;
        puntos[3][5] = ROSA;

        puntos[4][4] = ROJO;
        puntos[4][6] = ROJO;

        puntos[4][5] = CELESTE;
        puntos[5][0] = CELESTE;

        puntos[5][6] = MORADO;
        puntos[6][0] = MORADO;

        caminos.put(TURQUESA, new ArrayList<>());
        caminos.put(AMARILLO, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(ROSA, new ArrayList<>());
        caminos.put(ROJO, new ArrayList<>());
        caminos.put(CELESTE, new ArrayList<>());
        caminos.put(MORADO, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 4
    // =========================================================
    private void crearNivel4() {
        puntos[0][0] = ROJO;
        puntos[1][1] = ROJO;

        puntos[0][2] = ROSA;
        puntos[1][6] = ROSA;

        puntos[1][0] = NARANJA;
        puntos[5][1] = NARANJA;

        puntos[2][3] = AMARILLO;
        puntos[6][4] = AMARILLO;

        puntos[3][0] = TURQUESA;
        puntos[4][2] = TURQUESA;

        puntos[2][6] = MORADO;
        puntos[5][6] = MORADO;

        puntos[6][3] = GRIS;
        puntos[6][6] = GRIS;

        caminos.put(ROJO, new ArrayList<>());
        caminos.put(ROSA, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(AMARILLO, new ArrayList<>());
        caminos.put(TURQUESA, new ArrayList<>());
        caminos.put(MORADO, new ArrayList<>());
        caminos.put(GRIS, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 5
    // =========================================================
    private void crearNivel5() {
        puntos[1][1] = NARANJA;
        puntos[2][2] = NARANJA;

        puntos[2][1] = MORADO;
        puntos[3][7] = MORADO;

        puntos[1][4] = ROSA;
        puntos[7][0] = ROSA;

        puntos[1][5] = GRIS;
        puntos[7][3] = GRIS;

        puntos[2][4] = VERDE_LIMA;
        puntos[6][6] = VERDE_LIMA;

        puntos[4][1] = ROJO;
        puntos[3][4] = ROJO;

        puntos[6][1] = CYAN;
        puntos[5][4] = CYAN;

        puntos[7][1] = CELESTE;
        puntos[6][5] = CELESTE;

        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(MORADO, new ArrayList<>());
        caminos.put(ROSA, new ArrayList<>());
        caminos.put(GRIS, new ArrayList<>());
        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(ROJO, new ArrayList<>());
        caminos.put(CYAN, new ArrayList<>());
        caminos.put(CELESTE, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 6
    // =========================================================
    private void crearNivel6() {
        puntos[0][0] = GRIS;
        puntos[3][1] = GRIS;

        puntos[1][2] = ROSA;
        puntos[3][2] = ROSA;

        puntos[2][4] = VERDE_LIMA;
        puntos[4][3] = VERDE_LIMA;

        puntos[3][4] = ROJO;
        puntos[4][7] = ROJO;

        puntos[4][0] = CELESTE;
        puntos[6][1] = CELESTE;

        puntos[5][0] = CAFE;
        puntos[5][7] = CAFE;

        puntos[6][2] = NARANJA;
        puntos[6][6] = NARANJA;

        caminos.put(GRIS, new ArrayList<>());
        caminos.put(ROSA, new ArrayList<>());
        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(ROJO, new ArrayList<>());
        caminos.put(CELESTE, new ArrayList<>());
        caminos.put(CAFE, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 7
    // =========================================================
    private void crearNivel7() {
        puntos[0][0] = VERDE_LIMA;
        puntos[4][4] = VERDE_LIMA;

        puntos[0][3] = ROSA;
        puntos[2][1] = ROSA;

        puntos[0][4] = AMARILLO;
        puntos[7][2] = AMARILLO;

        puntos[1][2] = CAFE;
        puntos[1][4] = CAFE;

        puntos[1][5] = CYAN;
        puntos[5][7] = CYAN;

        puntos[2][2] = CELESTE;
        puntos[7][1] = CELESTE;

        puntos[3][1] = NARANJA;
        puntos[5][1] = NARANJA;

        puntos[5][0] = ROJO;
        puntos[7][8] = ROJO;

        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(ROSA, new ArrayList<>());
        caminos.put(AMARILLO, new ArrayList<>());
        caminos.put(CAFE, new ArrayList<>());
        caminos.put(CYAN, new ArrayList<>());
        caminos.put(CELESTE, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(ROJO, new ArrayList<>());
    }

   // =========================================================
    // PUZZLE - NIVEL 8
    // =========================================================
    private void crearNivel8() {
        puntos[0][0] = ROSA;
        puntos[8][2] = ROSA;

        puntos[0][1] = AMARILLO;
        puntos[8][3] = AMARILLO;

        puntos[0][4] = CYAN;
        puntos[5][4] = CYAN;

        puntos[0][5] = MORADO;
        puntos[5][6] = MORADO;

        puntos[1][7] = NARANJA;
        puntos[7][5] = NARANJA;

        puntos[2][0] = CELESTE;
        puntos[8][1] = CELESTE;

        puntos[2][5] = VERDE_LIMA;
        puntos[2][8] = VERDE_LIMA;

        puntos[3][5] = TURQUESA;
        puntos[8][8] = TURQUESA;

        caminos.put(ROSA, new ArrayList<>());
        caminos.put(AMARILLO, new ArrayList<>());
        caminos.put(CYAN, new ArrayList<>());
        caminos.put(MORADO, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(CELESTE, new ArrayList<>());
        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(TURQUESA, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 9
    // =========================================================
    private void crearNivel9() {
        puntos[0][3] = TURQUESA;
        puntos[6][2] = TURQUESA;

        puntos[0][5] = VERDE_LIMA;
        puntos[4][5] = VERDE_LIMA;

        puntos[0][6] = ROSA;
        puntos[2][3] = ROSA;

        puntos[1][1] = CYAN;
        puntos[5][7] = CYAN;

        puntos[1][2] = NARANJA;
        puntos[4][5] = NARANJA;

        puntos[1][3] = VERDE_LIMA;
        puntos[0][5] = VERDE_LIMA;

        puntos[1][8] = GRIS;
        puntos[4][7] = GRIS;

        puntos[1][9] = ROJO;
        puntos[5][6] = ROJO;

        puntos[2][9] = FUCSIA;
        puntos[8][7] = FUCSIA;

        puntos[4][8] = AMARILLO;
        puntos[9][9] = AMARILLO;

        puntos[5][1] = CELESTE;
        puntos[7][5] = CELESTE;

        puntos[8][1] = MORADO;
        puntos[9][5] = MORADO;

        puntos[8][5] = CAFE;
        puntos[9][1] = CAFE;

        caminos.put(TURQUESA, new ArrayList<>());
        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(ROSA, new ArrayList<>());
        caminos.put(CYAN, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(GRIS, new ArrayList<>());
        caminos.put(ROJO, new ArrayList<>());
        caminos.put(FUCSIA, new ArrayList<>());
        caminos.put(AMARILLO, new ArrayList<>());
        caminos.put(CELESTE, new ArrayList<>());
        caminos.put(MORADO, new ArrayList<>());
        caminos.put(CAFE, new ArrayList<>());
    }

    // =========================================================
    // PUZZLE - NIVEL 10
    // =========================================================
    private void crearNivel10() {
        puntos[0][0] = TURQUESA;
        puntos[1][3] = TURQUESA;

        puntos[0][1] = ROSA;
        puntos[1][4] = ROSA;

        puntos[1][9] = NARANJA;
        puntos[3][5] = NARANJA;

        puntos[2][7] = CAFE;
        puntos[7][6] = CAFE;

        puntos[3][0] = GRIS;
        puntos[7][3] = GRIS;

        puntos[3][6] = CELESTE;
        puntos[3][9] = CELESTE;

        puntos[3][8] = CYAN;
        puntos[9][9] = CYAN;

        puntos[4][2] = ROJO;
        puntos[8][5] = ROJO;

        puntos[4][3] = FUCSIA;
        puntos[9][0] = FUCSIA;

        puntos[5][3] = VERDE_LIMA;
        puntos[8][3] = VERDE_LIMA;

        puntos[5][6] = MORADO;
        puntos[9][5] = MORADO;

        puntos[6][6] = AMARILLO;
        puntos[8][6] = AMARILLO;

        caminos.put(TURQUESA, new ArrayList<>());
        caminos.put(ROSA, new ArrayList<>());
        caminos.put(NARANJA, new ArrayList<>());
        caminos.put(CAFE, new ArrayList<>());
        caminos.put(GRIS, new ArrayList<>());
        caminos.put(CELESTE, new ArrayList<>());
        caminos.put(CYAN, new ArrayList<>());
        caminos.put(ROJO, new ArrayList<>());
        caminos.put(FUCSIA, new ArrayList<>());
        caminos.put(VERDE_LIMA, new ArrayList<>());
        caminos.put(MORADO, new ArrayList<>());
        caminos.put(AMARILLO, new ArrayList<>());
    }

    @Override
    public void reiniciar() {
        cargarNivel(nivelActual);
    }

    @Override
    public int getNivelActual() {
        return nivelActual;
    }

    @Override
    public boolean haySiguienteNivel() {
        return nivelActual < TOTAL_NIVELES;
    }

    @Override
    public void avanzarNivel() {
        if (haySiguienteNivel()) {
            nivelActual++;
            cargarNivel(nivelActual);
        }
    }

    @Override
    public int getTotalNiveles() {
        return TOTAL_NIVELES;
    }

    @Override
    public void iniciarCamino(int fila, int columna) {
        if (!posicionValida(fila, columna)) {
            return;
        }

        int color = puntos[fila][columna];

        if (color == VACIO) {
            return;
        }

        limpiarCamino(color);

        colorActivo = color;
        arrastrando = true;

        ArrayList<Point> camino = caminos.get(colorActivo);
        Point inicio = new Point(columna, fila);
        camino.add(inicio);
        ocupadas[fila][columna] = colorActivo;
    }

    @Override
    public void arrastrarCamino(int fila, int columna) {
        if (!arrastrando || colorActivo == VACIO || !posicionValida(fila, columna)) {
            return;
        }

        ArrayList<Point> camino = caminos.get(colorActivo);

        if (camino.isEmpty()) {
            return;
        }

        Point ultimo = camino.get(camino.size() - 1);

        if (ultimo.x == columna && ultimo.y == fila) {
            return;
        }

        Point nuevo = new Point(columna, fila);

        if (camino.size() >= 2) {
            Point anterior = camino.get(camino.size() - 2);

            if (anterior.x == columna && anterior.y == fila) {
                ocupadas[ultimo.y][ultimo.x] = VACIO;
                camino.remove(camino.size() - 1);
                return;
            }
        }

        if (!esVecina(ultimo.y, ultimo.x, fila, columna)) {
            return;
        }

        if (puntos[fila][columna] != VACIO && puntos[fila][columna] != colorActivo) {
            return;
        }

        if (ocupadas[fila][columna] != VACIO && ocupadas[fila][columna] != colorActivo) {
            return;
        }

        if (caminoContiene(camino, nuevo)) {
            return;
        }

        camino.add(nuevo);
        ocupadas[fila][columna] = colorActivo;
    }

    @Override
    public void terminarCamino() {
        arrastrando = false;
        colorActivo = VACIO;
        victoria = validarVictoria();

        if (victoria) {
            detenerHiloTiempo();
        }
    }

    private void limpiarCamino(int color) {
        ArrayList<Point> camino = caminos.get(color);

        for (Point p : camino) {
            ocupadas[p.y][p.x] = VACIO;
        }

        camino.clear();
    }

    private boolean validarVictoria() {
        for (Integer color : caminos.keySet()) {
            if (!validarCamino(color)) {
                return false;
            }
        }

        return tableroLleno();
    }

    private boolean validarCamino(int color) {
        ArrayList<Point> camino = caminos.get(color);

        if (camino.size() < 2) {
            return false;
        }

        Point inicio = camino.get(0);
        Point fin = camino.get(camino.size() - 1);

        return puntos[inicio.y][inicio.x] == color && puntos[fin.y][fin.x] == color;
    }

    private boolean tableroLleno() {
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                if (ocupadas[fila][columna] == VACIO) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean posicionValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    private boolean esVecina(int fila1, int columna1, int fila2, int columna2) {
        int diferenciaFila = Math.abs(fila1 - fila2);
        int diferenciaColumna = Math.abs(columna1 - columna2);

        return diferenciaFila + diferenciaColumna == 1;
    }

    private boolean caminoContiene(ArrayList<Point> camino, Point punto) {
        for (Point p : camino) {
            if (p.x == punto.x && p.y == punto.y) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getFilas() {
        return filas;
    }

    @Override
    public int getColumnas() {
        return columnas;
    }

    @Override
    public int getSegundosJugados() {
        return segundosJugados;
    }

    @Override
    public String getNombreGrupoNivel() {
        return obtenerGrupoNivel(nivelActual).getNombreGrupo();
    }

    @Override
    public String getDificultadNivel() {
        return obtenerGrupoNivel(nivelActual).getDificultad();
    }

    public int getPunto(int fila, int columna) {
        return puntos[fila][columna];
    }

    public HashMap<Integer, ArrayList<Point>> getCaminos() {
        return caminos;
    }

    @Override
    public boolean hayVictoria() {
        return victoria;
    }

    public Color getColorJava(int color) {
        switch (color) {
            case ROJO:
                return new Color(230, 86, 83);
            case GRIS:
                return new Color(139, 154, 181);
            case NARANJA:
                return new Color(255, 128, 62);
            case CYAN:
                return new Color(67, 190, 212);
            case AMARILLO:
                return new Color(255, 188, 10);
            case MORADO:
                return new Color(152, 101, 239);
            case VERDE_LIMA:
                return new Color(164, 222, 62);
            case TURQUESA:
                return new Color(39, 215, 174);
            case ROSA:
                return new Color(210, 76, 180);
            case CELESTE:
                return new Color(107, 169, 177);
            case CAFE:
                return new Color(174, 140, 115);
            case FUCSIA:
                return new Color(220, 36, 112);
            default:
                return Color.LIGHT_GRAY;
        }
    }
    
}
