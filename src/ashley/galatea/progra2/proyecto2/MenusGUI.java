/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author ashley
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;
import java.io.File;

public class MenusGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel cards;
    private Menus menus;
    private Font arcadeFont;
    
    private final String CARD_LOADING = "Loading";
    private final String CARD_LANGUAGE = "Language";
    private final String CARD_MENU_INICIO = "Menu Inicio";
    private final String CARD_LOGIN = "Log In";
    private final String CARD_SIGNIN = "Sign In";

    private final String CARD_MENU_PRINCIPAL = "Menu Principal";
    private final String CARD_INFO_NIVELES = "Info Niveles";
    private final String CARD_MY_ACTIVITY = "My Activity";
    private final String CARD_CHALLENGE = "Challenge";

    private final String CARD_ARCADE_GIF = "Arcade Gif";
    private final String CARD_INICIO_CHALLENGE = "Inicio Challenge";

    private final String CARD_FRIENDS_HUB = "Friends Hub";
    private final String CARD_FIND_FRIENDS = "Find Friends";

    private final String CARD_MY_PROFILE = "My Profile";
    private final String CARD_DISABLE_ACCOUNT = "Disable Account";
    private final String CARD_DELETE_ACCOUNT = "Delete Account";
    private final String CARD_ROTATE_PASSWORD = "Rotate Password";
    private final String CARD_MY_AVATAR = "My Avatar";

    private final String CARD_MY_STATS = "My Stats";
    private final String CARD_GENERAL_RANKING = "General Ranking";
    private final String CARD_FRIENDS_RANKING = "Friends Ranking";
    private final String CARD_COMPARE_STATS = "Compare Stats";

    private final String CARD_WHATS_NEW = "Whats New";
    private final String CARD_SETTINGS = "Settings";

    private boolean loginPendienteDespuesIdioma = false;
    private String usernamePendienteIdioma = "";
    private String passwordPendienteIdioma = "";

    private JPanel playCardActual;
    private JPanel challengeCardActual;

    public MenusGUI() {
        menus = new Menus();
        cargarFuente();

        setTitle("Flow Free");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(screen.width * 0.82);
        int height = (int)(screen.height * 0.78);

        setSize(width, height);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        cards.add(loadingCard(), CARD_LOADING);
        cards.add(languageCard(true), CARD_LANGUAGE);
        cards.add(menuInicioCard(), CARD_MENU_INICIO);
        cards.add(logInCard(), CARD_LOGIN);
        cards.add(signInCard(), CARD_SIGNIN);

        add(cards);
        cardLayout.show(cards, CARD_LOADING);

        setVisible(true);
    }

    private JPanel loadingCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JFXPanel fxPanel = new JFXPanel();
        panel.add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            try {
                File archivo = new File("src/ashley/galatea/progra2/proyecto2/assets/loading.mp4");

                Media media = new Media(archivo.toURI().toString());
                MediaPlayer player = new MediaPlayer(media);
                MediaView mediaView = new MediaView(player);

                StackPane root = new StackPane();
                root.setStyle("-fx-background-color: black;");
                root.getChildren().add(mediaView);

                mediaView.setPreserveRatio(true);
                mediaView.fitWidthProperty().bind(root.widthProperty());
                mediaView.fitHeightProperty().bind(root.heightProperty());

                Scene scene = new Scene(root);
                fxPanel.setScene(scene);

                player.setOnEndOfMedia(() -> {
                    player.dispose();

                    SwingUtilities.invokeLater(() -> {
                        cardLayout.show(cards, CARD_LANGUAGE);
                    });
                });

                player.play();

            } catch (Exception e) {
                System.out.println("No se pudo cargar loading.mp4: " + e.getMessage());

                SwingUtilities.invokeLater(() -> {
                    cardLayout.show(cards, CARD_LANGUAGE);
                });
            }
        });

        return panel;
    }

    private JPanel languageCard(boolean permitirSkip) {
        BackgroundPanel panel = new BackgroundPanel("src/ashley/galatea/progra2/proyecto2/assets/background_basic.png");
        panel.setLayout(new GridBagLayout());

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("FLOW FREE", Color.WHITE, 42f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 55, 0);
        contenido.add(titulo, gbc);

        JLabel subtitulo = crearTexto("LANGUAGE PREFERENCE", new Color(0xD99B18), 15f);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 35, 0);
        contenido.add(subtitulo, gbc);

        JButton english = crearBoton("ENGLISH", new Color(0xC893C9));
        JButton spanish = crearBoton("SPANISH", new Color(0xC893C9));
        JButton skip = crearBoton("SKIP", new Color(0xE25B57));

        english.addActionListener(e -> {
            menus.seleccionarIdiomaTemporal("English");
            continuarDespuesDeSeleccionIdioma();
        });

        spanish.addActionListener(e -> {
            menus.seleccionarIdiomaTemporal("Spanish");
            continuarDespuesDeSeleccionIdioma();
        });

        skip.addActionListener(e -> {
            menus.omitirSeleccionIdiomaTemporal();
            cardLayout.show(cards, CARD_MENU_INICIO);
        });

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 22, 0);
        contenido.add(english, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        contenido.add(spanish, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(22, 0, 0, 0);
        skip.setEnabled(permitirSkip);
        contenido.add(skip, gbc);

        panel.add(contenido);

        return panel;
    }

    private JPanel menuInicioCard() {
        BackgroundPanel panel = new BackgroundPanel("src/ashley/galatea/progra2/proyecto2/assets/background_basic.png");
        panel.setLayout(new GridBagLayout());

        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(18, 0, 18, 0);

        JLabel titulo = new JLabel("FLOW FREE");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(arcadeFont.deriveFont(Font.PLAIN, 42f));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 70, 0);
        contenido.add(titulo, gbc);

        JButton login = crearBoton("LOG IN", new Color(0xC893C9));
        JButton signin = crearBoton("SIGN IN", new Color(0xC893C9));
        JButton exit = crearBoton("EXIT", new Color(0xE25B57));

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, CARD_LOGIN);
            }
        });

        signin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, CARD_SIGNIN);
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gbc.insets = new Insets(0, 0, 25, 0);
        gbc.gridy = 1;
        contenido.add(login, gbc);

        gbc.gridy = 2;
        contenido.add(signin, gbc);

        gbc.gridy = 3;
        contenido.add(exit, gbc);

        panel.add(contenido);

        return panel;
    }

    private JPanel logInCard() {
        BackgroundPanel panel = new BackgroundPanel("src/ashley/galatea/progra2/proyecto2/assets/background_basic.png");
        panel.setLayout(new GridBagLayout());

        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel titulo = new JLabel("[ LOG IN ]");
        titulo.setForeground(new Color(0xC893C9));
        titulo.setFont(arcadeFont.deriveFont(Font.PLAIN, 32f));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 65, 0);
        contenido.add(titulo, gbc);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.gridx = 0;
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.insets = new Insets(0, 0, 8, 0);

        JLabel userLabel = crearTexto("USER", Color.WHITE, 15f);
        JTextField userField = crearTextField();

        JLabel passLabel = crearTexto("PASSWORD", Color.WHITE, 15f);
        JPasswordField passField = crearPasswordField();

        JButton ojo = new JButton("◉");
        ojo.setPreferredSize(new Dimension(42, 28));
        ojo.setFocusPainted(false);
        ojo.setBorderPainted(false);
        ojo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final char echoOriginal = passField.getEchoChar();

        ojo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (passField.getEchoChar() == (char) 0) {
                    passField.setEchoChar(echoOriginal);
                } else {
                    passField.setEchoChar((char) 0);
                }
            }
        });

        fgbc.gridy = 0;
        form.add(userLabel, fgbc);

        fgbc.gridy = 1;
        form.add(userField, fgbc);

        fgbc.gridy = 2;
        fgbc.insets = new Insets(12, 0, 8, 0);
        form.add(passLabel, fgbc);

        JPanel passPanel = new JPanel(new GridBagLayout());
        passPanel.setOpaque(false);

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridy = 0;
        pgbc.insets = new Insets(0, 0, 0, 10);

        pgbc.gridx = 0;
        passPanel.add(passField, pgbc);

        pgbc.gridx = 1;
        pgbc.insets = new Insets(0, 0, 0, 0);
        passPanel.add(ojo, pgbc);

        fgbc.gridy = 3;
        fgbc.insets = new Insets(0, 0, 22, 0);
        fgbc.anchor = GridBagConstraints.WEST;
        form.add(passPanel, fgbc);

        JButton btnLogin = crearBoton("LOG IN", new Color(0xC893C9));
        JButton btnBack = crearBoton("BACK", new Color(0xE25B57));

        JPanel botones = new JPanel(new GridBagLayout());
        botones.setOpaque(false);

        GridBagConstraints bgbc = new GridBagConstraints();
        bgbc.gridy = 0;
        bgbc.insets = new Insets(0, 0, 0, 24);
        bgbc.anchor = GridBagConstraints.WEST;

        bgbc.gridx = 0;
        botones.add(btnLogin, bgbc);

        bgbc.gridx = 1;
        bgbc.insets = new Insets(0, 0, 0, 0);
        botones.add(btnBack, bgbc);

        fgbc.gridy = 4;
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.insets = new Insets(0, 0, 0, 0);
        form.add(botones, fgbc);

        JLabel signup = crearTexto("NOT REGISTERED YET? SIGN UP>", Color.WHITE, 13f);
        signup.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signup.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cards, CARD_SIGNIN);
            }
        });

        fgbc.gridy = 5;
        fgbc.insets = new Insets(25, 12, 0, 0);
        form.add(signup, fgbc);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, CARD_MENU_INICIO);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                String respuesta = menus.login(username, password);

                if (respuesta.equals("Welcome")) {
                    JOptionPane.showMessageDialog(null, respuesta);
                    cards.add(menuPrincipalCard(), CARD_MENU_PRINCIPAL);
                    cardLayout.show(cards, CARD_MENU_PRINCIPAL);

                } else if (respuesta.equals("Account disabled. To proceed reactivate your account.")) {
                    int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "This account is disabled.\nDo you want to reactivate it?",
                        "Reactivate Account",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        String reactivar = menus.reactivarCuenta(username, password);
                        JOptionPane.showMessageDialog(null, reactivar);

                        if (reactivar.equals("Account restored successfully.")) {
                            cards.add(menuPrincipalCard(), CARD_MENU_PRINCIPAL);
                            cardLayout.show(cards, CARD_MENU_PRINCIPAL);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, respuesta);
                }
            }
        });

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        contenido.add(form, gbc);

        panel.add(contenido);

        return panel;
    }

    private JPanel signInCard() {
        BackgroundPanel panel = new BackgroundPanel("src/ashley/galatea/progra2/proyecto2/assets/background_basic.png");
        panel.setLayout(new GridBagLayout());

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = new JLabel("[ SIGN IN ]");
        titulo.setForeground(new Color(0xC893C9));
        titulo.setFont(arcadeFont.deriveFont(Font.PLAIN, 32f));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 45, 0);
        contenido.add(titulo, gbc);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.gridx = 0;
        fgbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = crearTexto("NAME", Color.WHITE, 15f);
        JTextField nameField = crearTextField();

        JLabel userLabel = crearTexto("USER", Color.WHITE, 15f);
        JTextField userField = crearTextField();

        JLabel passLabel = crearTexto("PASSWORD", Color.WHITE, 15f);
        JPasswordField passField = crearPasswordField();

        JButton ojo = new JButton("◉");
        ojo.setPreferredSize(new Dimension(42, 28));
        ojo.setFocusPainted(false);
        ojo.setBorderPainted(false);
        ojo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final char echoOriginal = passField.getEchoChar();

        ojo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (passField.getEchoChar() == (char) 0) {
                    passField.setEchoChar(echoOriginal);
                } else {
                    passField.setEchoChar((char) 0);
                }
            }
        });

        fgbc.gridy = 0;
        fgbc.insets = new Insets(0, 0, 8, 0);
        form.add(nameLabel, fgbc);

        fgbc.gridy = 1;
        form.add(nameField, fgbc);

        fgbc.gridy = 2;
        fgbc.insets = new Insets(0, 0, 8, 0);
        form.add(userLabel, fgbc);

        fgbc.gridy = 3;
        form.add(userField, fgbc);

        fgbc.gridy = 4;
        fgbc.insets = new Insets(12, 0, 8, 0);
        form.add(passLabel, fgbc);

        JPanel passPanel = new JPanel(new GridBagLayout());
        passPanel.setOpaque(false);

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridy = 0;

        pgbc.gridx = 0;
        pgbc.insets = new Insets(0, 0, 0, 10);
        passPanel.add(passField, pgbc);

        pgbc.gridx = 1;
        pgbc.insets = new Insets(0, 0, 0, 0);
        passPanel.add(ojo, pgbc);

        fgbc.gridy = 5;
        fgbc.insets = new Insets(0, 0, 8, 0);
        form.add(passPanel, fgbc);

        JLabel check1 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text1 = crearTexto("CONTENER AL MENOS 8 CARACTERES", new Color(0xFFEAFF), 12f);

        JLabel check2 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text2 = crearTexto("INCLUIR AL MENOS UNA LETRA MAYÚSCULA (A-Z)", new Color(0xFFEAFF), 12f);

        JLabel check3 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text3 = crearTexto("INCLUIR AL MENOS UNA LETRA MINÚSCULA (a-z)", new Color(0xFFEAFF), 12f);

        JLabel check4 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text4 = crearTexto("CONTENER AL MENOS UN NÚMERO (0-9)", new Color(0xFFEAFF), 12f);

        JLabel check5 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text5 = crearTexto("INCLUIR AL MENOS UN CARÁCTER ESPECIAL (!@#$%&)", new Color(0xFFEAFF), 12f);

        JPanel reqPanel = new JPanel(new GridBagLayout());
        reqPanel.setOpaque(false);

        agregarReq(reqPanel, check1, text1, 0);
        agregarReq(reqPanel, check2, text2, 1);
        agregarReq(reqPanel, check3, text3, 2);
        agregarReq(reqPanel, check4, text4, 3);
        agregarReq(reqPanel, check5, text5, 4);

        fgbc.gridy = 6;
        fgbc.insets = new Insets(0, 6, 3, 0);
        form.add(reqPanel, fgbc);

        passField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String pass = new String(passField.getPassword());

                actualizarCheck(check1, menus.passwordTieneMinimoCaracteres(pass));
                actualizarCheck(check2, menus.passwordTieneMayuscula(pass));
                actualizarCheck(check3, menus.passwordTieneMinuscula(pass));
                actualizarCheck(check4, menus.passwordTieneNumero(pass));
                actualizarCheck(check5, menus.passwordTieneEspecial(pass));
            }
        });

        JButton btnSignIn = crearBoton("SIGN IN", new Color(0xC893C9));
        JButton btnBack = crearBoton("BACK", new Color(0xE25B57));

        JPanel botones = new JPanel(new GridBagLayout());
        botones.setOpaque(false);

        GridBagConstraints bgbc = new GridBagConstraints();
        bgbc.gridy = 0;
        bgbc.anchor = GridBagConstraints.WEST;

        bgbc.gridx = 0;
        bgbc.insets = new Insets(0, 0, 0, 24);
        botones.add(btnSignIn, bgbc);

        bgbc.gridx = 1;
        bgbc.insets = new Insets(0, 0, 0, 0);
        botones.add(btnBack, bgbc);

        fgbc.gridy = 11;
        fgbc.insets = new Insets(18, 0, 0, 0);
        form.add(botones, fgbc);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, CARD_MENU_INICIO);
            }
        });

        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nombreCompleto = nameField.getText();
                String username = userField.getText();
                String password = new String(passField.getPassword());

                String respuesta = menus.crearUsuario(username, password, nombreCompleto);

                if (respuesta.equals("Usuario creado correctamente.")) {
                    JOptionPane.showMessageDialog(null, respuesta);

                    if (!menus.idiomaTemporalSeleccionado()) {
                        loginPendienteDespuesIdioma = true;
                        usernamePendienteIdioma = username;
                        passwordPendienteIdioma = password;

                        cards.add(languageCard(false), CARD_LANGUAGE);
                        cardLayout.show(cards, CARD_LANGUAGE);
                        return;
                    }

                    menus.login(username, password);
                    cards.add(menuPrincipalCard(), CARD_MENU_PRINCIPAL);
                    cardLayout.show(cards, CARD_MENU_PRINCIPAL);

                } else {
                    JOptionPane.showMessageDialog(null, respuesta);
                }
            }
        });

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        contenido.add(form, gbc);

        panel.add(contenido);

        return panel;
    }

    private JPanel menuPrincipalCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        String username = "USER";
        if (menus.getUsuarioActual() != null) {
            username = menus.getUsuarioActual().getUsername().toUpperCase();
        }

        JLabel welcome = new JLabel(
            "<html><span style='color:white;'>[ WELCOME BACK, </span>"
            + "<span style='color:#c893c9;'>" + username + "</span>"
            + "<span style='color:white;'> ]</span></html>"
        );

        welcome.setFont(arcadeFont.deriveFont(Font.PLAIN, 26f));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 45, 0);
        derecho.add(welcome, gbc);

        JButton play = crearBoton("PLAY", new Color(0xC893C9));
        JButton challenge = crearBoton("CHALLENGE", new Color(0xC893C9));
        JButton activity = crearBoton("MY ACTIVITY", new Color(0xD99B18));

        
        play.addActionListener(e -> {
            mostrarPlayCardActualizado();
        });

        challenge.addActionListener(e -> {
            mostrarChallengeCardLimpio();
        });

        activity.addActionListener(e -> {
            cards.add(myActivityCard(), CARD_MY_ACTIVITY);
            cardLayout.show(cards, CARD_MY_ACTIVITY);
        });

        gbc.insets = new Insets(0, 0, 18, 0);
        gbc.gridy = 1;
        derecho.add(play, gbc);

        gbc.gridy = 2;
        derecho.add(challenge, gbc);

        gbc.gridy = 3;
        derecho.add(activity, gbc);

        return crearMenuLayout("HOME", derecho);
    }

    private JPanel playCard() {
        BackgroundPanel panel = new BackgroundPanel("src/ashley/galatea/progra2/proyecto2/assets/background_basic.png");
        panel.setLayout(new GridBagLayout());

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("FLOW FREE", Color.WHITE, 42f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 45, 0);
        contenido.add(titulo, gbc);

        JPanel gridNiveles = new JPanel(new GridLayout(2, 5, 28, 24));
        gridNiveles.setOpaque(false);

        for (int i = 1; i <= 10; i++) {
            JButton nivel = crearBotonNivel(i);
            gridNiveles.add(nivel);
        }

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 55, 0);
        contenido.add(gridNiveles, gbc);

        JButton back = crearBoton("GO BACK", new Color(0xD99B18));
        back.addActionListener(e -> cardLayout.show(cards, CARD_MENU_PRINCIPAL));

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        contenido.add(back, gbc);

        panel.add(contenido);

        return panel;
    }

    private JPanel challengeCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titulo = crearTexto("[ CHALLENGE ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 28, 0);
        derecho.add(titulo, gbc);

        JLabel opponentTitle = crearTexto("CHOOSE AN OPPONENT", new Color(0xC893C9), 16f);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 8, 0);
        derecho.add(opponentTitle, gbc);

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setOpaque(false);

        ButtonGroup opponentGroup = new ButtonGroup();

        ArrayList<String> oponentes = menus.obtenerOponentesDisponibles();

        for (int i = 0; i < oponentes.size(); i++) {
            JCheckBox check = crearCheckBox(oponentes.get(i).toUpperCase());
            check.setActionCommand(oponentes.get(i));
            opponentGroup.add(check);
            listaPanel.add(check);
        }

        JScrollPane scrollOponentes = crearScrollLista(listaPanel, 330, 180);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 18, 0);
        derecho.add(scrollOponentes, gbc);

        JLabel difficultyTitle = crearTexto("CHOOSE DIFFICULTY", new Color(0xC893C9), 16f);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 8, 0);
        derecho.add(difficultyTitle, gbc);

        JPanel difficultyPanel = new JPanel(new GridLayout(0, 1, 0, 2));
        difficultyPanel.setOpaque(false);

        ButtonGroup difficultyGroup = new ButtonGroup();

        String[] dificultades = {"NEON CIRCUIT", "POWER GRID", "VOLTAGE RUN", "ELECTRIC DRIFT", "OVERLOAD"};

        for (int i = 0; i < dificultades.length; i++) {
            JCheckBox check = crearCheckBox(dificultades[i]);
            check.setActionCommand(dificultades[i]);
            difficultyGroup.add(check);
            difficultyPanel.add(check);
        }

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 25, 0);
        derecho.add(difficultyPanel, gbc);

        JButton cancel = crearBotonBevel("CANCEL");
        JButton play = crearBotonBevel("PLAY");

        cancel.addActionListener(e -> cardLayout.show(cards, CARD_MENU_PRINCIPAL));

        play.addActionListener(e -> {
            if (opponentGroup.getSelection() == null || difficultyGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un oponente y una dificultad.");
                return;
            }

            String rival = opponentGroup.getSelection().getActionCommand();
            String dificultad = difficultyGroup.getSelection().getActionCommand();

            ChallengePartida challenge = menus.iniciarChallengePartida(rival, dificultad);

            if (challenge == null) {
                JOptionPane.showMessageDialog(null, "Could not start challenge.");
                return;
            }

            JOptionPane.showMessageDialog(null, "Challenge started.");

            mostrarArcadeGifAntesChallenge(challenge);
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        botones.setOpaque(false);
        botones.add(cancel);
        botones.add(play);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(botones, gbc);

        return crearMenuLayout("HOME", derecho);
    }

    private JPanel myActivityCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ MY ACTIVITY ]", new Color(0xC893C9), 28f);

        JButton back = new JButton("BACK");
        back.setFont(arcadeFont.deriveFont(Font.PLAIN, 14f));
        back.setBackground(new Color(0xD4D4D4));
        back.setForeground(Color.BLACK);
        back.setBorder(BorderFactory.createRaisedBevelBorder());
        back.setPreferredSize(new Dimension(85, 35));
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        back.addActionListener(e -> cardLayout.show(cards, CARD_MENU_PRINCIPAL));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(titulo, BorderLayout.CENTER);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        derecho.add(top, gbc);

        JLabel accountTitle = crearTexto("ACCOUNT ACTIVITY", Color.WHITE, 13f);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 6, 0);
        derecho.add(accountTitle, gbc);

        JTable accountTable = crearTablaActividad(menus.obtenerAccountActivity());
        JScrollPane accountScroll = crearScrollTabla(accountTable);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        derecho.add(accountScroll, gbc);

        JLabel gameTitle = crearTexto("GAME ACTIVITY", Color.WHITE, 13f);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 6, 0);
        derecho.add(gameTitle, gbc);

        JTable gameTable = crearTablaActividad(menus.obtenerGameActivity());
        JScrollPane gameScroll = crearScrollTabla(gameTable);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(gameScroll, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(18, 0, 0, 0);
        derecho.add(back, gbc);

        return crearMenuLayout("HOME", derecho);
    }

    private JPanel friendsHubCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ FRIENDS HUB ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 28, 0);
        derecho.add(titulo, gbc);

        JButton remove = crearBotonBevel("REMOVE FRIEND(S)");
        remove.setPreferredSize(new Dimension(180, 35));

        JButton find = crearBotonBevel("FIND FRIEND(S)");
        find.setPreferredSize(new Dimension(160, 35));

        JPanel botonesTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        botonesTop.setOpaque(false);
        botonesTop.add(remove);
        botonesTop.add(find);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 35, 0);
        derecho.add(botonesTop, gbc);

        ArrayList<String> amigos = menus.obtenerAmigosActuales();

        JLabel subtitulo = crearTexto("FRIENDS (" + amigos.size() + ")", Color.WHITE, 18f);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);
        derecho.add(subtitulo, gbc);

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setOpaque(false);

        ArrayList<JCheckBox> checks = new ArrayList<JCheckBox>();

        for (int i = 0; i < amigos.size(); i++) {
            JCheckBox check = crearCheckBox(amigos.get(i).toUpperCase());
            check.setActionCommand(amigos.get(i));
            checks.add(check);
            listaPanel.add(check);
        }

        JScrollPane scrollAmigos = crearScrollLista(listaPanel, 330, 260);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(scrollAmigos, gbc);

        remove.addActionListener(e -> {
            ArrayList<String> seleccionados = new ArrayList<String>();

            for (int i = 0; i < checks.size(); i++) {
                if (checks.get(i).isSelected()) {
                    seleccionados.add(checks.get(i).getActionCommand());
                }
            }

            String respuesta = menus.eliminarAmigos(seleccionados);
            JOptionPane.showMessageDialog(null, respuesta);

            cards.add(friendsHubCard(), CARD_FRIENDS_HUB);
            cardLayout.show(cards, CARD_FRIENDS_HUB);
        });

        find.addActionListener(e -> {
            cards.add(findFriendsCard(), CARD_FIND_FRIENDS);
            cardLayout.show(cards, CARD_FIND_FRIENDS);
        });

        return crearMenuLayout("FRIENDS HUB", derecho);
    }

    private JPanel findFriendsCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ FIND FRIENDS ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 28, 0);
        derecho.add(titulo, gbc);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(290, 36));
        searchField.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JButton searchBtn = crearBotonBevel("⌕");
        searchBtn.setPreferredSize(new Dimension(45, 36));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 18, 0);
        derecho.add(searchPanel, gbc);

        JButton add = crearBotonBevel("ADD FRIEND");
        add.setPreferredSize(new Dimension(130, 35));

        JButton cancel = crearBotonBevel("CANCEL");
        cancel.setPreferredSize(new Dimension(95, 35));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        botones.setOpaque(false);
        botones.add(add);
        botones.add(cancel);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 28, 0);
        derecho.add(botones, gbc);

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setOpaque(false);

        ArrayList<JCheckBox> checks = new ArrayList<JCheckBox>();

        cargarUsuariosFindFriends(listaPanel, checks, "");

        JScrollPane scrollUsuarios = crearScrollLista(listaPanel, 330, 260);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(scrollUsuarios, gbc);

        searchBtn.addActionListener(e -> {
            String filtro = searchField.getText().trim();

            cargarUsuariosFindFriends(listaPanel, checks, filtro);
            listaPanel.revalidate();
            listaPanel.repaint();
        });

        add.addActionListener(e -> {
            ArrayList<String> seleccionados = new ArrayList<String>();

            for (int i = 0; i < checks.size(); i++) {
                if (checks.get(i).isSelected()) {
                    seleccionados.add(checks.get(i).getActionCommand());
                }
            }

            if (seleccionados.size() == 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un usuario.");
                return;
            }

            for (int i = 0; i < seleccionados.size(); i++) {
                menus.agregarAmigoBidireccional(seleccionados.get(i));
            }

            cards.add(friendsHubCard(), CARD_FRIENDS_HUB);
            cardLayout.show(cards, CARD_FRIENDS_HUB);
        });

        cancel.addActionListener(e -> {
            searchField.setText("");
            cards.add(friendsHubCard(), CARD_FRIENDS_HUB);
            cardLayout.show(cards, CARD_FRIENDS_HUB);
        });

        return crearMenuLayout("FRIENDS HUB", derecho);
    }

    private JPanel myProfileCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ MY PROFILE ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        derecho.add(titulo, gbc);

        JButton disable = crearBotonBevel("DISABLE ACCOUNT");
        disable.setPreferredSize(new Dimension(155, 35));

        JButton delete = crearBotonBevel("DELETE ACCOUNT");
        delete.setPreferredSize(new Dimension(145, 35));

        JButton rotate = crearBotonBevel("ROTATE PASSWORD");
        rotate.setPreferredSize(new Dimension(165, 35));

        JPanel botonesTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botonesTop.setOpaque(false);
        botonesTop.add(disable);
        botonesTop.add(delete);
        botonesTop.add(rotate);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 22, 0);
        derecho.add(botonesTop, gbc);

        JLabel nombre = crearTexto("[ " + menus.obtenerNombreCompletoPerfil() + " ]", new Color(0xFFEAFF), 18f);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        derecho.add(nombre, gbc);

        JPanel perfilPanel = new JPanel(new GridBagLayout());
        perfilPanel.setOpaque(false);

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridy = 0;
        pgbc.insets = new Insets(0, 0, 0, 25);

        JPanel avatarPanel = new JPanel(new GridBagLayout());
        avatarPanel.setOpaque(false);

        JLabel avatarBg = new JLabel();
        avatarBg.setOpaque(true);
        avatarBg.setBackground(menus.obtenerColorAvatarPerfil());
        avatarBg.setPreferredSize(new Dimension(135, 135));

        ImageIcon icon = new ImageIcon(menus.obtenerAvatarPathPerfil());
        Image img = icon.getImage().getScaledInstance(95, 95, Image.SCALE_SMOOTH);
        avatarBg.setIcon(new ImageIcon(img));
        avatarBg.setHorizontalAlignment(SwingConstants.CENTER);

        JButton changeAvatar = crearBotonBevel("CHANGE AVATAR");
        changeAvatar.setPreferredSize(new Dimension(125, 35));

        GridBagConstraints agbc = new GridBagConstraints();
        agbc.gridx = 0;
        agbc.gridy = 0;
        agbc.insets = new Insets(0, 0, 10, 0);
        avatarPanel.add(avatarBg, agbc);

        agbc.gridy = 1;
        agbc.insets = new Insets(0, 0, 0, 0);
        avatarPanel.add(changeAvatar, agbc);

        pgbc.gridx = 0;
        perfilPanel.add(avatarPanel, pgbc);

        JPanel datos = new JPanel(new GridBagLayout());
        datos.setOpaque(false);

        agregarFilaPerfil(datos, "USERNAME:", menus.obtenerUsernamePerfil(), 0);
        agregarFilaPerfil(datos, "STATUS:", menus.obtenerStatusPerfil(), 1);
        agregarFilaPerfil(datos, "REGISTERED ON:", menus.obtenerFechaRegistroPerfil(), 2);
        agregarFilaPerfil(datos, "LAST LOGIN:", menus.obtenerUltimoLoginPerfil(), 3);
        agregarFilaPerfil(datos, "LEVELS COMPLETED:", menus.obtenerNivelesCompletadosPerfil(), 4);
        agregarFilaPerfil(datos, "CHALLENGES WON:", menus.obtenerRetosGanadosPerfil(), 5);
        agregarFilaPerfil(datos, "SCORE:", menus.obtenerScorePerfil(), 6);
        agregarFilaPerfil(datos, "FRIENDS:", menus.obtenerCantidadAmigosPerfil(), 7);

        pgbc.gridx = 1;
        pgbc.insets = new Insets(0, 0, 0, 0);
        perfilPanel.add(datos, pgbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(perfilPanel, gbc);

        disable.addActionListener(e -> {
            cards.add(disableAccountCard(), CARD_DISABLE_ACCOUNT);
            cardLayout.show(cards, CARD_DISABLE_ACCOUNT);
        });

        
        delete.addActionListener(e -> {
            cards.add(deleteAccountCard(), CARD_DELETE_ACCOUNT);
            cardLayout.show(cards, CARD_DELETE_ACCOUNT);
        });

        
        rotate.addActionListener(e -> {
            cards.add(rotatePasswordCard(), CARD_ROTATE_PASSWORD);
            cardLayout.show(cards, CARD_ROTATE_PASSWORD);
        });

        
        changeAvatar.addActionListener(e -> {
            cards.add(myAvatarCard(), CARD_MY_AVATAR);
            cardLayout.show(cards, CARD_MY_AVATAR);
        });

        return crearMenuLayout("MY PROFILE", derecho);
    }

    private JPanel disableAccountCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ DISABLE ACCOUNT ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 55, 0);
        derecho.add(titulo, gbc);

        JLabel texto = crearTexto(
            "<html><div style='text-align:left;'>"
            + "BY DISABLING YOUR ACCOUNT, YOU WILL NOT<br>"
            + "BE ABLE TO LOG IN OR ACCESS YOUR DATA<br>"
            + "UNTIL YOU REACTIVATE IT AGAIN.<br><br><br>"
            + "DO YOU WANT TO CONTINUE?"
            + "</div></html>",
            Color.WHITE,
            14f
        );

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 35, 0);
        derecho.add(texto, gbc);

        JCheckBox confirmar = crearCheckBox("I UNDERSTAND AND CONFIRM THIS ACTION");
        confirmar.setForeground(new Color(0xC893C9));

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 25, 0);
        derecho.add(confirmar, gbc);

        JButton btnDisable = crearBotonBevel("DISABLE ACCOUNT");
        btnDisable.setPreferredSize(new Dimension(170, 35));
        btnDisable.setEnabled(false);

        confirmar.addActionListener(e -> {
            btnDisable.setEnabled(confirmar.isSelected());
        });

        btnDisable.addActionListener(e -> {
            String respuesta = menus.desactivarCuentaActual();
            JOptionPane.showMessageDialog(null, respuesta);

            limpiarCardsAutenticacion();
            cardLayout.show(cards, CARD_MENU_INICIO);
        });

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(btnDisable, gbc);

        return crearMenuLayout("MY PROFILE", derecho);
    }

    private JPanel deleteAccountCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ DELETE ACCOUNT ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 45, 0);
        derecho.add(titulo, gbc);

        JLabel texto = crearTexto(
            "<html><div style='text-align:left;'>"
            + "THIS ACTION IS PERMANENT. YOUR ACCOUNT<br>"
            + "AND ALL YOUR DATA WILL BE DELETED AND<br>"
            + "CANNOT BE RECOVERED.<br><br>"
            + "DELETING YOUR ACCOUNT WILL ERASE ALL<br>"
            + "YOUR PROGRESS PERMANENTLY.<br><br>"
            + "DO YOU WANT TO CONTINUE?"
            + "</div></html>",
            Color.WHITE,
            14f
        );

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 35, 0);
        derecho.add(texto, gbc);

        JCheckBox confirmar = crearCheckBox("I UNDERSTAND AND CONFIRM THIS ACTION");
        confirmar.setForeground(new Color(0xC893C9));

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 25, 0);
        derecho.add(confirmar, gbc);

        JButton btnDelete = crearBotonBevel("DELETE ACCOUNT");
        btnDelete.setPreferredSize(new Dimension(170, 35));
        btnDelete.setEnabled(false);

        confirmar.addActionListener(e -> {
            btnDelete.setEnabled(confirmar.isSelected());
        });

        btnDelete.addActionListener(e -> {
            String respuesta = menus.eliminarCuentaActual();
            JOptionPane.showMessageDialog(null, respuesta);

            if (respuesta.equals("Account deleted successfully.")) {
                limpiarCardsAutenticacion();
                cardLayout.show(cards, CARD_MENU_INICIO);
            }
        });

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(btnDelete, gbc);

        return crearMenuLayout("MY PROFILE", derecho);
    }

    private JPanel rotatePasswordCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ ROTATE PASSWORD ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        derecho.add(titulo, gbc);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.gridx = 0;
        fgbc.anchor = GridBagConstraints.WEST;

        JLabel currentLabel = crearTexto("CURRENT PASSWORD", Color.WHITE, 15f);
        JPasswordField currentField = crearPasswordField();

        JLabel newLabel = crearTexto("NEW PASSWORD", Color.WHITE, 15f);
        JPasswordField newField = crearPasswordField();

        JButton ojo = new JButton("◉");
        ojo.setPreferredSize(new Dimension(42, 28));
        ojo.setFocusPainted(false);
        ojo.setBorderPainted(false);
        ojo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final char echoOriginal = newField.getEchoChar();

        ojo.addActionListener(e -> {
            if (newField.getEchoChar() == (char) 0) {
                newField.setEchoChar(echoOriginal);
            } else {
                newField.setEchoChar((char) 0);
            }
        });

        fgbc.gridy = 0;
        fgbc.insets = new Insets(0, 0, 8, 0);
        form.add(currentLabel, fgbc);

        fgbc.gridy = 1;
        form.add(currentField, fgbc);

        fgbc.gridy = 2;
        fgbc.insets = new Insets(18, 0, 8, 0);
        form.add(newLabel, fgbc);

        JPanel newPassPanel = new JPanel(new GridBagLayout());
        newPassPanel.setOpaque(false);

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridy = 0;

        pgbc.gridx = 0;
        pgbc.insets = new Insets(0, 0, 0, 10);
        newPassPanel.add(newField, pgbc);

        pgbc.gridx = 1;
        pgbc.insets = new Insets(0, 0, 0, 0);
        newPassPanel.add(ojo, pgbc);

        fgbc.gridy = 3;
        fgbc.insets = new Insets(0, 0, 8, 0);
        form.add(newPassPanel, fgbc);

        JLabel check1 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text1 = crearTexto("CONTENER AL MENOS 8 CARACTERES", new Color(0xFFEAFF), 12f);

        JLabel check2 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text2 = crearTexto("INCLUIR AL MENOS UNA LETRA MAYÚSCULA (A-Z)", new Color(0xFFEAFF), 12f);

        JLabel check3 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text3 = crearTexto("INCLUIR AL MENOS UNA LETRA MINÚSCULA (a-z)", new Color(0xFFEAFF), 12f);

        JLabel check4 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text4 = crearTexto("CONTENER AL MENOS UN NÚMERO (0-9)", new Color(0xFFEAFF), 12f);

        JLabel check5 = crearTexto("✔", Color.WHITE, 17f);
        JLabel text5 = crearTexto("INCLUIR AL MENOS UN CARÁCTER ESPECIAL (!@#$%&)", new Color(0xFFEAFF), 12f);

        JPanel reqPanel = new JPanel(new GridBagLayout());
        reqPanel.setOpaque(false);

        agregarReq(reqPanel, check1, text1, 0);
        agregarReq(reqPanel, check2, text2, 1);
        agregarReq(reqPanel, check3, text3, 2);
        agregarReq(reqPanel, check4, text4, 3);
        agregarReq(reqPanel, check5, text5, 4);

        fgbc.gridy = 4;
        fgbc.insets = new Insets(0, 6, 25, 0);
        form.add(reqPanel, fgbc);

        newField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String pass = new String(newField.getPassword());

                actualizarCheck(check1, menus.passwordTieneMinimoCaracteres(pass));
                actualizarCheck(check2, menus.passwordTieneMayuscula(pass));
                actualizarCheck(check3, menus.passwordTieneMinuscula(pass));
                actualizarCheck(check4, menus.passwordTieneNumero(pass));
                actualizarCheck(check5, menus.passwordTieneEspecial(pass));
            }
        });

        JButton btnRotate = crearBotonBevel("ROTATE PASSWORD");
        btnRotate.setPreferredSize(new Dimension(180, 35));

        btnRotate.addActionListener(e -> {
            String actual = new String(currentField.getPassword());
            String nueva = new String(newField.getPassword());

            String respuesta = menus.cambiarPassword(actual, nueva);
            JOptionPane.showMessageDialog(null, respuesta);

            if (respuesta.equals("Password rotated successfully.")) {
                cards.add(myProfileCard(), CARD_MY_PROFILE);
                cardLayout.show(cards, CARD_MY_PROFILE);
            }
        });

        fgbc.gridy = 5;
        fgbc.anchor = GridBagConstraints.CENTER;
        fgbc.insets = new Insets(5, 0, 0, 0);
        form.add(btnRotate, fgbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(form, gbc);

        return crearMenuLayout("MY PROFILE", derecho);
    }

    private JPanel myAvatarCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ MY AVATAR ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 28, 0);
        derecho.add(titulo, gbc);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setOpaque(false);

        GridBagConstraints cgbc = new GridBagConstraints();

        final String[] avatarSeleccionado = {menus.obtenerAvatarActual()};
        final String[] colorSeleccionado = {menus.obtenerColorAvatarActual()};

        AvatarPreviewPanel preview = new AvatarPreviewPanel(
            avatarSeleccionado[0],
            colorSeleccionado[0]
        );

        JPanel avatarGrid = new JPanel(new GridLayout(3, 7, 10, 10));
        avatarGrid.setOpaque(false);

        ArrayList<JButton> botonesAvatar = new ArrayList<JButton>();
        ArrayList<String> avatares = menus.obtenerAvataresDisponibles();

        for (int i = 0; i < avatares.size(); i++) {
            String avatar = avatares.get(i);

            JButton btnAvatar = crearBotonAvatar(avatar);

            if (avatar.equals(avatarSeleccionado[0])) {
                btnAvatar.setBorder(BorderFactory.createLineBorder(new Color(0xE25B57), 3));
            }

            btnAvatar.addActionListener(e -> {
                avatarSeleccionado[0] = avatar;

                for (int j = 0; j < botonesAvatar.size(); j++) {
                    botonesAvatar.get(j).setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                }

                btnAvatar.setBorder(BorderFactory.createLineBorder(new Color(0xE25B57), 3));

                preview.actualizar(avatarSeleccionado[0], colorSeleccionado[0]);
            });

            botonesAvatar.add(btnAvatar);
            avatarGrid.add(btnAvatar);
        }

        cgbc.gridx = 0;
        cgbc.gridy = 0;
        cgbc.insets = new Insets(0, 0, 18, 35);
        contenido.add(avatarGrid, cgbc);

        cgbc.gridx = 1;
        cgbc.gridy = 0;
        cgbc.gridheight = 2;
        cgbc.insets = new Insets(0, 25, 0, 0);
        contenido.add(preview, cgbc);

        JPanel coloresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        coloresPanel.setOpaque(false);

        ArrayList<JButton> botonesColor = new ArrayList<JButton>();
        ArrayList<String> colores = menus.obtenerColoresAvatarDisponibles();

        for (int i = 0; i < colores.size(); i++) {
            String colorHex = colores.get(i);

            JButton btnColor = crearBotonColorAvatar(Color.decode(colorHex));

            if (colorHex.equals(colorSeleccionado[0])) {
                btnColor.setSelected(true);
            }

            btnColor.addActionListener(e -> {
                colorSeleccionado[0] = colorHex;

                for (int j = 0; j < botonesColor.size(); j++) {
                    botonesColor.get(j).setSelected(false);
                    botonesColor.get(j).repaint();
                }

                btnColor.setSelected(true);
                btnColor.repaint();

                preview.actualizar(avatarSeleccionado[0], colorSeleccionado[0]);
            });

            botonesColor.add(btnColor);
            coloresPanel.add(btnColor);
        }

        cgbc.gridx = 0;
        cgbc.gridy = 1;
        cgbc.gridheight = 1;
        cgbc.insets = new Insets(0, 0, 28, 35);
        contenido.add(coloresPanel, cgbc);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        botones.setOpaque(false);

        JButton cancel = crearBotonBevel("CANCEL");
        JButton save = crearBotonBevel("SAVE");

        cancel.setPreferredSize(new Dimension(95, 35));
        save.setPreferredSize(new Dimension(85, 35));

        cancel.addActionListener(e -> {
            cards.add(myProfileCard(), CARD_MY_PROFILE);
            cardLayout.show(cards, CARD_MY_PROFILE);
        });

        save.addActionListener(e -> {
            String respuesta = menus.guardarAvatarPerfil(avatarSeleccionado[0], colorSeleccionado[0]);
            JOptionPane.showMessageDialog(null, respuesta);

            if (respuesta.equals("Avatar saved successfully.") || respuesta.equals("No avatar changes detected.")) {
                cards.add(myProfileCard(), CARD_MY_PROFILE);
                cardLayout.show(cards, CARD_MY_PROFILE);
            }
        });

        botones.add(cancel);
        botones.add(save);

        cgbc.gridx = 0;
        cgbc.gridy = 2;
        cgbc.insets = new Insets(0, 0, 0, 35);
        contenido.add(botones, cgbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(contenido, gbc);

        return crearMenuLayout("MY PROFILE", derecho);
    }

    private void mostrarArcadeGifAntesChallenge(ChallengePartida challenge) {
        JPanel gifCard = arcadeGifCard();

        cards.add(gifCard, CARD_ARCADE_GIF);
        cardLayout.show(cards, CARD_ARCADE_GIF);

        Timer timerGif = new Timer(3000, e -> {
            ((Timer) e.getSource()).stop();

            cards.add(inicioChallengeCard(challenge), CARD_INICIO_CHALLENGE);
            cardLayout.show(cards, CARD_INICIO_CHALLENGE);

            Timer timerInicio = new Timer(3000, ev -> {
                ((Timer) ev.getSource()).stop();

                mostrarChallengeCardLimpio();

                FlowFreeGUI juego = new FlowFreeGUI(
                        menus,
                        this,
                        challenge.getNivel(),
                        true,
                        challenge.getId()
                );

                juego.setVisible(true);
            });

            timerInicio.setRepeats(false);
            timerInicio.start();
        });

        timerGif.setRepeats(false);
        timerGif.start();
    }

    private JPanel arcadeGifCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JLabel gif = new JLabel();
        gif.setHorizontalAlignment(SwingConstants.CENTER);
        gif.setVerticalAlignment(SwingConstants.CENTER);
        gif.setIcon(new ImageIcon("src/ashley/galatea/progra2/proyecto2/assets/arcade.gif"));

        panel.add(gif, BorderLayout.CENTER);

        return panel;
    }

    private JPanel inicioChallengeCard(ChallengePartida challenge) {
        BackgroundPanel panel = new BackgroundPanel("src/ashley/galatea/progra2/proyecto2/assets/backgroung_challenge.png");
        panel.setLayout(new GridBagLayout());

        JLabel mensaje = new JLabel(
            "<html><div style='text-align:center;'>"
            + "CHALLENGE STARTED<br><br>"
            + "The arcade won't repair itself.<br>"
            + "Time to prove your skills.<br><br>"
            + "Hope your rival knows how to handle loose wires."
            + "</div></html>"
        );

        mensaje.setForeground(Color.WHITE);
        mensaje.setFont(arcadeFont.deriveFont(Font.PLAIN, 24f));
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(mensaje);

        return panel;
    }

    private JPanel myStatsCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ MY STATS ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0);
        derecho.add(titulo, gbc);

        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 16, 18));
        statsPanel.setOpaque(false);

        statsPanel.add(crearCajaStat("GAMES PLAYED", menus.obtenerGamesPlayedStats()));
        statsPanel.add(crearCajaStat("LEVELS COMPLETED", menus.obtenerLevelsCompletedStats()));
        statsPanel.add(crearCajaStat("AVG. TIME PER LEVEL", menus.obtenerAvgTimePerLevelStats()));
        statsPanel.add(crearCajaStat("CHALLENGES WON", menus.obtenerChallengesWonStats()));
        statsPanel.add(crearCajaStat("SCORE", menus.obtenerScoreStats()));
        statsPanel.add(crearCajaStat("RANKING", menus.obtenerRankingStats()));

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 24, 0);
        derecho.add(statsPanel, gbc);

        JLabel rankingTitle = crearTexto("[ PERFORMANCE RANKINGS ]", new Color(0xFFEAFF), 15f);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 14, 0);
        derecho.add(rankingTitle, gbc);

        JPanel botonesRanking = new JPanel(new GridLayout(1, 2, 22, 0));
        botonesRanking.setOpaque(false);

        JButton general = crearBotonBevel("GENERAL RANKING");
        general.setPreferredSize(new Dimension(150, 35));

        general.addActionListener(e -> {
            cards.add(generalRankingCard(), CARD_GENERAL_RANKING);
            cardLayout.show(cards, CARD_GENERAL_RANKING);
        });

        JButton friends = crearBotonBevel("FRIENDS RANKING");
        friends.setPreferredSize(new Dimension(150, 35));

        friends.addActionListener(e -> {
            cards.add(friendsRankingCard(), CARD_FRIENDS_RANKING);
            cardLayout.show(cards, CARD_FRIENDS_RANKING);
        });

        botonesRanking.add(general);
        botonesRanking.add(friends);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 24, 0);
        derecho.add(botonesRanking, gbc);

        JLabel compareTitle = crearTexto("[ COMPARE WITH PLAYER ]", new Color(0xFFEAFF), 15f);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 12, 0);
        derecho.add(compareTitle, gbc);

        JTextField searchField = crearTextField();
        searchField.setPreferredSize(new Dimension(230, 32));

        JButton searchBtn = new JButton("⌕");
        searchBtn.setPreferredSize(new Dimension(45, 32));

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);

        GridBagConstraints sgbc = new GridBagConstraints();
        sgbc.gridy = 0;
        sgbc.gridx = 0;
        sgbc.insets = new Insets(0, 0, 0, 8);
        searchPanel.add(searchField, sgbc);

        sgbc.gridx = 1;
        sgbc.insets = new Insets(0, 0, 0, 0);
        searchPanel.add(searchBtn, sgbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 0);
        derecho.add(searchPanel, gbc);

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setOpaque(false);

        ArrayList<JCheckBox> checks = new ArrayList<JCheckBox>();
        cargarUsuariosCompareStats(listaPanel, checks, "");

        JScrollPane scroll = crearScrollLista(listaPanel, 280, 80);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 12, 0);
        derecho.add(scroll, gbc);

        searchBtn.addActionListener(e -> {
            cargarUsuariosCompareStats(listaPanel, checks, searchField.getText().trim());
            listaPanel.revalidate();
            listaPanel.repaint();
        });

        JButton compare = crearBotonBevel("COMPARE STATS");
        compare.setPreferredSize(new Dimension(140, 35));

        compare.addActionListener(e -> {
            String usernameSeleccionado = "";

            for (int i = 0; i < checks.size(); i++) {
                if (checks.get(i).isSelected()) {
                    usernameSeleccionado = checks.get(i).getActionCommand();
                }
            }

            if (usernameSeleccionado.length() == 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un jugador.");
                return;
            }

            cards.add(compareStatsCard(usernameSeleccionado), CARD_COMPARE_STATS);
            cardLayout.show(cards, CARD_COMPARE_STATS);
        });

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(compare, gbc);

        return crearMenuLayout("MY STATS", derecho);
    }

    private JPanel generalRankingCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ GENERAL RANKING ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        derecho.add(titulo, gbc);

        String[] columnas = {"#", "PLAYER", "SCORE", "LEVELS", "AVG TIME", "HOURS PLAYED"};

        ArrayList<String[]> ranking = menus.obtenerGeneralRanking();
        String[][] datos = new String[ranking.size()][6];

        for (int i = 0; i < ranking.size(); i++) {
            datos[i] = ranking.get(i);
        }

        JTable tabla = new JTable(datos, columnas);
        tabla.setOpaque(false);
        tabla.setBackground(new Color(0, 0, 0, 0));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(arcadeFont.deriveFont(Font.PLAIN, 13f));
        tabla.setRowHeight(24);
        tabla.setEnabled(false);
        tabla.setShowGrid(false);

        tabla.getTableHeader().setOpaque(true);
        tabla.getTableHeader().setBackground(new Color(0xD4D4D4));
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(arcadeFont.deriveFont(Font.PLAIN, 9f));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 24));

        javax.swing.table.DefaultTableCellRenderer headerRenderer =
                (javax.swing.table.DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();

        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(560, 230));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 32, 0);
        derecho.add(scroll, gbc);

        JButton back = crearBotonBevel("BACK");
        back.setPreferredSize(new Dimension(90, 38));

        back.addActionListener(e -> {
            cards.add(myStatsCard(), CARD_MY_STATS);
            cardLayout.show(cards, CARD_MY_STATS);
        });

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(back, gbc);

        return crearMenuLayout("MY STATS", derecho);
    }

    private JPanel friendsRankingCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ FRIENDS RANKING ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        derecho.add(titulo, gbc);

        String[] columnas = {"#", "PLAYER", "SCORE", "LEVELS", "AVG TIME", "HOURS PLAYED"};

        ArrayList<String[]> ranking = menus.obtenerFriendsRanking();
        String[][] datos = new String[ranking.size()][6];

        for (int i = 0; i < ranking.size(); i++) {
            datos[i] = ranking.get(i);
        }

        JTable tabla = new JTable(datos, columnas);
        tabla.setOpaque(false);
        tabla.setBackground(new Color(0, 0, 0, 0));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(arcadeFont.deriveFont(Font.PLAIN, 12f));
        tabla.setRowHeight(30);
        tabla.setEnabled(false);
        tabla.setShowGrid(false);

        tabla.getTableHeader().setOpaque(true);
        tabla.getTableHeader().setBackground(new Color(0xD4D4D4));
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(arcadeFont.deriveFont(Font.PLAIN, 11f));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 30));

        javax.swing.table.DefaultTableCellRenderer headerRenderer =
                (javax.swing.table.DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();

        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(560, 230));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 32, 0);
        derecho.add(scroll, gbc);

        JButton back = crearBotonBevel("BACK");
        back.setPreferredSize(new Dimension(90, 38));

        back.addActionListener(e -> {
            cards.add(myStatsCard(), CARD_MY_STATS);
            cardLayout.show(cards, CARD_MY_STATS);
        });

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(back, gbc);

        return crearMenuLayout("MY STATS", derecho);
    }

    private JPanel compareStatsCard(String usernameComparar) {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ COMPARE STATS ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        derecho.add(titulo, gbc);

        String[] columnas = {"#", "PLAYER", "SCORE", "LEVELS", "AVG TIME", "HOURS PLAYED"};

        ArrayList<String[]> ranking = menus.obtenerCompareStats(usernameComparar);
        String[][] datos = new String[ranking.size()][6];

        for (int i = 0; i < ranking.size(); i++) {
            datos[i] = ranking.get(i);
        }

        JTable tabla = new JTable(datos, columnas);
        tabla.setOpaque(false);
        tabla.setBackground(new Color(0, 0, 0, 0));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(arcadeFont.deriveFont(Font.PLAIN, 12f));
        tabla.setRowHeight(30);
        tabla.setEnabled(false);
        tabla.setShowGrid(false);

        tabla.getTableHeader().setOpaque(true);
        tabla.getTableHeader().setBackground(new Color(0xD4D4D4));
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(arcadeFont.deriveFont(Font.PLAIN, 11f));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 30));

        javax.swing.table.DefaultTableCellRenderer headerRenderer =
                (javax.swing.table.DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();

        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(560, 150));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 32, 0);
        derecho.add(scroll, gbc);

        JButton back = crearBotonBevel("BACK");
        back.setPreferredSize(new Dimension(90, 38));

        back.addActionListener(e -> {
            cards.add(myStatsCard(), CARD_MY_STATS);
            cardLayout.show(cards, CARD_MY_STATS);
        });

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(back, gbc);

        return crearMenuLayout("MY STATS", derecho);
    }

    private JPanel whatsNewCard() {
        JPanel derecho = new JPanel(new GridBagLayout());
        derecho.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel titulo = crearTexto("[ WHAT'S NEW ]", new Color(0xC893C9), 28f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0);
        derecho.add(titulo, gbc);

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setOpaque(false);

        ArrayList<ChallengePartida> pendientes = menus.obtenerChallengesPendientes();

        if (pendientes.size() == 0) {
            JLabel vacio = crearTexto("NO NEW CHALLENGES", Color.WHITE, 14f);
            lista.add(vacio);
        }

        for (int i = 0; i < pendientes.size(); i++) {
            lista.add(crearItemChallenge(pendientes.get(i)));

            if (i < pendientes.size() - 1) {
                JLabel linea = crearTexto("---------------------------------------------", Color.WHITE, 12f);
                lista.add(Box.createVerticalStrut(12));
                lista.add(linea);
                lista.add(Box.createVerticalStrut(12));
            }
        }

        JScrollPane scroll = crearScrollLista(lista, 500, 360);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        derecho.add(scroll, gbc);

        return crearMenuLayout("WHAT'S NEW", derecho);
    }

    private JPanel crearItemChallenge(ChallengePartida challenge) {
        JPanel item = new JPanel(new GridBagLayout());
        item.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = crearTexto("NEW CHALLENGE!", new Color(0xE5B7E6), 13f);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        item.add(title, gbc);

        JLabel retador = new JLabel(
                "<html><span style='color:#c893c9;'>"
                + challenge.getJugador1().toUpperCase()
                + "</span><span style='color:white;'> CHALLENGED YOU!</span></html>"
        );
        retador.setFont(arcadeFont.deriveFont(Font.PLAIN, 12f));

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 2, 0);
        item.add(retador, gbc);

        JLabel dificultad = new JLabel(
                "<html><span style='color:white;'>LEVEL: </span>"
                + "<span style='color:#c893c9;'>"
                + challenge.getDificultad()
                + "</span></html>"
        );
        dificultad.setFont(arcadeFont.deriveFont(Font.PLAIN, 12f));

        gbc.gridy = 2;
        item.add(dificultad, gbc);

        JLabel tiempo = crearTexto(menus.obtenerTiempoChallengeAgo(challenge), Color.WHITE, 12f);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 14, 0);
        item.add(tiempo, gbc);

        JButton accept = crearBotonTextoTransparente("[ ACCEPT ]");
        JButton decline = crearBotonTextoTransparente("[ DECLINE ]");

        accept.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Challenge accepted.");
            mostrarArcadeGifAntesChallenge(challenge);
        });

        decline.addActionListener(e -> {
            String respuesta = menus.declinarChallenge(challenge.getId());
            JOptionPane.showMessageDialog(null, respuesta);

            cards.add(whatsNewCard(), CARD_WHATS_NEW);
            cardLayout.show(cards, CARD_WHATS_NEW);
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 0));
        botones.setOpaque(false);
        botones.add(accept);
        botones.add(decline);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        item.add(botones, gbc);

        return item;
    }

    private JTable crearTablaActividad(ArrayList<Actividad> actividades) {
        String[] columnas = {"Time", "Log"};
        String[][] datos = new String[actividades.size()][2];

        for (int i = 0; i < actividades.size(); i++) {
            datos[i][0] = actividades.get(i).getTimestamp();
            datos[i][1] = actividades.get(i).getLog();
        }

        JTable tabla = new JTable(datos, columnas);

        tabla.setFont(new Font("Monospaced", Font.PLAIN, 11));
        tabla.getTableHeader().setFont(new Font("Monospaced", Font.BOLD, 11));

        tabla.setForeground(Color.WHITE);
        tabla.setBackground(new Color(0, 0, 0, 0));
        tabla.setOpaque(false);

        tabla.setGridColor(new Color(0xC8D5D8));
        tabla.setShowGrid(true);
        tabla.setRowHeight(22);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(500);

        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setBackground(new Color(0xD4D4D4));
        tabla.getTableHeader().setOpaque(true);
        tabla.getTableHeader().setReorderingAllowed(false);

        return tabla;
    }

    private JScrollPane crearScrollTabla(JTable tabla) {
        JScrollPane scroll = new JScrollPane(
            tabla,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setPreferredSize(new Dimension(520, 135));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xC8D5D8)));

        return scroll;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(arcadeFont.deriveFont(Font.PLAIN, 16f));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(210, 42));

        return boton;
    }

    private JLabel crearTexto(String texto, Color color, float size) {
        JLabel label = new JLabel(texto);
        label.setForeground(color);
        label.setFont(arcadeFont.deriveFont(Font.PLAIN, size));
        return label;
    }

    private JTextField crearTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(350, 30));
        field.setFont(new Font("Monospaced", Font.PLAIN, 16));
        field.setBorder(null);
        return field;
    }

    private JPasswordField crearPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(350, 30));
        field.setFont(new Font("Monospaced", Font.PLAIN, 16));
        field.setBorder(null);
        return field;
    }

    private void agregarReq(JPanel panel, JLabel check, JLabel texto, int fila) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = fila;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 3, 6);
        panel.add(check, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 3, 0);
        panel.add(texto, gbc);
    }

    private void actualizarCheck(JLabel check, boolean cumple) {
        if (cumple) {
            check.setForeground(new Color(0xEE46EC));
        } else {
            check.setForeground(Color.WHITE);
        }
    }

    private JPanel crearMenuLayout(String activo, JPanel contenidoDerecho) {
        BackgroundPanel panel = new BackgroundPanel("src/ashley/galatea/progra2/proyecto2/assets/background_menus.png");
        panel.setLayout(new BorderLayout());

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setOpaque(false);

        JPanel sidebarWrapper = new JPanel(new GridBagLayout());
        sidebarWrapper.setOpaque(false);
        sidebarWrapper.setPreferredSize(new Dimension(500, 500));

        GridBagConstraints sgbc = new GridBagConstraints();
        sgbc.gridx = 0;
        sgbc.gridy = 0;
        sgbc.anchor = GridBagConstraints.NORTH;
        sgbc.insets = new Insets(70, 0, 0, 0);

        sidebarWrapper.add(crearSidebar(activo), sgbc);

        JPanel rightWrapper = new JPanel(new GridBagLayout());
        rightWrapper.setOpaque(false);

        GridBagConstraints rgbc = new GridBagConstraints();
        rgbc.gridx = 0;
        rgbc.gridy = 0;
        rgbc.anchor = GridBagConstraints.CENTER;
        rgbc.insets = new Insets(25, 0, 0, 120);

        rightWrapper.add(contenidoDerecho, rgbc);

        contenido.add(sidebarWrapper, BorderLayout.WEST);
        contenido.add(rightWrapper, BorderLayout.CENTER);

        panel.add(contenido, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearSidebar(String activo) {
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 38, 0);

        JButton home = crearBotonMenu("HOME", new Color(0xC893C9), activo.equals("HOME"));
        JButton friends = crearBotonMenu("FRIENDS HUB", new Color(0xC893C9), activo.equals("FRIENDS HUB"));
        JButton stats = crearBotonMenu("MY STATS", new Color(0xC893C9), activo.equals("MY STATS"));
        JButton profile = crearBotonMenu("MY PROFILE", new Color(0xC893C9), activo.equals("MY PROFILE"));
        JButton news = crearBotonMenu("WHAT'S NEW", new Color(0xC893C9), activo.equals("WHAT'S NEW"));
        JButton settings = crearBotonMenu("SETTINGS", new Color(0xC893C9), activo.equals("SETTINGS"));
        JButton logout = crearBotonMenu("LOG OUT", new Color(0xE25B57), activo.equals("LOG OUT"));

        home.addActionListener(e -> cardLayout.show(cards, CARD_MENU_PRINCIPAL));

        friends.addActionListener(e -> {
            cards.add(friendsHubCard(), CARD_FRIENDS_HUB);
            cardLayout.show(cards, CARD_FRIENDS_HUB);
        });

        stats.addActionListener(e -> {
            cards.add(myStatsCard(), CARD_MY_STATS);
            cardLayout.show(cards, CARD_MY_STATS);
        });
        
        profile.addActionListener(e -> {
            cards.add(myProfileCard(), CARD_MY_PROFILE);
            cardLayout.show(cards, CARD_MY_PROFILE);
        });

        news.addActionListener(e -> {
            cards.add(whatsNewCard(), CARD_WHATS_NEW);
            cardLayout.show(cards, CARD_WHATS_NEW);
        });

        settings.addActionListener(e -> cardLayout.show(cards, CARD_SETTINGS));

        logout.addActionListener(e -> {
            menus.logout();
            limpiarCardsAutenticacion();
            cardLayout.show(cards, CARD_MENU_INICIO);
        });

        JButton[] botones = {home, friends, stats, profile, news, settings, logout};

        for (int i = 0; i < botones.length; i++) {
            gbc.gridy = i + 1;
            gbc.insets = new Insets(0, 0, 12, 0);
            sidebar.add(botones[i], gbc);
        }

        return sidebar;
    }

    private JButton crearBotonMenu(String texto, Color color, boolean activo) {
        JButton boton = crearBoton(texto, color);
        boton.setPreferredSize(new Dimension(180, 35));

        if (activo) {
            boton.setBorder(BorderFactory.createLineBorder(new Color(0xE25B57), 3));
            boton.setBorderPainted(true);
        }

        return boton;
    }

    private void cargarFuente() {
        try {
            File fontFile = new File("src/ashley/galatea/progra2/proyecto2/assets/fonts/BrokenConsoleBold.ttf");
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(arcadeFont);
        } catch (Exception e) {
            arcadeFont = new Font("Monospaced", Font.BOLD, 24);
        }
    }

    private class BackgroundPanel extends JPanel {

        private Image background;

        public BackgroundPanel(String path) {
            background = new ImageIcon(path).getImage();
            setBackground(new Color(0x1D3740));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int panelW = getWidth();
            int panelH = getHeight();

            int imgW = background.getWidth(this);
            int imgH = background.getHeight(this);

            double scale = Math.min((double) panelW / imgW, (double) panelH / imgH);

            int newW = (int)(imgW * scale);
            int newH = (int)(imgH * scale);

            int x = (panelW - newW) / 2;
            int y = (panelH - newH) / 2;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            g2.drawImage(background, x, y, newW, newH, this);
        }
    }

    private JButton crearBotonNivel(int numero) {
        Color color = Color.WHITE;

        if (menus.nivelDebeVerseCompletado(numero)) {
            color = new Color(0xC893C9);
        }

        JButton boton = new JButton(String.valueOf(numero));
        boton.setFont(arcadeFont.deriveFont(Font.PLAIN, 22f));
        boton.setForeground(Color.BLACK);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(75, 75));

        boton.addActionListener(e -> {
            String respuesta = menus.seleccionarNivel(numero);

            if (respuesta.equals("Nivel iniciado.")) {
                FlowFreeGUI juego = new FlowFreeGUI(menus, this, numero);
                juego.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, respuesta);
            }
        });

        return boton;
    }

    private JCheckBox crearCheckBox(String texto) {
        JCheckBox check = new JCheckBox(texto);
        check.setOpaque(false);
        check.setForeground(Color.WHITE);
        check.setFont(arcadeFont.deriveFont(Font.PLAIN, 13f));
        check.setFocusPainted(false);
        check.setCursor(new Cursor(Cursor.HAND_CURSOR));

        check.setAlignmentX(Component.LEFT_ALIGNMENT);
        check.setMaximumSize(new Dimension(280, 28));

        return check;
    }

    private JButton crearBotonBevel(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(arcadeFont.deriveFont(Font.PLAIN, 14f));
        boton.setBackground(new Color(0xD4D4D4));
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setPreferredSize(new Dimension(85, 35));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void cargarUsuariosFindFriends(JPanel listaPanel, ArrayList<JCheckBox> checks, String filtro) {
        listaPanel.removeAll();
        checks.clear();

        ArrayList<String> usuarios = menus.buscarUsuariosParaAgregar(filtro);

        for (int i = 0; i < usuarios.size(); i++) {
            JCheckBox check = crearCheckBox(usuarios.get(i).toUpperCase());
            check.setActionCommand(usuarios.get(i));
            checks.add(check);
            listaPanel.add(check);
        }
    }

    private void agregarFilaPerfil(JPanel panel, String key, String value, int fila) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 8, 0);

        JLabel label = new JLabel(
            "<html><span style='color:white;'>" + key + " </span>"
            + "<span style='color:#c893c9;'>" + value + "</span></html>"
        );

        label.setFont(arcadeFont.deriveFont(Font.PLAIN, 15f));

        gbc.gridx = 0;
        panel.add(label, gbc);
    }

    private JButton crearBotonAvatar(String avatar) {
        JButton boton = new JButton();

        ImageIcon icon = new ImageIcon(menus.obtenerRutaAvatar(avatar));
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        boton.setIcon(new ImageIcon(img));
        boton.setPreferredSize(new Dimension(50, 50));
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    private JButton crearBotonColorAvatar(Color color) {
        JButton boton = new JButton() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillOval(4, 4, getWidth() - 8, getHeight() - 8);
            }

            protected void paintBorder(Graphics g) {
                if (isSelected()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0xE25B57));
                    g2.setStroke(new BasicStroke(3));
                    g2.drawOval(3, 3, getWidth() - 7, getHeight() - 7);
                }
            }
        };

        boton.setBackground(color);
        boton.setPreferredSize(new Dimension(34, 34));
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    private class AvatarPreviewPanel extends JPanel {
        private String avatar;
        private String colorHex;

        public AvatarPreviewPanel(String avatar, String colorHex) {
            this.avatar = avatar;
            this.colorHex = colorHex;
            setOpaque(false);
            setPreferredSize(new Dimension(145, 145));
        }

        public void actualizar(String avatar, String colorHex) {
            this.avatar = avatar;
            this.colorHex = colorHex;
            revalidate();
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.decode(colorHex));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);

            ImageIcon icon = new ImageIcon(menus.obtenerRutaAvatar(avatar));
            Image img = icon.getImage().getScaledInstance(105, 105, Image.SCALE_SMOOTH);
            ImageIcon avatarIcon = new ImageIcon(img);

            int x = (getWidth() - avatarIcon.getIconWidth()) / 2;
            int y = (getHeight() - avatarIcon.getIconHeight()) / 2;

            avatarIcon.paintIcon(this, g2, x, y);

        }
    }

    private void continuarDespuesDeSeleccionIdioma() {
        if (loginPendienteDespuesIdioma) {
            String username = usernamePendienteIdioma;
            String password = passwordPendienteIdioma;

            loginPendienteDespuesIdioma = false;
            usernamePendienteIdioma = "";
            passwordPendienteIdioma = "";

            String respuesta = menus.login(username, password);

            if (respuesta.equals("Welcome")) {
                cards.add(menuPrincipalCard(), CARD_MENU_PRINCIPAL);
                cardLayout.show(cards, CARD_MENU_PRINCIPAL);
            } else {
                JOptionPane.showMessageDialog(null, respuesta);
                cardLayout.show(cards, CARD_LOGIN);
            }

        } else {
            cardLayout.show(cards, CARD_MENU_INICIO);
        }
    }

    private void limpiarCardsAutenticacion() {
        cards.removeAll();

        loginPendienteDespuesIdioma = false;
        usernamePendienteIdioma = "";
        passwordPendienteIdioma = "";

        cards.add(menuInicioCard(), CARD_MENU_INICIO);
        cards.add(logInCard(), CARD_LOGIN);
        cards.add(signInCard(), CARD_SIGNIN);

        cards.revalidate();
        cards.repaint();
    }

    private JScrollPane crearScrollLista(JPanel listaPanel, int ancho, int alto) {
        JScrollPane scroll = new JScrollPane(listaPanel);
        scroll.setPreferredSize(new Dimension(ancho, alto));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }

    public void mostrarPlayCardActualizado() {
        if (playCardActual != null) {
            cards.remove(playCardActual);
        }

        playCardActual = playCard();
        cards.add(playCardActual, CARD_INFO_NIVELES);

        cardLayout.show(cards, CARD_INFO_NIVELES);

        cards.revalidate();
        cards.repaint();
    }

    private void mostrarChallengeCardLimpio() {
        if (challengeCardActual != null) {
            cards.remove(challengeCardActual);
        }

        challengeCardActual = challengeCard();
        cards.add(challengeCardActual, CARD_CHALLENGE);

        cardLayout.show(cards, CARD_CHALLENGE);

        cards.revalidate();
        cards.repaint();
    }

    private JPanel crearCajaStat(String titulo, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(150, 55));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0xD4D4D4)));

        JLabel labelTitulo = crearTexto(titulo, Color.WHITE, 10f);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelValor = crearTexto(valor, new Color(0xFFEAFF), 12f);
        labelValor.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(labelTitulo, BorderLayout.NORTH);
        panel.add(labelValor, BorderLayout.CENTER);

        return panel;
    }

    private void cargarUsuariosCompareStats(JPanel listaPanel, ArrayList<JCheckBox> checks, String filtro) {
        listaPanel.removeAll();
        checks.clear();

        ButtonGroup grupo = new ButtonGroup();

        ArrayList<String> usuarios = menus.buscarUsuariosParaCompararStats(filtro);

        for (int i = 0; i < usuarios.size(); i++) {
            JCheckBox check = crearCheckBox(usuarios.get(i).toUpperCase());
            check.setActionCommand(usuarios.get(i));

            grupo.add(check);
            checks.add(check);
            listaPanel.add(check);
        }
    }

    private JButton crearBotonTextoTransparente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(arcadeFont.deriveFont(Font.PLAIN, 12f));
        boton.setForeground(Color.WHITE);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

}