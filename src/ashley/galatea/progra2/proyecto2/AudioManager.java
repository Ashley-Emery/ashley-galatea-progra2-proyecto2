/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author USER
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class AudioManager {

    private static final String ASSETS_DIR = "src/ashley/galatea/progra2/proyecto2/assets/";

    private MediaPlayer musicaPlayer;

    private boolean sfxActivo = true;
    private boolean musicaActiva = true;

    private int volumenSFX = 80;
    private int volumenMusica = 60;

    private double posicionMusicaSegundos = 0;

    private Menus menus;

    static {
        new JFXPanel();
    }

    public AudioManager(Menus menus) {
        this.menus = menus;

        if (menus != null && menus.getUsuarioActual() != null) {
            Usuario u = menus.getUsuarioActual();

            this.volumenSFX = u.getVolumenSFX();
            this.volumenMusica = u.getVolumenMusica();
            this.sfxActivo = u.isSfxActivo();
            this.musicaActiva = u.isMusicaActiva();
            this.posicionMusicaSegundos = u.getPosicionMusicaSegundos();
        }
    }

    public void reproducirSFX(String archivo) {
        if (!sfxActivo || volumenSFX <= 0) {
            return;
        }

        Platform.runLater(() -> {
            try {
                File file = new File(ASSETS_DIR + archivo);
                Media media = new Media(file.toURI().toString());
                MediaPlayer sfx = new MediaPlayer(media);

                sfx.setVolume(volumenSFX / 100.0);
                sfx.setOnEndOfMedia(() -> sfx.dispose());
                sfx.play();

            } catch (Exception e) {
                System.out.println("No se pudo reproducir SFX: " + e.getMessage());
            }
        });
    }

    public void iniciarMusicaPartida() {
        if (!musicaActiva || volumenMusica <= 0) {
            return;
        }

        Platform.runLater(() -> {
            try {
                if (musicaPlayer != null) {
                    musicaPlayer.play();
                    return;
                }

                File file = new File(ASSETS_DIR + "long-day.mp3");
                Media media = new Media(file.toURI().toString());

                musicaPlayer = new MediaPlayer(media);
                musicaPlayer.setVolume(volumenMusica / 100.0);
                musicaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                musicaPlayer.setOnReady(() -> {
                    if (posicionMusicaSegundos > 0) {
                        musicaPlayer.seek(Duration.seconds(posicionMusicaSegundos));
                    }
                    musicaPlayer.play();
                });

            } catch (Exception e) {
                System.out.println("No se pudo reproducir música: " + e.getMessage());
            }
        });
    }

    public void detenerMusica() {
        guardarEstadoActual();

        Platform.runLater(() -> {
            if (musicaPlayer != null) {
                musicaPlayer.stop();
                musicaPlayer.dispose();
                musicaPlayer = null;
            }
        });
    }

    public void alternarMuteGeneral() {
        boolean nuevoEstado = !(sfxActivo || musicaActiva);

        sfxActivo = nuevoEstado;
        musicaActiva = nuevoEstado;

        Platform.runLater(() -> {
            if (musicaPlayer != null) {
                musicaPlayer.setMute(!musicaActiva);
            }
        });

        guardarConfigAudio();
    }

    public void mostrarControlVolumenSFX(JFrame parent) {
        JDialog dialogo = crearDialogo(parent, "SFX Volume", "SFX Volume", true);
        dialogo.setVisible(true);
    }

    public void mostrarControlVolumenMusica(JFrame parent) {
        JDialog dialogo = crearDialogo(parent, "Music Volume", "Music Volume", false);
        dialogo.setVisible(true);
    }

    private JDialog crearDialogo(JFrame parent, String titulo, String texto, boolean esSFX) {
        JDialog dialogo = new JDialog(parent, titulo, true);
        dialogo.setSize(320, 120);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setLayout(new BorderLayout());

        int valor = esSFX ? volumenSFX : volumenMusica;

        JLabel lblVolumen = new JLabel(texto + ": " + valor + "%", SwingConstants.CENTER);
        lblVolumen.setFont(new Font("Arial", Font.BOLD, 16));

        JSlider slider = new JSlider(0, 100, valor);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.addChangeListener(e -> {
            if (esSFX) {
                setVolumenSFX(slider.getValue());
                lblVolumen.setText("SFX Volume: " + volumenSFX + "%");
            } else {
                setVolumenMusica(slider.getValue());
                lblVolumen.setText("Music Volume: " + volumenMusica + "%");
            }
        });

        dialogo.add(lblVolumen, BorderLayout.NORTH);
        dialogo.add(slider, BorderLayout.CENTER);

        return dialogo;
    }

    public void setVolumenSFX(int volumenSFX) {
        this.volumenSFX = volumenSFX;
        this.sfxActivo = volumenSFX > 0;
        guardarConfigAudio();
    }

    public void setVolumenMusica(int volumenMusica) {
        this.volumenMusica = volumenMusica;
        this.musicaActiva = volumenMusica > 0;

        Platform.runLater(() -> {
            if (musicaPlayer != null) {
                musicaPlayer.setVolume(volumenMusica / 100.0);
                musicaPlayer.setMute(!musicaActiva);
            }
        });

        guardarConfigAudio();
    }

    public void guardarEstadoActual() {
        Platform.runLater(() -> {
            if (musicaPlayer != null) {
                posicionMusicaSegundos = musicaPlayer.getCurrentTime().toSeconds();
                guardarConfigAudio();
            }
        });
    }

    private void guardarConfigAudio() {
        if (menus != null) {
            menus.actualizarConfigAudio(
                    volumenSFX,
                    volumenMusica,
                    sfxActivo,
                    musicaActiva,
                    posicionMusicaSegundos
            );
        }
    }
}
