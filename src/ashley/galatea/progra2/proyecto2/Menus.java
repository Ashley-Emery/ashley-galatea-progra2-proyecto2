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

    private final String RUTA_CHALLENGES = "data/challenges/";
    private final String RUTA_FRIEND_REQUESTS = "data/friend_requests/";

    public Menus() {
        File carpeta = new File(RUTA_USUARIOS);
        carpeta.mkdirs();
        new File(RUTA_CHALLENGES).mkdirs();
        usuarioActual = null;
        new File(RUTA_FRIEND_REQUESTS).mkdirs();
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    private String idiomaMensajes() {
        if (usuarioActual != null) {
            return usuarioActual.getIdioma();
        }

        if (idiomaSeleccionadoTemporal != null) {
            return idiomaSeleccionadoTemporal;
        }

        return "English";
    }

    private String msg(String key) {
        return Idioma.get(key, idiomaMensajes());
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
        return usuarioActual.isCuentaActiva() ? msg("ACTIVE") : msg("DISABLED");
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
        return String.valueOf(obtenerAmigosActuales().size());
    }

    public String obtenerDificultadPreferidaPerfil() {
        if (usuarioActual == null) return "NEON CIRCUIT";
        return usuarioActual.getDificultadPreferida();
    }

    private boolean usuarioDisponible(String username) {
        Usuario usuario = cargarUsuario(limpiarTexto(username));

        return usuario != null && usuario.isCuentaActiva();
    }

    private ArrayList<Usuario> obtenerUsuariosActivos() {
        ArrayList<Usuario> activos = new ArrayList<Usuario>();
        ArrayList<Usuario> usuarios = obtenerUsuarios();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).isCuentaActiva()) {
                activos.add(usuarios.get(i));
            }
        }

        return activos;
    }

    // =========================================================
    // USUARIO Y PASSWORD - ADMINISTRAR MI CUENTA
    // =========================================================

    public String crearUsuario(String username, String password, String nombreCompleto) {
        username = limpiarTexto(username);

        if (username.length() == 0 || password.length() == 0 || nombreCompleto.trim().length() == 0) {
            return msg("ERROR_FILL_FIELDS");
        }

        if (existeUsuario(username)) {
            return msg("ERROR_USER_EXISTS");
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

        return msg("USER_CREATED");
    }

    public String login(String username, String password) {
        username = limpiarTexto(username);

        if (!existeUsuario(username)) {
            return msg("ERROR_USER_DOES_NOT_EXIST");
        }

        Usuario usuario = cargarUsuario(username);

        if (usuario == null) {
            return msg("ERROR_COULD_NOT_LOAD_USER");
        }

        String hashIngresado = generarHash(password);

        if (!usuario.getPasswordHash().equals(hashIngresado)) {
            return msg("ERROR_WRONG_PASSWORD");
        }

        if (!usuario.isCuentaActiva()) {
            return msg("ACCOUNT_DISABLED_TO_REACTIVATE");
        }

        usuario.iniciarSesion();

        usuarioActual = usuario;
        aplicarIdiomaSeleccionadoAlUsuarioActual();
        guardarUsuario(usuarioActual);
        registrarAccountActivity("User logged in");

        return msg("WELCOME");
    }

    public String logout() {
        if (usuarioActual == null) {
            return msg("ERROR_NO_ACTIVE_SESSION");
        }

        registrarAccountActivity("User logged out");

        usuarioActual.cerrarSesion();
        guardarUsuario(usuarioActual);
        usuarioActual = null;

        return msg("LOGOUT_SUCCESS");
    }

    public String cambiarPassword(String passwordActual, String passwordNueva) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        String hashActual = generarHash(passwordActual);

        if (!usuarioActual.getPasswordHash().equals(hashActual)) {
            return msg("ERROR_CURRENT_PASSWORD_WRONG");
        }

        String hashNueva = generarHash(passwordNueva);

        if (usuarioActual.getPasswordHash().equals(hashNueva)) {
            return msg("ERROR_SAME_PASSWORD");
        }

        String validacion = validarPassword(passwordNueva);

        if (!validacion.equals("OK")) {
            return validacion;
        }

        usuarioActual.setPasswordHash(generarHash(passwordNueva));
        guardarUsuario(usuarioActual);

        registrarAccountActivity("User rotated password");

        return msg("PASSWORD_CHANGED");
    }

    public String validarPassword(String password) {
        if (!passwordTieneMinimoCaracteres(password)) {
            return msg("ERROR_PASSWORD_MIN");
        }

        if (!passwordTieneMayuscula(password)) {
            return msg("ERROR_PASSWORD_UPPER");
        }

        if (!passwordTieneMinuscula(password)) {
            return msg("ERROR_PASSWORD_LOWER");
        }

        if (!passwordTieneNumero(password)) {
            return msg("ERROR_PASSWORD_NUMBER");
        }

        if (!passwordTieneEspecial(password)) {
            return msg("ERROR_PASSWORD_SPECIAL");
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
            return msg("ERROR_LOGIN_REQUIRED");
        }

        String username = usuarioActual.getUsername();

        registrarAccountActivity("User disabled account");

        resolverChallengesPorCuentaInactiva(username);

        usuarioActual.setCuentaActiva(false);
        usuarioActual.cerrarSesion();
        guardarUsuario(usuarioActual);

        usuarioActual = null;

        return msg("ACCOUNT_DISABLED_SUCCESS");
    }

    public String reactivarCuenta(String username, String password) {
        username = limpiarTexto(username);

        if (!existeUsuario(username)) {
            return msg("ERROR_USER_DOES_NOT_EXIST");
        }

        Usuario usuario = cargarUsuario(username);

        if (usuario == null) {
            return msg("ERROR_COULD_NOT_LOAD_USER");
        }

        String hashIngresado = generarHash(password);

        if (!usuario.getPasswordHash().equals(hashIngresado)) {
            return msg("ERROR_WRONG_PASSWORD");
        }

        usuario.setCuentaActiva(true);
        usuario.iniciarSesion();

        usuarioActual = usuario;
        guardarUsuario(usuarioActual);

        restaurarAmistadesAlReactivar(username);

        registrarAccountActivity("User restored account");

        return msg("ACCOUNT_RESTORED_SUCCESS");
    }

    private void restaurarAmistadesAlReactivar(String usernameReactivado) {
        Usuario reactivado = cargarUsuario(usernameReactivado);

        if (reactivado == null) {
            return;
        }

        ArrayList<String> amigos = reactivado.getAmigosRivales();

        for (int i = 0; i < amigos.size(); i++) {
            String usernameAmigo = limpiarTexto(amigos.get(i));
            Usuario amigo = cargarUsuario(usernameAmigo);

            if (amigo != null && amigo.isCuentaActiva()) {
                amigo.agregarAmigoRival(usernameReactivado);
                guardarUsuario(amigo);
            }
        }
    }

    public String eliminarCuentaActual() {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        String usernameEliminado = usuarioActual.getUsername();

        resolverChallengesPorCuentaInactiva(usernameEliminado);
        limpiarUsuarioEliminadoDeOtrosUsuarios(usernameEliminado);

        File carpetaUsuario = new File(RUTA_USUARIOS + usernameEliminado);

        usuarioActual = null;

        boolean eliminado = eliminarCarpetaRecursiva(carpetaUsuario);

        if (eliminado) {
            return msg("ACCOUNT_DELETED_SUCCESS");
        }

        return msg("ERROR_ACCOUNT_DELETE_FAILED");
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
            return msg("ERROR_LOGIN_REQUIRED");
        }

        usernameRival = limpiarTexto(usernameRival);

        if (!existeUsuario(usernameRival)) {
            return msg("ERROR_RIVAL_NOT_FOUND");
        }

        if (usuarioActual.getUsername().equals(usernameRival)) {
            return msg("ERROR_CANNOT_ADD_SELF");
        }

        usuarioActual.agregarAmigoRival(usernameRival);
        guardarUsuario(usuarioActual);

        return msg("FRIEND_ADDED_RIVAL");
    }

    public String agregarAmigoBidireccional(String usernameAmigo) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        usernameAmigo = limpiarTexto(usernameAmigo);

        if (!existeUsuario(usernameAmigo)) {
            return msg("ERROR_USER_DOES_NOT_EXIST");
        }

        if (usuarioActual.getUsername().equals(usernameAmigo)) {
            return msg("ERROR_CANNOT_ADD_SELF");
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

        return msg("FRIEND_ADDED");
    }

    public ArrayList<String> obtenerAmigosActuales() {
        ArrayList<String> amigosActivos = new ArrayList<String>();

        if (usuarioActual == null) {
            return amigosActivos;
        }

        ArrayList<String> amigos = usuarioActual.getAmigosRivales();

        for (int i = 0; i < amigos.size(); i++) {
            String amigo = limpiarTexto(amigos.get(i));

            if (usuarioDisponible(amigo)) {
                amigosActivos.add(amigo);
            }
        }

        return amigosActivos;
    }

    public String eliminarAmigos(ArrayList<String> amigos) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        if (amigos == null || amigos.size() == 0) {
            return msg("SELECT_AT_LEAST_ONE_FRIEND");
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

        return msg("FRIENDS_REMOVED");
    }


    public ArrayList<String> buscarUsuariosParaAgregar(String filtro) {
        ArrayList<String> resultado = new ArrayList<String>();

        if (usuarioActual == null) {
            return resultado;
        }

        filtro = limpiarTexto(filtro);

        ArrayList<Usuario> usuarios = obtenerUsuariosActivos();
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

    private void guardarSolicitudAmistad(SolicitudAmistad solicitud) {
        try {
            File carpeta = new File(RUTA_FRIEND_REQUESTS);
            carpeta.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(RUTA_FRIEND_REQUESTS + solicitud.getId() + ".dat")
            );

            out.writeObject(solicitud);
            out.close();

        } catch (Exception e) {
            System.out.println("Error guardando solicitud de amistad: " + e.getMessage());
        }
    }

    private SolicitudAmistad cargarSolicitudAmistad(String id) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(RUTA_FRIEND_REQUESTS + id + ".dat")
            );

            SolicitudAmistad solicitud = (SolicitudAmistad) in.readObject();
            in.close();

            return solicitud;

        } catch (Exception e) {
            return null;
        }
    }

    public String enviarSolicitudAmistad(String usernameReceptor) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        usernameReceptor = limpiarTexto(usernameReceptor);

        if (!usuarioDisponible(usernameReceptor)) {
            return msg("ERROR_USER_NOT_AVAILABLE");
        }

        if (usuarioActual.getUsername().equals(usernameReceptor)) {
            return msg("ERROR_CANNOT_REQUEST_SELF");
        }

        if (usuarioActual.getAmigosRivales().contains(usernameReceptor)) {
            return msg("ERROR_ALREADY_FRIEND");
        }

        if (existeSolicitudPendiente(usuarioActual.getUsername(), usernameReceptor)) {
            return msg("FRIEND_REQUEST_ALREADY_SENT");
        }

        SolicitudAmistad solicitud = new SolicitudAmistad(
                usuarioActual.getUsername(),
                usernameReceptor
        );

        guardarSolicitudAmistad(solicitud);

        registrarAccountActivity("User sent friend request to " + usernameReceptor);

        guardarActividad(
                usernameReceptor,
                "account_activity.dat",
                "Friend request received from " + usuarioActual.getUsername()
        );

        return msg("FRIEND_REQUEST_SENT");
    }

    private boolean existeSolicitudPendiente(String solicitante, String receptor) {
        File carpeta = new File(RUTA_FRIEND_REQUESTS);
        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            return false;
        }

        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].isFile() && archivos[i].getName().endsWith(".dat")) {
                String id = archivos[i].getName().replace(".dat", "");
                SolicitudAmistad solicitud = cargarSolicitudAmistad(id);

                if (solicitud != null
                        && !solicitud.isFinalizada()
                        && solicitud.getSolicitante().equals(solicitante)
                        && solicitud.getReceptor().equals(receptor)) {
                    return true;
                }
            }
        }

        return false;
    }

    public ArrayList<SolicitudAmistad> obtenerSolicitudesAmistadPendientes() {
        ArrayList<SolicitudAmistad> pendientes = new ArrayList<SolicitudAmistad>();

        if (usuarioActual == null) {
            return pendientes;
        }

        File carpeta = new File(RUTA_FRIEND_REQUESTS);
        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            return pendientes;
        }

        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].isFile() && archivos[i].getName().endsWith(".dat")) {
                String id = archivos[i].getName().replace(".dat", "");
                SolicitudAmistad solicitud = cargarSolicitudAmistad(id);

                if (solicitud != null
                        && !solicitud.isFinalizada()
                        && solicitud.getReceptor().equals(usuarioActual.getUsername())
                        && usuarioDisponible(solicitud.getSolicitante())) {
                    pendientes.add(solicitud);
                }
            }
        }

        return pendientes;
    }

    public String aceptarSolicitudAmistad(String solicitudId) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        SolicitudAmistad solicitud = cargarSolicitudAmistad(solicitudId);

        if (solicitud == null) {
            return msg("ERROR_COULD_NOT_LOAD_REQUEST");
        }

        if (!solicitud.getReceptor().equals(usuarioActual.getUsername())) {
            return msg("ERROR_REQUEST_NOT_CURRENT_USER");
        }

        Usuario solicitante = cargarUsuario(solicitud.getSolicitante());

        if (solicitante == null || !solicitante.isCuentaActiva()) {
            solicitud.declinar();
            guardarSolicitudAmistad(solicitud);
            return msg("ERROR_USER_NO_LONGER_AVAILABLE");
        }

        usuarioActual.agregarAmigoRival(solicitud.getSolicitante());
        solicitante.agregarAmigoRival(usuarioActual.getUsername());

        guardarUsuario(usuarioActual);
        guardarUsuario(solicitante);

        solicitud.aceptar();
        guardarSolicitudAmistad(solicitud);

        registrarAccountActivity("User accepted friend request from " + solicitud.getSolicitante());

        guardarActividad(
                solicitud.getSolicitante(),
                "account_activity.dat",
                usuarioActual.getUsername() + " accepted your friend request"
        );

        return msg("FRIEND_REQUEST_ACCEPTED");
    }

    public String declinarSolicitudAmistad(String solicitudId) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        SolicitudAmistad solicitud = cargarSolicitudAmistad(solicitudId);

        if (solicitud == null) {
            return msg("ERROR_COULD_NOT_LOAD_REQUEST");
        }

        if (!solicitud.getReceptor().equals(usuarioActual.getUsername())) {
            return msg("ERROR_REQUEST_NOT_CURRENT_USER");
        }

        solicitud.declinar();
        guardarSolicitudAmistad(solicitud);

        registrarAccountActivity("User declined friend request from " + solicitud.getSolicitante());

        guardarActividad(
                solicitud.getSolicitante(),
                "account_activity.dat",
                usuarioActual.getUsername() + " declined your friend request"
        );

        return msg("FRIEND_REQUEST_DECLINED");
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

            if (usuarioDisponible(usernameAmigo)) {
                oponentes.add(usernameAmigo);
            }
        }

        return oponentes;
    }

    public ChallengePartida iniciarChallengePartida(String usernameRival, String dificultad) {
        if (usuarioActual == null) {
            return null;
        }

        usernameRival = limpiarTexto(usernameRival);

        if (!usuarioDisponible(usernameRival)) {
            return null;
        }

        if (usuarioActual.getUsername().equals(usernameRival)) {
            return null;
        }

        int nivel = obtenerNivelAleatorioPorDificultad(dificultad);

        ChallengePartida challenge = new ChallengePartida(
                usuarioActual.getUsername(),
                usernameRival,
                dificultad,
                nivel
        );

        guardarChallenge(challenge);

        String logActual = "User started challenge against " + usernameRival
                + ", difficulty " + dificultad + ", level " + nivel;

        String logRival = "User was challenged by " + usuarioActual.getUsername()
                + ", difficulty " + dificultad + ", level " + nivel;

        usuarioActual.agregarReto("Challenge ID: " + challenge.getId()
                + " | Against: " + usernameRival
                + " | Difficulty: " + dificultad
                + " | Level: " + nivel);
        guardarUsuario(usuarioActual);

        Usuario rival = cargarUsuario(usernameRival);

        if (rival != null) {
            rival.agregarReto("Challenge ID: " + challenge.getId()
                    + " | Challenged by: " + usuarioActual.getUsername()
                    + " | Difficulty: " + dificultad
                    + " | Level: " + nivel);
            guardarUsuario(rival);

            guardarActividad(usernameRival, "game_activity.dat", logRival);
        }

        registrarGameActivity(logActual);

        return challenge;
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

    public String registrarResultadoChallenge(String challengeId, int tiempoSegundos) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        ChallengePartida challenge = cargarChallenge(challengeId);

        if (challenge == null) {
            return msg("ERROR_COULD_NOT_LOAD_CHALLENGE");
        }

        int scoreBase = calcularPuntajeChallenge(challenge.getDificultad(), false);

        boolean registrado = challenge.registrarTiempo(
                usuarioActual.getUsername(),
                tiempoSegundos,
                scoreBase
        );

        if (!registrado) {
            return msg("RESULT_ALREADY_REGISTERED");
        }

        usuarioActual.sumarPuntuacion(scoreBase);
        guardarUsuario(usuarioActual);

        registrarGameActivity(
                "User completed challenge " + challenge.getId()
                + " in " + tiempoSegundos + " seconds and won " + scoreBase + " points"
        );

        if (challenge.ambosCompletaron()) {
            challenge.calcularGanador();

            Usuario jugador1 = cargarUsuario(challenge.getJugador1());
            Usuario jugador2 = cargarUsuario(challenge.getJugador2());

            guardarActividad(challenge.getJugador1(), "game_activity.dat",
                    "Challenge " + challenge.getId() + " completed. Winner: " + challenge.getGanador());

            guardarActividad(challenge.getJugador2(), "game_activity.dat",
                    "Challenge " + challenge.getId() + " completed. Winner: " + challenge.getGanador());
        }

        guardarChallenge(challenge);

        return "OK";
    }

    public ChallengePartida obtenerChallenge(String challengeId) {
        return cargarChallenge(challengeId);
    }

    private void resolverChallengesPorCuentaInactiva(String usernameInactivo) {
        File carpeta = new File(RUTA_CHALLENGES);
        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            return;
        }

        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].isFile() && archivos[i].getName().endsWith(".dat")) {
                String id = archivos[i].getName().replace(".dat", "");
                ChallengePartida challenge = cargarChallenge(id);

                if (challenge == null || challenge.isFinalizado()) {
                    continue;
                }

                boolean participa =
                        challenge.getJugador1().equals(usernameInactivo)
                        || challenge.getJugador2().equals(usernameInactivo);

                if (!participa) {
                    continue;
                }

                challenge.finalizarPorCuentaInactiva(usernameInactivo);
                guardarChallenge(challenge);

                String ganador = challenge.getGanador();
                Usuario usuarioGanador = cargarUsuario(ganador);

                if (usuarioGanador != null && usuarioGanador.isCuentaActiva()) {
                    guardarUsuario(usuarioGanador);

                    guardarActividad(
                            ganador,
                            "game_activity.dat",
                            "Challenge " + challenge.getId()
                            + " was won because " + usernameInactivo
                            + " disabled or deleted the account. User won 50 bonus points."
                    );
                }
            }
        }
    }

    public ArrayList<ChallengePartida> obtenerRewardsChallengePendientes() {
        ArrayList<ChallengePartida> pendientes = new ArrayList<ChallengePartida>();

        if (usuarioActual == null) {
            return pendientes;
        }

        File carpeta = new File(RUTA_CHALLENGES);
        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            return pendientes;
        }

        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].isFile() && archivos[i].getName().endsWith(".dat")) {
                String id = archivos[i].getName().replace(".dat", "");
                ChallengePartida challenge = cargarChallenge(id);

                if (challenge != null
                        && challenge.isFinalizado()
                        && challenge.getGanador().equals(usuarioActual.getUsername())
                        && !challenge.rewardClaimedPor(usuarioActual.getUsername())) {
                    pendientes.add(challenge);
                }
            }
        }

        return pendientes;
    }

    public String claimChallengeReward(String challengeId) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        ChallengePartida challenge = cargarChallenge(challengeId);

        if (challenge == null) {
            return msg("ERROR_COULD_NOT_LOAD_CHALLENGE");
        }

        if (!challenge.isFinalizado()) {
            return msg("ERROR_CHALLENGE_NOT_FINISHED");
        }

        if (!challenge.getGanador().equals(usuarioActual.getUsername())) {
            return msg("ERROR_REWARD_NOT_USER");
        }

        if (challenge.rewardClaimedPor(usuarioActual.getUsername())) {
            return msg("ERROR_REWARD_ALREADY_CLAIMED");
        }

        usuarioActual.sumarPuntuacion(50);
        usuarioActual.sumarRetoGanado();

        challenge.marcarRewardClaimed(usuarioActual.getUsername());

        guardarUsuario(usuarioActual);
        guardarChallenge(challenge);

        registrarGameActivity(
                "User claimed 50 bonus points for winning challenge "
                + challenge.getId()
        );

        return msg("REWARD_CLAIMED");
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
            return msg("LEVEL_LOCKED");
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
            return msg("ERROR_LOGIN_REQUIRED");
        }

        ArrayList<Niveles> puzzles = obtenerPuzzlesUsuario();

        for (int i = 0; i < puzzles.size(); i++) {
            if (puzzles.get(i).getNivel() == nivel) {
                puzzles.get(i).completarNivel(puntaje, tiempoMinutos);
                guardarPuzzles(usuarioActual.getUsername(), puzzles);

                registrarGameActivity("User successfully completed level " + nivel);
                registrarGameActivity("User won " + puntaje + " points completing level " + nivel);

                return msg("LEVEL_PROGRESS_SAVED");
            }
        }

        return msg("LEVEL_NOT_FOUND");
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
            return msg("ERROR_LOGIN_REQUIRED");
        }

        if (!puedeJugarNivel(nivel)) {
            return msg("LEVEL_LOCKED");
        }

        ArrayList<Niveles> puzzles = obtenerPuzzlesUsuario();

        for (int i = 0; i < puzzles.size(); i++) {
            if (puzzles.get(i).getNivel() == nivel) {
                registrarGameActivity("User started level " + nivel + " - " + puzzles.get(i).getDificultad());
                return msg("LEVEL_STARTED");
            }
        }

        return msg("LEVEL_NOT_FOUND");
    }

    public String registrarResultadoPartida(boolean gano, int nivel, int puntos, long tiempoJugado, String detalle) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        usuarioActual.registrarPartida(gano, nivel, puntos, tiempoJugado, detalle);
        guardarUsuario(usuarioActual);

        return msg("STATS_UPDATED");
    }

    public String guardarProgresoNivel(int nivel, int puntaje, long tiempoMinutos, int tiempoSegundos) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        boolean primeraVez = !nivelCompletado(nivel);
        int puntosAplicados = primeraVez ? puntaje : 0;

        if (primeraVez) {
            completarPuzzle(nivel, puntaje, tiempoMinutos);

            usuarioActual.registrarPartida(
                    true,
                    nivel,
                    puntosAplicados,
                    tiempoSegundos,
                    "Level completed in " + tiempoSegundos + " seconds"
            );

            guardarUsuario(usuarioActual);

            return msg("LEVEL_PROGRESS_SAVED");
        }

        registrarGameActivity("User replayed and completed level " + nivel);

        usuarioActual.registrarPartida(
                true,
                nivel,
                0,
                tiempoSegundos,
                "Level replayed in " + tiempoSegundos + " seconds"
        );

        guardarUsuario(usuarioActual);

        return msg("LEVEL_REPLAY_NO_POINTS");
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
            return msg("ERROR_LOGIN_REQUIRED");
        }

        usuarioActual.setAvatar(avatar);
        usuarioActual.setAvatarColorHex(colorHex);
        guardarUsuario(usuarioActual);

        registrarAccountActivity("User changed avatar");

        return msg("AVATAR_CHANGED");
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
            return msg("ERROR_LOGIN_REQUIRED");
        }

        if (!obtenerAvataresDisponibles().contains(avatar)) {
            return msg("ERROR_INVALID_AVATAR");
        }

        if (!obtenerColoresAvatarDisponibles().contains(colorHex)) {
            return msg("ERROR_INVALID_COLOR");
        }

        boolean cambioAvatar = !usuarioActual.getAvatar().equals(avatar);
        boolean cambioColor = !usuarioActual.getAvatarColorHex().equals(colorHex);

        if (!cambioAvatar && !cambioColor) {
            return msg("NO_AVATAR_CHANGES");
        }

        usuarioActual.setAvatar(avatar);
        usuarioActual.setAvatarColorHex(colorHex);
        guardarUsuario(usuarioActual);

        registrarAccountActivity("User changed avatar");

        return msg("AVATAR_SAVED");
    }

    // =========================================================
    // CONFIG PERSISTENTE DEL UI DEL USER
    // =========================================================

    public String actualizarPerfil(String avatar, int volumen, String idioma, String controles) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        if (volumen < 0 || volumen > 100) {
            return msg("ERROR_VOLUME_RANGE");
        }

        usuarioActual.actualizarPerfil(avatar, volumen, idioma, controles);
        guardarUsuario(usuarioActual);

        return msg("PROFILE_UPDATED");
    }

    public String actualizarConfigAudio(int volumenSFX, int volumenMusica, boolean sfxActivo, boolean musicaActiva, double posicionMusicaSegundos) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        usuarioActual.actualizarConfigAudio(
                volumenSFX,
                volumenMusica,
                sfxActivo,
                musicaActiva,
                posicionMusicaSegundos
        );

        guardarUsuario(usuarioActual);

        return msg("AUDIO_CONFIG_UPDATED");
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

            idiomaSeleccionadoTemporal = null;
        }
    }

    public String getIdiomaSeleccionadoTemporal() {
        return idiomaSeleccionadoTemporal;
    }

    public String obtenerIdiomaSettings() {
        if (usuarioActual == null) return "English";
        return usuarioActual.getIdioma();
    }

    public int obtenerVolumenMusicaSettings() {
        if (usuarioActual == null) return 60;
        return usuarioActual.getVolumenMusica();
    }

    public int obtenerVolumenSFXSettings() {
        if (usuarioActual == null) return 80;
        return usuarioActual.getVolumenSFX();
    }

    public boolean musicaActivaSettings() {
        if (usuarioActual == null) return true;
        return usuarioActual.isMusicaActiva();
    }

    public boolean sfxActivoSettings() {
        if (usuarioActual == null) return true;
        return usuarioActual.isSfxActivo();
    }

    public String obtenerDificultadPreferidaSettings() {
        if (usuarioActual == null) return "NEON CIRCUIT";
        return usuarioActual.getDificultadPreferida();
    }

    public String guardarSettingsUsuario(
            String idioma,
            boolean musicaActiva,
            boolean sfxActivo,
            int volumenMusica,
            int volumenSFX,
            String dificultadPreferida
    ) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        usuarioActual.setIdioma(idioma);
        usuarioActual.actualizarConfigAudio(
                volumenSFX,
                volumenMusica,
                sfxActivo,
                musicaActiva,
                usuarioActual.getPosicionMusicaSegundos()
        );
        usuarioActual.setDificultadPreferida(dificultadPreferida);

        guardarUsuario(usuarioActual);

        registrarAccountActivity("User updated settings");

        return msg("SETTINGS_SAVED");
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

    private void guardarChallenge(ChallengePartida challenge) {
        try {
            File carpeta = new File(RUTA_CHALLENGES);
            carpeta.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(RUTA_CHALLENGES + challenge.getId() + ".dat")
            );

            out.writeObject(challenge);
            out.close();

        } catch (Exception e) {
            System.out.println("Error guardando challenge: " + e.getMessage());
        }
    }

    private ChallengePartida cargarChallenge(String id) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(RUTA_CHALLENGES + id + ".dat")
            );

            ChallengePartida challenge = (ChallengePartida) in.readObject();
            in.close();

            return challenge;

        } catch (Exception e) {
            return null;
        }
    }

    // =========================================================
    // STATS - RANKING DE PUNTAJES
    // =========================================================

    public String obtenerGamesPlayedStats() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getPartidasJugadas());
    }

    public String obtenerLevelsCompletedStats() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getNivelesCompletados());
    }

    public String obtenerAvgTimePerLevelStats() {
        if (usuarioActual == null) return "00:00";

        long segundos = usuarioActual.getTiempoPromedioPorNivel();
        long minutos = segundos / 60;
        long resto = segundos % 60;

        return String.format("%02d:%02d", minutos, resto);
    }

    public String obtenerChallengesWonStats() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getRetosGanados());
    }

    public String obtenerScoreStats() {
        if (usuarioActual == null) return "0";
        return String.valueOf(usuarioActual.getPuntuacionGeneral());
    }

    public String obtenerRankingStats() {
        if (usuarioActual == null) return "#00";

        ArrayList<Usuario> usuarios = obtenerUsuariosActivos();

        Collections.sort(usuarios, new Comparator<Usuario>() {
            public int compare(Usuario a, Usuario b) {
                return b.getPuntuacionGeneral() - a.getPuntuacionGeneral();
            }
        });

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuarioActual.getUsername())) {
                return String.format("#%02d", i + 1);
            }
        }

        return "#00";
    }

    public ArrayList<String> buscarUsuariosParaCompararStats(String filtro) {
        ArrayList<String> resultado = new ArrayList<String>();

        if (usuarioActual == null) {
            return resultado;
        }

        filtro = limpiarTexto(filtro);

        ArrayList<Usuario> usuarios = obtenerUsuariosActivos();
        String actual = usuarioActual.getUsername();

        for (int i = 0; i < usuarios.size(); i++) {
            String username = usuarios.get(i).getUsername();

            if (username.equals(actual)) {
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

    public ArrayList<String[]> obtenerGeneralRanking() {
        ArrayList<String[]> ranking = new ArrayList<String[]>();
        ArrayList<Usuario> usuarios = obtenerUsuariosActivos();

        Collections.sort(usuarios, new Comparator<Usuario>() {
            public int compare(Usuario a, Usuario b) {
                return b.getPuntuacionGeneral() - a.getPuntuacionGeneral();
            }
        });

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);

            String[] fila = {
                String.valueOf(i + 1),
                u.getUsername().toUpperCase(),
                String.valueOf(u.getPuntuacionGeneral()),
                String.valueOf(u.getNivelesCompletados()),
                formatearSegundos(u.getTiempoPromedioPorNivel()),
                formatearSegundos(u.getTiempoTotalJugado())
            };

            ranking.add(fila);
        }

        return ranking;
    }

    private String formatearSegundos(long segundos) {
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long resto = segundos % 60;

        return String.format("%02d:%02d:%02d", horas, minutos, resto);
    }

    public ArrayList<String[]> obtenerFriendsRanking() {
        ArrayList<String[]> ranking = new ArrayList<String[]>();

        if (usuarioActual == null) {
            return ranking;
        }

        ArrayList<String> amigos = usuarioActual.getAmigosRivales();
        ArrayList<Usuario> usuariosRanking = new ArrayList<Usuario>();

        for (int i = 0; i < amigos.size(); i++) {
            Usuario amigo = cargarUsuario(amigos.get(i));

            if (amigo != null && amigo.isCuentaActiva()) {
                usuariosRanking.add(amigo);
            }
        }

        Collections.sort(usuariosRanking, new Comparator<Usuario>() {
            public int compare(Usuario a, Usuario b) {
                return b.getPuntuacionGeneral() - a.getPuntuacionGeneral();
            }
        });

        for (int i = 0; i < usuariosRanking.size(); i++) {
            Usuario u = usuariosRanking.get(i);

            String[] fila = {
                String.valueOf(i + 1),
                u.getUsername().toUpperCase(),
                String.valueOf(u.getPuntuacionGeneral()),
                String.valueOf(u.getNivelesCompletados()),
                formatearSegundos(u.getTiempoPromedioPorNivel()),
                formatearSegundos(u.getTiempoTotalJugado())
            };

            ranking.add(fila);
        }

        return ranking;
    }

    public ArrayList<String[]> obtenerCompareStats(String usernameComparar) {
        ArrayList<String[]> datos = new ArrayList<String[]>();

        if (usuarioActual == null) {
            return datos;
        }

        Usuario comparado = cargarUsuario(limpiarTexto(usernameComparar));

        if (comparado == null || !comparado.isCuentaActiva()) {
            return datos;
        }

        datos.add(crearFilaRanking(usuarioActual, 1));
        datos.add(crearFilaRanking(comparado, 2));

        return datos;
    }

    private String[] crearFilaRanking(Usuario u, int posicion) {
        String[] fila = {
            String.valueOf(posicion),
            u.getUsername().toUpperCase(),
            String.valueOf(u.getPuntuacionGeneral()),
            String.valueOf(u.getNivelesCompletados()),
            formatearSegundos(u.getTiempoPromedioPorNivel()),
            formatearSegundos(u.getTiempoTotalJugado())
        };

        return fila;
    }

    public ArrayList<ChallengePartida> obtenerChallengesPendientes() {
        ArrayList<ChallengePartida> pendientes = new ArrayList<ChallengePartida>();

        if (usuarioActual == null) {
            return pendientes;
        }

        File carpeta = new File(RUTA_CHALLENGES);
        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            return pendientes;
        }

        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].isFile() && archivos[i].getName().endsWith(".dat")) {
                String id = archivos[i].getName().replace(".dat", "");
                ChallengePartida challenge = cargarChallenge(id);

                if (challenge != null
                        && challenge.getJugador2().equals(usuarioActual.getUsername())
                        && !challenge.isFinalizado()
                        && challenge.getTiempoJugador2() == -1) {
                    pendientes.add(challenge);
                }
            }
        }

        return pendientes;
    }

    public String declinarChallenge(String challengeId) {
        if (usuarioActual == null) {
            return msg("ERROR_LOGIN_REQUIRED");
        }

        ChallengePartida challenge = cargarChallenge(challengeId);

        if (challenge == null) {
            return msg("ERROR_COULD_NOT_LOAD_CHALLENGE");
        }

        if (!challenge.getJugador2().equals(usuarioActual.getUsername())) {
            return msg("ERROR_CHALLENGE_NOT_CURRENT_USER");
        }

        challenge.declinar(usuarioActual.getUsername());

        Usuario retador = cargarUsuario(challenge.getJugador1());

        if (retador != null) {
            guardarUsuario(retador);
        }

        guardarChallenge(challenge);

        guardarActividad(challenge.getJugador1(), "game_activity.dat",
                "Challenge " + challenge.getId() + " was declined. User won 50 bonus points.");

        registrarGameActivity(
                "User declined challenge " + challenge.getId()
                + " from " + challenge.getJugador1()
        );

        return msg("CHALLENGE_DECLINED");
    }

    public String obtenerTiempoChallengeAgo(ChallengePartida challenge) {
        if (challenge == null) {
            return "0 min ago";
        }

        return formatearTiempoAgo(challenge.getFechaCreacion());
    }

    public String obtenerTiempoSolicitudAgo(SolicitudAmistad solicitud) {
        if (solicitud == null) {
            return "0 min ago";
        }

        return formatearTiempoAgo(solicitud.getFechaCreacion());
    }

    private String formatearTiempoAgo(Date fecha) {
        if (fecha == null) {
            return "0 min ago";
        }

        long diferencia = new Date().getTime() - fecha.getTime();
        long minutos = diferencia / 60000;

        if (minutos < 1) {
            return "0 min ago";
        }

        if (minutos < 60) {
            return minutos + " min ago";
        }

        long horas = minutos / 60;

        if (horas < 24) {
            return horas + "h ago";
        }

        long dias = horas / 24;
        return dias + "d ago";
    }

    // =========================================================
    // NOTIFICACIONES
    // =========================================================

    public boolean hayNotificacionesPendientes() {
        return obtenerChallengesPendientes().size() > 0
                || obtenerSolicitudesAmistadPendientes().size() > 0
                || obtenerRewardsChallengePendientes().size() > 0;
    }

}