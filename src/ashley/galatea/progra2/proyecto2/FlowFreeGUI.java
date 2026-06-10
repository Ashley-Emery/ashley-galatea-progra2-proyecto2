/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author USER
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class FlowFreeGUI extends JFrame {

    private FlowFreeJuego juego;
    private PanelTablero panelTablero;
    private JLabel lblEstado;
    private Timer timerGUI;

    private CardLayout cardLayout;
    private JPanel contenedor;

    private Menus menus;
    private MenusGUI menuPrincipal;
    private int nivelInicial;

    private static final String CARD_INTRO = "INTRO";
    private static final String CARD_TRANSICION = "TRANSICION";
    private static final String CARD_JUEGO = "JUEGO";

    private AudioManager audioManager;

    private static final String ASSETS_DIR = "src/ashley/galatea/progra2/proyecto2/assets/";

    public FlowFreeGUI() {
        this(null, null, 1);
    }

    public FlowFreeGUI(Menus menus, MenusGUI menuPrincipal, int nivelInicial) {
        this.menus = menus;
        this.menuPrincipal = menuPrincipal;
        this.nivelInicial = nivelInicial;
        this.audioManager = new AudioManager(menus);

        setTitle("Flow Free");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int) (screen.width * 0.82);
        int height = (int) (screen.height * 0.78);

        setSize(width, height);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        contenedor.add(new PanelIntro(), CARD_INTRO);

        add(contenedor);

        cardLayout.show(contenedor, CARD_INTRO);
    }

    private void mostrarTransicion(String nombreImagen, Runnable accionDespues) {
        PanelImagen panelTransicion = new PanelImagen(ASSETS_DIR + nombreImagen);
        contenedor.add(panelTransicion, CARD_TRANSICION);
        cardLayout.show(contenedor, CARD_TRANSICION);

        Timer timer = new Timer(3000, e -> {
            ((Timer) e.getSource()).stop();
            accionDespues.run();
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void iniciarPrimerNivel() {
        mostrarTransicion("cero_fixes.png", () -> {
            crearVistaJuego();
            cardLayout.show(contenedor, CARD_JUEGO);
        });
    }

    private void crearVistaJuego() {
        juego = new FlowFreeJuego(nivelInicial);

        PanelJuegoFondo panelJuego = new PanelJuegoFondo();
        panelJuego.setLayout(new BorderLayout());

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 22));
        lblEstado.setForeground(new Color(0xFF03FF));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        panelTablero = new PanelTablero(juego, lblEstado, this);

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnReiniciar.addActionListener(e -> {
            juego.reiniciar();
            actualizarEstado();
            panelTablero.repaint();
        });

        panelJuego.add(lblEstado, BorderLayout.NORTH);
        panelJuego.add(panelTablero, BorderLayout.CENTER);
        panelJuego.add(crearBarraLateral(), BorderLayout.WEST);

        contenedor.add(panelJuego, CARD_JUEGO);

        timerGUI = new Timer(500, e -> actualizarEstado());
        timerGUI.start();

        actualizarEstado();
        audioManager.iniciarMusicaPartida();
    }

    private JPanel crearBarraLateral() {
        JPanel barra = new JPanel();
        barra.setBackground(new Color(0x04054D));
        barra.setPreferredSize(new Dimension(80, 0));
        barra.setLayout(new BoxLayout(barra, BoxLayout.Y_AXIS));
        barra.setBorder(BorderFactory.createEmptyBorder(25, 14, 25, 14));

        JButton btnVolume = crearBotonBarra("volume.png", "SFX Volume");
        JButton btnSoundOff = crearBotonBarra("sound_off.png", "Mute Sound");
        JButton btnMusic = crearBotonBarra("music.png", "Music Volume");
        JButton btnHome = crearBotonBarra("home.png", "Home");
        JButton btnRestart = crearBotonBarra("restart_level.png", "Restart Level");
        JButton btnReverse = crearBotonBarra("reverse_one_move.png", "Undo Last Move");

        btnVolume.addActionListener(e -> audioManager.mostrarControlVolumenSFX(this));

        btnSoundOff.addActionListener(e -> audioManager.alternarMuteGeneral());

        btnMusic.addActionListener(e -> audioManager.mostrarControlVolumenMusica(this));

        btnHome.addActionListener(e -> confirmarSalidaAlMenu());

        btnRestart.addActionListener(e -> {
            juego.reiniciar();
            actualizarEstado();
            panelTablero.repaint();
        });

        btnReverse.addActionListener(e -> {
            juego.deshacerUltimoMovimiento();
            panelTablero.repaint();
        });

        barra.add(btnVolume);
        barra.add(Box.createVerticalStrut(10));
        barra.add(btnSoundOff);
        barra.add(Box.createVerticalStrut(10));
        barra.add(btnMusic);

        barra.add(Box.createVerticalGlue());

        barra.add(btnHome);
        barra.add(Box.createVerticalStrut(10));
        barra.add(btnRestart);
        barra.add(Box.createVerticalStrut(10));
        barra.add(btnReverse);

        return barra;
    }

    private JButton crearBotonBarra(String nombreImagen, String tooltip) {
        String ruta = ASSETS_DIR + "buttons/" + nombreImagen;

        ImageIcon iconoOriginal = new ImageIcon(ruta);
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(52, 52, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(imagenEscalada);

        JButton boton = new JButton(icono);
        boton.setToolTipText(tooltip);

        boton.setPreferredSize(new Dimension(52, 52));
        boton.setMaximumSize(new Dimension(52, 52));
        boton.setMinimumSize(new Dimension(52, 52));

        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        boton.setBorder(null);

        return boton;
    }

    private void confirmarSalidaAlMenu() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "Do you want to abandon this game?\nYour progress for this unfinished level will not be saved.",
                "Leave Game?",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.OK_OPTION) {
            if (timerGUI != null) {
                timerGUI.stop();
            }

            audioManager.detenerMusica();

            dispose();
        }
    }

    private void mostrarNivelCompletado(int nivelCompletado) {

        guardarProgresoNivel(nivelCompletado);

        String nombreImagen = "level" + nivelCompletado + "_completed.png";

        mostrarTransicion(nombreImagen, () -> {
            if (juego.haySiguienteNivel()) {
                juego.avanzarNivel();
                actualizarEstado();
                panelTablero.repaint();
                cardLayout.show(contenedor, CARD_JUEGO);
            } else {
                cardLayout.show(contenedor, CARD_JUEGO);
                JOptionPane.showMessageDialog(
                        this,
                        "All levels completed!",
                        "Game Completed",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    private void actualizarEstado() {
        if (juego == null || lblEstado == null) {
            return;
        }

        lblEstado.setText(
                "Level " + juego.getNivelActual()
                + " - " + juego.getNombreGrupoNivel()
                + " | Time: " + juego.getSegundosJugados() + "s"
        );
    }

    public static void abrir() {
        SwingUtilities.invokeLater(() -> {
            FlowFreeGUI ventana = new FlowFreeGUI();
            ventana.setVisible(true);
        });
    }

    private class PanelIntro extends JPanel {

        private BufferedImage imagenFondo;

        public PanelIntro() {
            setLayout(new BorderLayout());
            setBackground(Color.BLACK);

            try {
                imagenFondo = ImageIO.read(new File(ASSETS_DIR + "peters_message.png"));
            } catch (IOException e) {
                imagenFondo = null;
            }

            JPanel panelBoton = new JPanel();
            panelBoton.setOpaque(false);
            panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 35, 0));

            JButton btnContinue = new JButton("CONTINUE");
            btnContinue.setFont(new Font("Monospaced", Font.BOLD, 22));
            btnContinue.setForeground(Color.DARK_GRAY);
            btnContinue.setBackground(new Color(0xD4D4D4));
            btnContinue.setFocusPainted(false);
            btnContinue.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            btnContinue.setPreferredSize(new Dimension(180, 50));

            btnContinue.addActionListener(e -> iniciarPrimerNivel());

            panelBoton.add(btnContinue);

            add(panelBoton, BorderLayout.SOUTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            dibujarImagenEscalada(g, imagenFondo);
        }
    }

    private class PanelImagen extends JPanel {

        private BufferedImage imagen;

        public PanelImagen(String rutaImagen) {
            setBackground(Color.BLACK);

            try {
                imagen = ImageIO.read(new File(rutaImagen));
            } catch (IOException e) {
                imagen = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            dibujarImagenEscalada(g, imagen);
        }
    }

    private void dibujarImagenEscalada(Graphics g, BufferedImage imagen) {
        if (imagen == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int panelW = getWidth();
        int panelH = getHeight();

        int imgW = imagen.getWidth();
        int imgH = imagen.getHeight();

        double escala = Math.min((double) panelW / imgW, (double) panelH / imgH);

        int nuevoW = (int) (imgW * escala);
        int nuevoH = (int) (imgH * escala);

        int x = (panelW - nuevoW) / 2;
        int y = (panelH - nuevoH) / 2;

        g2.drawImage(imagen.getScaledInstance(nuevoW, nuevoH, Image.SCALE_FAST), x, y, null);
    }

    private class PanelJuegoFondo extends JPanel {

        private BufferedImage fondo;

        public PanelJuegoFondo() {
            try {
                fondo = ImageIO.read(new File(ASSETS_DIR + "background_puzzles.png"));
            } catch (IOException e) {
                fondo = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (fondo != null) {
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }

    private static class PanelTablero extends JPanel {

        private FlowFreeJuego juego;
        private JLabel lblEstado;
        private FlowFreeGUI ventana;

        private int margen;
        private int tamanoCelda;
        private int inicioX;
        private int inicioY;

        private BufferedImage fondoPuzzle;

        public PanelTablero(FlowFreeJuego juego, JLabel lblEstado, FlowFreeGUI ventana) {
            this.juego = juego;
            this.lblEstado = lblEstado;
            this.ventana = ventana;

            setOpaque(false);

            try {
                fondoPuzzle = ImageIO.read(
                        new File("src/ashley/galatea/progra2/proyecto2/assets/background_puzzles.png")
                );
            } catch (IOException e) {
                fondoPuzzle = null;
            }

            MouseAdapter mouse = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point celda = obtenerCelda(e.getX(), e.getY());

                    if (celda != null) {
                        if (juego.getPunto(celda.y, celda.x) != FlowFreeJuego.VACIO) {
                            ventana.audioManager.reproducirSFX("juniorsoundays-ui-sound-12-527794.mp3");
                        }

                        juego.iniciarCamino(celda.y, celda.x);
                        repaint();
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    Point celda = obtenerCelda(e.getX(), e.getY());

                    if (celda != null) {
                        juego.arrastrarCamino(celda.y, celda.x);
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                    boolean lineaCompleta = juego.caminoActivoCompleto();

                    juego.terminarCamino();

                    if (lineaCompleta) {
                        ventana.audioManager.reproducirSFX("juniorsoundays-ui-sound-12-527794.mp3");
                    }

                    if (juego.hayVictoria()) {
                        int nivelCompletado = juego.getNivelActual();
                        int tiempoFinal = juego.getSegundosJugados();

                        JOptionPane.showMessageDialog(
                                PanelTablero.this,
                                "Level " + nivelCompletado + " Completed!\nTime: " + tiempoFinal + " seconds",
                                "Level Completed",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        ventana.mostrarNivelCompletado(nivelCompletado);
                    }

                    repaint();
                }
            };

            addMouseListener(mouse);
            addMouseMotionListener(mouse);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            calcularMedidas();

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            dibujarCaminos(g2);
            dibujarPuntos(g2);
        }

        private void calcularMedidas() {
            margen = 40;
            int espacioDisponible = Math.min(getWidth(), getHeight()) - margen * 2;

            int mayorDimension = Math.max(juego.getFilas(), juego.getColumnas());

            tamanoCelda = espacioDisponible / mayorDimension;

            int tableroAncho = tamanoCelda * juego.getColumnas();
            int tableroAlto = tamanoCelda * juego.getFilas();

            inicioX = (getWidth() - tableroAncho) / 2;
            inicioY = (getHeight() - tableroAlto) / 2;
        }

        private void dibujarCaminos(Graphics2D g2) {
            HashMap<Integer, ArrayList<Point>> caminos = juego.getCaminos();

            for (Integer color : caminos.keySet()) {
                ArrayList<Point> camino = caminos.get(color);

                if (camino.size() < 2) {
                    continue;
                }

                g2.setColor(juego.getColorJava(color));
                g2.setStroke(new BasicStroke(tamanoCelda / 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                for (int i = 0; i < camino.size() - 1; i++) {
                    Point a = camino.get(i);
                    Point b = camino.get(i + 1);

                    g2.drawLine(centroX(a.x), centroY(a.y), centroX(b.x), centroY(b.y));
                }
            }
        }

        private void dibujarPuntos(Graphics2D g2) {
            int tamanoPunto = (int) (tamanoCelda * 0.68);
            int arco = tamanoPunto / 3;

            for (int fila = 0; fila < juego.getFilas(); fila++) {
                for (int columna = 0; columna < juego.getColumnas(); columna++) {
                    int color = juego.getPunto(fila, columna);

                    if (color != FlowFreeJuego.VACIO) {
                        int x = inicioX + columna * tamanoCelda + (tamanoCelda - tamanoPunto) / 2;
                        int y = inicioY + fila * tamanoCelda + (tamanoCelda - tamanoPunto) / 2;

                        g2.setColor(new Color(0, 0, 0, 45));
                        g2.fillRoundRect(x, y + 6, tamanoPunto, tamanoPunto, arco, arco);

                        g2.setColor(juego.getColorJava(color));
                        g2.fillRoundRect(x, y, tamanoPunto, tamanoPunto, arco, arco);
                    }
                }
            }
        }

        private Point obtenerCelda(int mouseX, int mouseY) {
            int tableroAncho = tamanoCelda * juego.getColumnas();
            int tableroAlto = tamanoCelda * juego.getFilas();

            if (mouseX < inicioX || mouseX >= inicioX + tableroAncho) {
                return null;
            }

            if (mouseY < inicioY || mouseY >= inicioY + tableroAlto) {
                return null;
            }

            int columna = (mouseX - inicioX) / tamanoCelda;
            int fila = (mouseY - inicioY) / tamanoCelda;

            return new Point(columna, fila);
        }

        private int centroX(int columna) {
            return inicioX + columna * tamanoCelda + tamanoCelda / 2;
        }

        private int centroY(int fila) {
            return inicioY + fila * tamanoCelda + tamanoCelda / 2;
        }
    }

    private void guardarProgresoNivel(int nivelCompletado) {
        if (menus == null) {
            return;
        }

        int tiempoSegundos = juego.getSegundosJugados();
        long tiempoMinutos = tiempoSegundos / 60;

        if (tiempoMinutos == 0) {
            tiempoMinutos = 1;
        }

        int puntaje = calcularPuntaje(tiempoSegundos);

        menus.completarPuzzle(nivelCompletado, puntaje, tiempoMinutos);

        menus.registrarResultadoPartida(
                true,
                nivelCompletado,
                puntaje,
                tiempoSegundos,
                "Level completed in " + tiempoSegundos + " seconds"
        );
    }

    private int calcularPuntaje(int tiempoSegundos) {
        int puntajeBase = 1000;
        int penalizacion = tiempoSegundos * 5;
        int puntajeFinal = puntajeBase - penalizacion;

        if (puntajeFinal < 100) {
            puntajeFinal = 100;
        }

        return puntajeFinal;
    }
}