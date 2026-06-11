/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author ashley
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Color;

public class Menus {


    private final String RUTA_USUARIOS = "data/usuarios/";
    private Usuario usuarioActual;
    private String idiomaSeleccionadoTemporal = null;

    public Menus() {
        File carpeta = new File(RUTA_USUARIOS);
        carpeta.mkdirs();
        usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    private Usuario cargarUsuario(String username) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(rutaArchivoUsuario(username))
            );

            Usuario usuario = (Usuario) in.readObject();
            in.close();

            if (!usuario.isEstadoCuentaInicializado()) {
                usuario.setCuentaActiva(true);
                usuario.setEstadoCuentaInicializado(true);
                guardarUsuario(usuario);
            }

            return usuario;

        } catch (Exception e) {
            return null;
        }
    }

    public Usuario buscarUsuario(String username) {
        return cargarUsuario(limpiarTexto(username));
    }

    private boolean existeUsuario(String username) {
        File archivo = new File(rutaArchivoUsuario(username));
        return archivo.exists();
    }

    private void guardarUsuario(Usuario usuario) {
        try {
            File carpetaUsuario = new File(RUTA_USUARIOS + usuario.getUsername());
            carpetaUsuario.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(rutaArchivoUsuario(usuario.getUsername()))
            );

            out.writeObject(usuario);
            out.close();

        } catch (Exception e) {
            System.out.println("Error guardando usuario: " + e.getMessage());
        }
    }

    private String rutaArchivoUsuario(String username) {
        return RUTA_USUARIOS + username + "/usuario.dat";
    }

    private String limpiarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.trim().toLowerCase();
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        File carpeta = new File(RUTA_USUARIOS);
        File[] carpetasUsuarios = carpeta.listFiles();

        if (carpetasUsuarios == null) {
            return usuarios;
        }

        for (int i = 0; i < carpetasUsuarios.length; i++) {
            if (carpetasUsuarios[i].isDirectory()) {
                Usuario u = cargarUsuario(carpetasUsuarios[i].getName());

                if (u != null) {
                    usuarios.add(u);
                }
            }
        }

        return usuarios;
    }

    private String generarHash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes("UTF-8"));

            String hash = "";

            for (int i = 0; i < bytes.length; i++) {
                hash += String.format("%02x", bytes[i]);
            }

            return hash;

        } catch (Exception e) {
            return texto;
        }
    }

    private String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "N/A";
        }

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fecha);
    }

    public String obtenerNombreCompletoPerfil() {
        if (usuarioActual == null) return "USER";
        return usuarioActual.getNombreCompleto().toUpperCase();
    }

    public String obtenerUsernamePerfil() {
        if (usuarioActual == null) return "USER";
        return usuarioActual.getUsername().toUpperCase();
    }

    public String obtenerStatusPerfil() {
        if (usuarioActual == null) return "N/A";
        return usuarioActual.isCuentaActiva() ? "ACTIVE" : "DISABLED";
    }

    public String obtenerFechaRegistroPerfil() {
        if (usuarioActual == null) return "N/A";
        return formatearFecha(usuarioActual.getFechaRegistro());
    }

    public String obtenerUltimoLoginPerfil() {
        if (usuarioActual == null) return "N/A";
        return formatearFecha(usuarioActual.getUltimaSesion());
    }

    public String obtenerNivelesCompletadosPerfil() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getNivelesCompletados());
    }

    public String obtenerRetosGanadosPerfil() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getRetosGanados());
    }

    public String obtenerScorePerfil() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getPuntuacionGeneral());
    }

    public String obtenerCantidadAmigosPerfil() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getAmigosRivales().size());
    }

    // =========================================================
    // USUARIO Y PASSWORD - ADMINISTRAR MI CUENTA
    // =========================================================

    public String crearUsuario(String username, String password, String nombreCompleto) {
        username = limpiarTexto(username);

        if (username.length() == 0 || password.length() == 0 || nombreCompleto.trim().length() == 0) {
            return "Debe llenar todos los campos.";
        }

        if (existeUsuario(username)) {
            return "Ese nombre de usuario ya existe.";
        }

        String validacion = validarPassword(password);

        if (!validacion.equals("OK")) {
            return validacion;
        }

        String hash = generarHash(password);
        Usuario nuevo = new Usuario(username, hash, nombreCompleto.trim());

        if (idiomaSeleccionadoTemporal != null) {
            nuevo.setIdioma(idiomaSeleccionadoTemporal);
        }

        guardarUsuario(nuevo);
        guardarActividad(nuevo.getUsername(), "account_activity.dat", "Account created and registered");

        return "Usuario creado correctamente.";
    }

    public String login(String username, String password) {
        username = limpiarTexto(username);

        if (!existeUsuario(username)) {
            return "El usuario no existe.";
        }

        Usuario usuario = cargarUsuario(username);

        if (usuario == null) {
            return "No se pudo cargar el usuario.";
        }

        String hashIngresado = generarHash(password);

        if (!usuario.getPasswordHash().equals(hashIngresado)) {
            return "Contraseña incorrecta.";
        }

        if (!usuario.isCuentaActiva()) {
            return "Account disabled. To proceed reactivate your account.";
        }

        usuario.iniciarSesion();

        usuarioActual = usuario;
        aplicarIdiomaSeleccionadoAlUsuarioActual();
        guardarUsuario(usuarioActual);
        registrarAccountActivity("User logged in");

        return "Welcome";
    }

    public String logout() {
        if (usuarioActual == null) {
            return "No hay una sesión activa.";
        }

        registrarAccountActivity("User logged out");

        usuarioActual.cerrarSesion();
        guardarUsuario(usuarioActual);
        usuarioActual = null;

        return "Sesión cerrada correctamente.";
    }

    public String cambiarPassword(String passwordActual, String passwordNueva) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        String hashActual = generarHash(passwordActual);

        if (!usuarioActual.getPasswordHash().equals(hashActual)) {
            return "La contraseña actual es incorrecta.";
        }

        String hashNueva = generarHash(passwordNueva);

        if (usuarioActual.getPasswordHash().equals(hashNueva)) {
            return "La nueva contraseña no puede ser igual a la contraseña actual.";
        }

        String validacion = validarPassword(passwordNueva);

        if (!validacion.equals("OK")) {
            return validacion;
        }

        usuarioActual.setPasswordHash(generarHash(passwordNueva));
        guardarUsuario(usuarioActual);

        registrarAccountActivity("User rotated password");

        return "Contraseña cambiada correctamente.";
    }

    public String validarPassword(String password) {
        if (!passwordTieneMinimoCaracteres(password)) {
            return "La contraseña debe tener al menos 8 caracteres.";
        }

        if (!passwordTieneMayuscula(password)) {
            return "La contraseña debe incluir al menos una letra mayúscula.";
        }

        if (!passwordTieneMinuscula(password)) {
            return "La contraseña debe incluir al menos una letra minúscula.";
        }

        if (!passwordTieneNumero(password)) {
            return "La contraseña debe incluir al menos un número.";
        }

        if (!passwordTieneEspecial(password)) {
            return "La contraseña debe incluir al menos un carácter especial.";
        }

        return "OK";
    }

    public boolean passwordTieneMinimoCaracteres(String password) {
        return password != null && password.length() >= 8;
    }

    public boolean passwordTieneMayuscula(String password) {
        if (password == null) return false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean passwordTieneMinuscula(String password) {
        if (password == null) return false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean passwordTieneNumero(String password) {
        if (password == null) return false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean passwordTieneEspecial(String password) {
        if (password == null) return false;

        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public String desactivarCuentaActual() {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        registrarAccountActivity("User disabled account");

        usuarioActual.setCuentaActiva(false);
        usuarioActual.cerrarSesion();
        guardarUsuario(usuarioActual);

        usuarioActual = null;

        return "Account disabled successfully.";
    }

    public String reactivarCuenta(String username, String password) {
        username = limpiarTexto(username);

        if (!existeUsuario(username)) {
            return "El usuario no existe.";
        }

        Usuario usuario = cargarUsuario(username);

        if (usuario == null) {
            return "No se pudo cargar el usuario.";
        }

        String hashIngresado = generarHash(password);

        if (!usuario.getPasswordHash().equals(hashIngresado)) {
            return "Contraseña incorrecta.";
        }

        usuario.setCuentaActiva(true);
        usuario.iniciarSesion();

        usuarioActual = usuario;
        guardarUsuario(usuarioActual);
        registrarAccountActivity("User restored account");

        return "Account restored successfully.";
    }

    public String eliminarCuentaActual() {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        String usernameEliminado = usuarioActual.getUsername();

        limpiarUsuarioEliminadoDeOtrosUsuarios(usernameEliminado);

        File carpetaUsuario = new File(RUTA_USUARIOS + usernameEliminado);

        usuarioActual = null;

        boolean eliminado = eliminarCarpetaRecursiva(carpetaUsuario);

        if (eliminado) {
            return "Account deleted successfully.";
        }

        return "No se pudo eliminar la cuenta completamente.";
    }

    private void limpiarUsuarioEliminadoDeOtrosUsuarios(String usernameEliminado) {
        ArrayList<Usuario> usuarios = obtenerUsuarios();

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);

            if (!usuario.getUsername().equals(usernameEliminado)) {
                usuario.eliminarAmigoRival(usernameEliminado);
                guardarUsuario(usuario);
            }
        }
    }

    private boolean eliminarCarpetaRecursiva(File archivo) {
        if (archivo == null || !archivo.exists()) {
            return true;
        }

        if (archivo.isDirectory()) {
            File[] archivos = archivo.listFiles();

            if (archivos != null) {
                for (int i = 0; i < archivos.length; i++) {
                    if (!eliminarCarpetaRecursiva(archivos[i])) {
                        return false;
                    }
                }
            }
        }

        return archivo.delete();
    }

    // =========================================================
    // AMIGOS - RIVALES
    // =========================================================


    public String agregarAmigoRival(String usernameRival) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        usernameRival = limpiarTexto(usernameRival);

        if (!existeUsuario(usernameRival)) {
            return "El usuario rival no existe.";
        }

        if (usuarioActual.getUsername().equals(usernameRival)) {
            return "No puede agregarse a usted mismo.";
        }

        usuarioActual.agregarAmigoRival(usernameRival);
        guardarUsuario(usuarioActual);

        return "Usuario agregado como amigo/rival.";
    }

    public String agregarAmigoBidireccional(String usernameAmigo) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        usernameAmigo = limpiarTexto(usernameAmigo);

        if (!existeUsuario(usernameAmigo)) {
            return "El usuario no existe.";
        }

        if (usuarioActual.getUsername().equals(usernameAmigo)) {
            return "No puede agregarse a usted mismo.";
        }

        usuarioActual.agregarAmigoRival(usernameAmigo);
        guardarUsuario(usuarioActual);

        Usuario amigo = cargarUsuario(usernameAmigo);

        if (amigo != null) {
            amigo.agregarAmigoRival(usuarioActual.getUsername());
            guardarUsuario(amigo);
        }

        registrarAccountActivity("User added " + usernameAmigo + " as friend");
        guardarActividad(usernameAmigo, "account_activity.dat",
                "User added by " + usuarioActual.getUsername() + " as friend");

        return "Friend added correctly.";
    }

    public ArrayList<String> obtenerAmigosActuales() {
        if (usuarioActual == null) {
            return new ArrayList<String>();
        }

        return usuarioActual.getAmigosRivales();
    }

    public String eliminarAmigos(ArrayList<String> amigos) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        if (amigos == null || amigos.size() == 0) {
            return "Debe seleccionar al menos un amigo.";
        }

        for (int i = 0; i < amigos.size(); i++) {
            String amigo = limpiarTexto(amigos.get(i));

            usuarioActual.eliminarAmigoRival(amigo);

            Usuario usuarioAmigo = cargarUsuario(amigo);

            if (usuarioAmigo != null) {
                usuarioAmigo.eliminarAmigoRival(usuarioActual.getUsername());
                guardarUsuario(usuarioAmigo);
            }

            registrarAccountActivity("User removed " + amigo + " from friend list");
            guardarActividad(amigo, "account_activity.dat",
                    "User removed by " + usuarioActual.getUsername() + " from friend list");
        }

        guardarUsuario(usuarioActual);

        return "Friend(s) removed correctly.";
    }


    public ArrayList<String> buscarUsuariosParaAgregar(String filtro) {
        ArrayList<String> resultado = new ArrayList<String>();

        if (usuarioActual == null) {
            return resultado;
        }

        filtro = limpiarTexto(filtro);

        ArrayList<Usuario> usuarios = obtenerUsuarios();
        ArrayList<String> amigos = usuarioActual.getAmigosRivales();
        String actual = usuarioActual.getUsername();

        for (int i = 0; i < usuarios.size(); i++) {
            String username = usuarios.get(i).getUsername();

            if (username.equals(actual)) {
                continue;
            }

            if (amigos.contains(username)) {
                continue;
            }

            if (filtro.length() == 1 && !username.startsWith(filtro)) {
                continue;
            }

            if (filtro.length() > 1 && !username.contains(filtro)) {
                continue;
            }

            resultado.add(username);
        }

        return resultado;
    }

    // =========================================================
    // ACCOUNT Y GAME ACTIVITY
    // =========================================================

    public ArrayList<Actividad> obtenerAccountActivity() {
        if (usuarioActual == null) {
            return new ArrayList<Actividad>();
        }

        return cargarActividades(usuarioActual.getUsername(), "account_activity.dat");
    }

    public ArrayList<Actividad> obtenerGameActivity() {
        if (usuarioActual == null) {
            return new ArrayList<Actividad>();
        }

        return cargarActividades(usuarioActual.getUsername(), "game_activity.dat");
    }

    public void registrarAccountActivity(String log) {
        if (usuarioActual != null) {
            guardarActividad(usuarioActual.getUsername(), "account_activity.dat", log);
        }
    }

    public void registrarGameActivity(String log) {
        if (usuarioActual != null) {
            guardarActividad(usuarioActual.getUsername(), "game_activity.dat", log);
        }
    }

    private void guardarActividad(String username, String archivo, String log) {
        ArrayList<Actividad> actividades = cargarActividades(username, archivo);

        actividades.add(new Actividad(log));

        try {
            File carpetaUsuario = new File(RUTA_USUARIOS + username);
            carpetaUsuario.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(RUTA_USUARIOS + username + "/" + archivo)
            );

            out.writeObject(actividades);
            out.close();

        } catch (Exception e) {
            System.out.println("Error guardando actividad: " + e.getMessage());
        }
    }

    private ArrayList<Actividad> cargarActividades(String username, String archivo) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(RUTA_USUARIOS + username + "/" + archivo)
            );

            ArrayList<Actividad> actividades = (ArrayList<Actividad>) in.readObject();
            in.close();

            return actividades;

        } catch (Exception e) {
            return new ArrayList<Actividad>();
        }
    }

    // =========================================================
    // CHALLANGE (COMPETENCIAS ENTRE USUARIOS)
    // =========================================================

    public ArrayList<String> obtenerOponentesDisponibles() {
        ArrayList<String> oponentes = new ArrayList<String>();

        if (usuarioActual == null) {
            return oponentes;
        }

        ArrayList<String> amigos = usuarioActual.getAmigosRivales();

        for (int i = 0; i < amigos.size(); i++) {
            String usernameAmigo = limpiarTexto(amigos.get(i));

            if (existeUsuario(usernameAmigo)) {
                oponentes.add(usernameAmigo);
            }
        }

        return oponentes;
    }

    public String iniciarChallenge(String usernameRival, String dificultad) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        usernameRival = limpiarTexto(usernameRival);

        if (!existeUsuario(usernameRival)) {
            return "El usuario rival no existe.";
        }

        if (usuarioActual.getUsername().equals(usernameRival)) {
            return "No puede retarse a usted mismo.";
        }

        int nivel = obtenerNivelAleatorioPorDificultad(dificultad);

        String logActual = "User started challenge against " + usernameRival
                + ", difficulty " + dificultad + ", level " + nivel;

        String logRival = "User was challenged by " + usuarioActual.getUsername()
                + ", difficulty " + dificultad + ", level " + nivel;

        usuarioActual.agregarReto("Challenge started against " + usernameRival
                + " | Difficulty: " + dificultad + " | Level: " + nivel);
        guardarUsuario(usuarioActual);

        Usuario rival = cargarUsuario(usernameRival);

        if (rival != null) {
            rival.agregarReto("User was challenged by " + usuarioActual.getUsername()
                    + " | Difficulty: " + dificultad + " | Level: " + nivel);
            guardarUsuario(rival);

            guardarActividad(usernameRival, "game_activity.dat", logRival);
        }

        registrarGameActivity(logActual);

        return "Challenge started.";
    }

    private int obtenerNivelAleatorioPorDificultad(String dificultad) {
        int opcion = (int)(Math.random() * 2);

        if (dificultad.equals("NEON CIRCUIT")) {
            return opcion == 0 ? 1 : 2;
        }

        if (dificultad.equals("POWER GRID")) {
            return opcion == 0 ? 3 : 4;
        }

        if (dificultad.equals("VOLTAGE RUN")) {
            return opcion == 0 ? 5 : 6;
        }

        if (dificultad.equals("ELECTRIC DRIFT")) {
            return opcion == 0 ? 7 : 8;
        }

        if (dificultad.equals("OVERLOAD")) {
            return opcion == 0 ? 9 : 10;
        }

        return 1;
    }

    // =========================================================
    // PUZZLES - NIVELES
    // =========================================================

    public boolean nivelDebeVerseCompletado(int nivel) {
        return nivelCompletado(nivel);
    }

    public boolean puedeJugarNivel(int nivel) {
        if (usuarioActual == null) {
            return false;
        }

        return nivel <= usuarioActual.getNivelDesbloqueado();
    }

    public String seleccionarNivel(int nivel) {
        if (!puedeJugarNivel(nivel)) {
            return "Nivel bloqueado.";
        }

        return iniciarPuzzle(nivel);
    }

    public ArrayList<Niveles> obtenerPuzzlesUsuario() {
        if (usuarioActual == null) {
            return new ArrayList<Niveles>();
        }

        ArrayList<Niveles> puzzles = cargarPuzzles(usuarioActual.getUsername());

        if (puzzles.size() == 0) {
            puzzles = crearPuzzlesIniciales();
            guardarPuzzles(usuarioActual.getUsername(), puzzles);
        }

        return puzzles;
    }

    public boolean nivelCompletado(int nivel) {
        ArrayList<Niveles> puzzles = obtenerPuzzlesUsuario();

        for (int i = 0; i < puzzles.size(); i++) {
            if (puzzles.get(i).getNivel() == nivel) {
                return puzzles.get(i).isCompletado();
            }
        }

        return false;
    }

    public String completarPuzzle(int nivel, int puntaje, long tiempoMinutos) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        ArrayList<Niveles> puzzles = obtenerPuzzlesUsuario();

        for (int i = 0; i < puzzles.size(); i++) {
            if (puzzles.get(i).getNivel() == nivel) {
                puzzles.get(i).completarNivel(puntaje, tiempoMinutos);
                guardarPuzzles(usuarioActual.getUsername(), puzzles);

                registrarGameActivity("User successfully completed level " + nivel);
                registrarGameActivity("User won " + puntaje + " points completing level " + nivel);

                return "Nivel completado correctamente.";
            }
        }

        return "Nivel no encontrado.";
    }

    private ArrayList<Niveles> crearPuzzlesIniciales() {
        ArrayList<Niveles> puzzles = new ArrayList<Niveles>();

        puzzles.add(new Niveles(1, "Neon Circuit"));
        puzzles.add(new Niveles(2, "Neon Circuit"));
        puzzles.add(new Niveles(3, "Power Grid"));
        puzzles.add(new Niveles(4, "Power Grid"));
        puzzles.add(new Niveles(5, "Voltage Run"));
        puzzles.add(new Niveles(6, "Voltage Run"));
        puzzles.add(new Niveles(7, "Electric Drift"));
        puzzles.add(new Niveles(8, "Electric Drift"));
        puzzles.add(new Niveles(9, "Overload"));
        puzzles.add(new Niveles(10, "Overload"));

        return puzzles;
    }

    private void guardarPuzzles(String username, ArrayList<Niveles> puzzles) {
        try {
            File carpetaUsuario = new File(RUTA_USUARIOS + username);
            carpetaUsuario.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(RUTA_USUARIOS + username + "/puzzles.dat")
            );

            out.writeObject(puzzles);
            out.close();

        } catch (Exception e) {
            System.out.println("Error guardando puzzles: " + e.getMessage());
        }
    }

    private ArrayList<Niveles> cargarPuzzles(String username) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(RUTA_USUARIOS + username + "/puzzles.dat")
            );

            ArrayList<Niveles> puzzles = (ArrayList<Niveles>) in.readObject();
            in.close();

            return puzzles;

        } catch (Exception e) {
            return new ArrayList<Niveles>();
        }
    }

    public String iniciarPuzzle(int nivel) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        if (!puedeJugarNivel(nivel)) {
            return "Nivel bloqueado.";
        }

        ArrayList<Niveles> puzzles = obtenerPuzzlesUsuario();

        for (int i = 0; i < puzzles.size(); i++) {
            if (puzzles.get(i).getNivel() == nivel) {
                registrarGameActivity("User started level " + nivel + " - " + puzzles.get(i).getDificultad());
                return "Nivel iniciado.";
            }
        }

        return "Nivel no encontrado.";
    }

    public String registrarResultadoPartida(boolean gano, int nivel, int puntos, long tiempoJugado, String detalle) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        usuarioActual.registrarPartida(gano, nivel, puntos, tiempoJugado, detalle);
        guardarUsuario(usuarioActual);

        return "Estadísticas actualizadas correctamente.";
    }

    // =========================================================
    // AVATARES
    // =========================================================

    public String obtenerAvatarPathPerfil() {
        if (usuarioActual == null || usuarioActual.getAvatar() == null || usuarioActual.getAvatar().equals("default")) {
            return "src/ashley/galatea/progra2/proyecto2/assets/avatar_1.png";
        }

        return "src/ashley/galatea/progra2/proyecto2/assets/" + usuarioActual.getAvatar();
    }

    public Color obtenerColorAvatarPerfil() {
        if (usuarioActual == null || usuarioActual.getAvatarColorHex() == null) {
            return Color.decode("#a2b794");
        }

        return Color.decode(usuarioActual.getAvatarColorHex());
    }

    public String cambiarAvatar(String avatar, String colorHex) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        usuarioActual.setAvatar(avatar);
        usuarioActual.setAvatarColorHex(colorHex);
        guardarUsuario(usuarioActual);

        registrarAccountActivity("User changed avatar");

        return "Avatar cambiado correctamente.";
    }

    public ArrayList<String> obtenerAvataresDisponibles() {
        ArrayList<String> avatares = new ArrayList<String>();

        for (int i = 1; i <= 21; i++) {
            avatares.add("avatar_" + i + ".png");
        }

        return avatares;
    }

    public ArrayList<String> obtenerColoresAvatarDisponibles() {
        ArrayList<String> colores = new ArrayList<String>();

        colores.add("#a2b794");
        colores.add("#c893c9");
        colores.add("#d99b18");
        colores.add("#b4b4b4");
        colores.add("#205c97");

        return colores;
    }

    public String obtenerAvatarActual() {
        if (usuarioActual == null || usuarioActual.getAvatar() == null) {
            return "avatar_1.png";
        }

        return usuarioActual.getAvatar();
    }

    public String obtenerColorAvatarActual() {
        if (usuarioActual == null || usuarioActual.getAvatarColorHex() == null) {
            return "#a2b794";
        }

        return usuarioActual.getAvatarColorHex();
    }

    public String obtenerRutaAvatar(String avatar) {
        return "src/ashley/galatea/progra2/proyecto2/assets/" + avatar;
    }

    public String guardarAvatarPerfil(String avatar, String colorHex) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        if (!obtenerAvataresDisponibles().contains(avatar)) {
            return "Avatar inválido.";
        }

        if (!obtenerColoresAvatarDisponibles().contains(colorHex)) {
            return "Color inválido.";
        }

        boolean cambioAvatar = !usuarioActual.getAvatar().equals(avatar);
        boolean cambioColor = !usuarioActual.getAvatarColorHex().equals(colorHex);

        if (!cambioAvatar && !cambioColor) {
            return "No avatar changes detected.";
        }

        usuarioActual.setAvatar(avatar);
        usuarioActual.setAvatarColorHex(colorHex);
        guardarUsuario(usuarioActual);

        registrarAccountActivity("User changed avatar");

        return "Avatar saved successfully.";
    }

    // =========================================================
    // CONFIG PERSISTENTE DEL UI DEL USER
    // =========================================================

    public String actualizarPerfil(String avatar, int volumen, String idioma, String controles) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        if (volumen < 0 || volumen > 100) {
            return "El volumen debe estar entre 0 y 100.";
        }

        usuarioActual.actualizarPerfil(avatar, volumen, idioma, controles);
        guardarUsuario(usuarioActual);

        return "Perfil actualizado correctamente.";
    }

    public String actualizarConfigAudio(int volumenSFX, int volumenMusica, boolean sfxActivo, boolean musicaActiva, double posicionMusicaSegundos) {
        if (usuarioActual == null) {
            return "Debe iniciar sesión.";
        }

        usuarioActual.actualizarConfigAudio(
                volumenSFX,
                volumenMusica,
                sfxActivo,
                musicaActiva,
                posicionMusicaSegundos
        );

        guardarUsuario(usuarioActual);

        return "Configuración de audio actualizada.";
    }

    public void seleccionarIdiomaTemporal(String idioma) {
        if (idioma == null) {
            idiomaSeleccionadoTemporal = null;
            return;
        }

        if (idioma.equalsIgnoreCase("English")) {
            idiomaSeleccionadoTemporal = "English";
        } else if (idioma.equalsIgnoreCase("Spanish")) {
            idiomaSeleccionadoTemporal = "Spanish";
        }
    }

    public void omitirSeleccionIdiomaTemporal() {
        idiomaSeleccionadoTemporal = null;
    }

    public boolean idiomaTemporalSeleccionado() {
        return idiomaSeleccionadoTemporal != null;
    }

    private void aplicarIdiomaSeleccionadoAlUsuarioActual() {
        if (usuarioActual != null && idiomaSeleccionadoTemporal != null) {
            usuarioActual.setIdioma(idiomaSeleccionadoTemporal);
            guardarUsuario(usuarioActual);
        }
    }

    public String getIdiomaSeleccionadoTemporal() {
        return idiomaSeleccionadoTemporal;
    }

    // =========================================================
    // PUNTUACION - PUNTAJE NIVELES Y CHALLENGES
    // =========================================================

    public int calcularPuntajeNivel(int nivel) {
        if (nivel >= 1 && nivel <= 2) {
            return 50;
        }

        if (nivel >= 3 && nivel <= 4) {
            return 100;
        }

        if (nivel >= 5 && nivel <= 6) {
            return 150;
        }

        if (nivel >= 7 && nivel <= 8) {
            return 200;
        }

        if (nivel >= 9 && nivel <= 10) {
            return 250;
        }

        return 0;
    }

    public int obtenerNumeroDificultadChallenge(String dificultad) {
        if (dificultad == null) {
            return 1;
        }

        dificultad = dificultad.toUpperCase();

        if (dificultad.equals("NEON CIRCUIT")) {
            return 1;
        }

        if (dificultad.equals("POWER GRID")) {
            return 2;
        }

        if (dificultad.equals("VOLTAGE RUN")) {
            return 3;
        }

        if (dificultad.equals("ELECTRIC DRIFT")) {
            return 4;
        }

        if (dificultad.equals("OVERLOAD")) {
            return 5;
        }

        return 1;
    }

    public int calcularPuntajeChallenge(String dificultad, boolean ganador) {
        int puntos = obtenerNumeroDificultadChallenge(dificultad) * 50;

        if (ganador) {
            puntos += 50;
        }

        return puntos;
    }

}