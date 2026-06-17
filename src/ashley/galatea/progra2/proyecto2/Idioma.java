/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.galatea.progra2.proyecto2;

/**
 *
 * @author USER
 */
import java.util.HashMap;

public class Idioma {

    private static HashMap<String, String> en = new HashMap<String, String>();
    private static HashMap<String, String> es = new HashMap<String, String>();

    static {
        cargarIngles();
        cargarEspanol();
    }

    public static String get(String key, Menus menus) {
        String idioma = "English";

        if (menus != null && menus.getUsuarioActual() != null) {
            idioma = menus.getUsuarioActual().getIdioma();
        }

        return get(key, idioma);
    }

    public static String get(String key, String idioma) {
        if (key == null) {
            return "";
        }

        if (esIdiomaEspanol(idioma) && es.containsKey(key)) {
            return es.get(key);
        }

        if (en.containsKey(key)) {
            return en.get(key);
        }

        return key;
    }

    private static boolean esIdiomaEspanol(String idioma) {
        if (idioma == null) {
            return false;
        }

        return idioma.equalsIgnoreCase("Spanish")
                || idioma.equalsIgnoreCase("Español")
                || idioma.equalsIgnoreCase("Espanol");
    }

    private static void cargarIngles() {
        en.put("LANGUAGE_PREFERENCE", "LANGUAGE PREFERENCE");
        en.put("ENGLISH", "ENGLISH");
        en.put("SPANISH", "SPANISH");
        en.put("SKIP", "SKIP");

        en.put("LOG_IN", "LOG IN");
        en.put("SIGN_IN", "SIGN IN");
        en.put("EXIT", "EXIT");
        en.put("BACK", "BACK");
        en.put("GO_BACK", "GO BACK");
        en.put("CANCEL", "CANCEL");
        en.put("SAVE", "SAVE");
        en.put("ACCEPT", "ACCEPT");
        en.put("DECLINE", "DECLINE");
        en.put("PLAY", "PLAY");
        en.put("CONTINUE", "CONTINUE");
        en.put("SEARCH", "SEARCH");

        en.put("USER", "USER");
        en.put("USERNAME", "USERNAME");
        en.put("NAME", "NAME");
        en.put("PASSWORD", "PASSWORD");
        en.put("CURRENT_PASSWORD", "CURRENT PASSWORD");
        en.put("NEW_PASSWORD", "NEW PASSWORD");
        en.put("WELCOME", "Welcome");
        en.put("NOT_REGISTERED_SIGN_UP", "NOT REGISTERED YET? SIGN UP>");

        en.put("HOME", "HOME");
        en.put("FRIENDS_HUB", "FRIENDS HUB");
        en.put("MY_STATS", "MY STATS");
        en.put("MY_PROFILE", "MY PROFILE");
        en.put("WHATS_NEW", "WHAT'S NEW");
        en.put("SETTINGS", "SETTINGS");
        en.put("LOG_OUT", "LOG OUT");
        en.put("MY_ACTIVITY", "MY ACTIVITY");

        en.put("FLOW_FREE", "FLOW FREE");
        en.put("WELCOME_BACK", "WELCOME BACK");

        en.put("PLAY_TITLE", "PLAY");
        en.put("CHALLENGE", "CHALLENGE");
        en.put("CHOOSE_OPPONENT", "CHOOSE AN OPPONENT");
        en.put("CHOOSE_DIFFICULTY", "CHOOSE DIFFICULTY");
        en.put("CHALLENGE_STARTED", "Challenge started.");
        en.put("CHALLENGE_ACCEPTED", "Challenge accepted.");
        en.put("COULD_NOT_START_CHALLENGE", "Could not start challenge.");
        en.put("SELECT_OPPONENT_DIFFICULTY", "You must select an opponent and a difficulty.");
        en.put("CHALLENGE_STARTED_TITLE", "CHALLENGE STARTED");
        en.put("CHALLENGE_STARTED_TEXT", "The arcade won't repair itself.<br>Time to prove your skills.<br><br>Hope your rival knows how to handle loose wires.");
        en.put("CHALLENGE_RESULTS", "CHALLENGE RESULTS");
        en.put("TIME", "TIME");
        en.put("SCORE", "SCORE");
        en.put("WINNER", "WINNER");
        en.put("CLAIM_REWARD", "CLAIM REWARD");
        en.put("YOU_WON", "YOU WON!");
        en.put("YOU_WON_CHALLENGE_AGAINST", "YOU WON CHALLENGE AGAINST");
        en.put("REWARD_CLAIMED", "Reward claimed. You won 50 points.");

        en.put("NEW_CHALLENGE", "NEW CHALLENGE!");
        en.put("CHALLENGED_YOU", "CHALLENGED YOU!");
        en.put("LEVEL", "LEVEL");
        en.put("NEW_FRIEND_REQUEST", "NEW FRIEND REQUEST!");
        en.put("WANTS_TO_ADD_YOU", "WANTS TO ADD YOU AS A FRIEND!");
        en.put("NO_NEW_NOTIFICATIONS", "NO NEW NOTIFICATIONS");

        en.put("FRIENDS", "FRIENDS");
        en.put("FIND_FRIENDS", "FIND FRIEND(S)");
        en.put("REMOVE_FRIENDS", "REMOVE FRIEND(S)");
        en.put("ADD_FRIEND", "ADD FRIEND");
        en.put("FRIEND_REQUEST_SENT", "Friend request sent.");
        en.put("FRIEND_REQUESTS_SENT", "friend request(s) sent.");
        en.put("SELECT_AT_LEAST_ONE_USER", "You must select at least one user.");
        en.put("SELECT_AT_LEAST_ONE_FRIEND", "You must select at least one friend.");
        en.put("FRIENDS_REMOVED", "Friend(s) removed correctly.");
        en.put("FRIEND_ADDED", "Friend added correctly.");

        en.put("ACCOUNT_ACTIVITY", "ACCOUNT ACTIVITY");
        en.put("GAME_ACTIVITY", "GAME ACTIVITY");
        en.put("LOG", "LOG");
        en.put("DATE", "DATE");

        en.put("DISABLE_ACCOUNT", "DISABLE ACCOUNT");
        en.put("DELETE_ACCOUNT", "DELETE ACCOUNT");
        en.put("ROTATE_PASSWORD", "ROTATE PASSWORD");
        en.put("CHANGE_AVATAR", "CHANGE AVATAR");
        en.put("MY_AVATAR", "MY AVATAR");

        en.put("DISABLE_ACCOUNT_TEXT", "BY DISABLING YOUR ACCOUNT, YOU WILL NOT<br>BE ABLE TO LOG IN OR ACCESS YOUR DATA<br>UNTIL YOU REACTIVATE IT AGAIN.<br><br><br>DO YOU WANT TO CONTINUE?");
        en.put("DELETE_ACCOUNT_TEXT", "THIS ACTION IS PERMANENT. YOUR ACCOUNT<br>AND ALL YOUR DATA WILL BE DELETED AND<br>CANNOT BE RECOVERED.<br><br>DELETING YOUR ACCOUNT WILL ERASE ALL<br>YOUR PROGRESS PERMANENTLY.<br><br>DO YOU WANT TO CONTINUE?");
        en.put("CONFIRM_ACTION", "I UNDERSTAND AND CONFIRM THIS ACTION");

        en.put("STATUS", "STATUS");
        en.put("REGISTERED_ON", "REGISTERED ON");
        en.put("LAST_LOGIN", "LAST LOGIN");
        en.put("DIFFICULTY_MODE", "DIFFICULTY MODE");
        en.put("LEVELS_COMPLETED", "LEVELS COMPLETED");
        en.put("CHALLENGES_WON", "CHALLENGES WON");
        en.put("ACTIVE", "ACTIVE");
        en.put("DISABLED", "DISABLED");

        en.put("GAMES_PLAYED", "GAMES PLAYED");
        en.put("AVG_TIME_PER_LEVEL", "AVG. TIME PER LEVEL");
        en.put("RANKING", "RANKING");
        en.put("PERFORMANCE_RANKINGS", "PERFORMANCE RANKINGS");
        en.put("GENERAL_RANKING", "GENERAL RANKING");
        en.put("FRIENDS_RANKING", "FRIENDS RANKING");
        en.put("COMPARE_WITH_PLAYER", "COMPARE WITH PLAYER");
        en.put("COMPARE_STATS", "COMPARE STATS");
        en.put("SELECT_PLAYER", "You must select a player.");

        en.put("PLAYER", "PLAYER");
        en.put("LEVELS", "LEVELS");
        en.put("AVG_TIME", "AVG TIME");
        en.put("HOURS_PLAYED", "HOURS PLAYED");

        en.put("LANGUAGE", "LANGUAGE");
        en.put("AUDIO", "AUDIO");
        en.put("MUSIC_VOLUME", "MUSIC VOLUME");
        en.put("SFX_VOLUME", "SFX VOLUME");
        en.put("DEFAULT_DIFFICULTY", "DEFAULT DIFFICULTY");
        en.put("ENABLE_MUSIC", "ENABLE MUSIC");
        en.put("ENABLE_SOUND_EFFECTS", "ENABLE SOUND EFFECTS");

        en.put("NEON_CIRCUIT", "NEON CIRCUIT");
        en.put("POWER_GRID", "POWER GRID");
        en.put("VOLTAGE_RUN", "VOLTAGE RUN");
        en.put("ELECTRIC_DRIFT", "ELECTRIC DRIFT");
        en.put("OVERLOAD", "OVERLOAD");

        en.put("PASSWORD_REQ_1", "CONTAIN AT LEAST 8 CHARACTERS");
        en.put("PASSWORD_REQ_2", "INCLUDE AT LEAST ONE UPPERCASE LETTER (A-Z)");
        en.put("PASSWORD_REQ_3", "INCLUDE AT LEAST ONE LOWERCASE LETTER (a-z)");
        en.put("PASSWORD_REQ_4", "CONTAIN AT LEAST ONE NUMBER (0-9)");
        en.put("PASSWORD_REQ_5", "INCLUDE AT LEAST ONE SPECIAL CHARACTER (!@#$%&)");

        en.put("ACCOUNT_DISABLED_REACTIVATE", "This account is disabled.\nDo you want to reactivate it?");
        en.put("REACTIVATE_ACCOUNT", "Reactivate Account");
        en.put("LEAVE_GAME", "Leave Game?");
        en.put("LEAVE_GAME_MESSAGE", "Do you want to abandon this game?\nYour progress for this unfinished level will not be saved.");
        en.put("ALL_LEVELS_COMPLETED", "All levels completed!");
        en.put("GAME_COMPLETED", "Game Completed");
        en.put("LEVEL_STATUS", "Level");
        en.put("TIME_STATUS", "Time");
        en.put("SECONDS_SHORT", "s");

        en.put("RESTART", "Restart");
        en.put("TOOLTIP_SFX_VOLUME", "SFX Volume");
        en.put("TOOLTIP_MUTE_SOUND", "Mute Sound");
        en.put("TOOLTIP_MUSIC_VOLUME", "Music Volume");
        en.put("TOOLTIP_HOME", "Home");
        en.put("TOOLTIP_RESTART_LEVEL", "Restart Level");
        en.put("TOOLTIP_UNDO_LAST_MOVE", "Undo Last Move");

        en.put("ERROR_FILL_FIELDS", "You must fill in all fields.");
        en.put("ERROR_USER_EXISTS", "That username already exists.");
        en.put("ERROR_USER_DOES_NOT_EXIST", "The user does not exist.");
        en.put("ERROR_COULD_NOT_LOAD_USER", "Could not load user.");
        en.put("ERROR_WRONG_PASSWORD", "Incorrect password.");
        en.put("ERROR_LOGIN_REQUIRED", "You must log in.");
        en.put("ERROR_PASSWORD_MIN", "Password must have at least 8 characters.");
        en.put("ERROR_PASSWORD_UPPER", "Password must include at least one uppercase letter.");
        en.put("ERROR_PASSWORD_LOWER", "Password must include at least one lowercase letter.");
        en.put("ERROR_PASSWORD_NUMBER", "Password must include at least one number.");
        en.put("ERROR_PASSWORD_SPECIAL", "Password must include at least one special character.");
        en.put("ERROR_SAME_PASSWORD", "The new password cannot be the same as the current password.");

        en.put("USER_CREATED", "User created successfully.");
        en.put("PASSWORD_CHANGED", "Password changed successfully.");
        en.put("ACCOUNT_DISABLED_SUCCESS", "Account disabled successfully.");
        en.put("ACCOUNT_RESTORED_SUCCESS", "Account restored successfully.");
        en.put("ACCOUNT_DELETED_SUCCESS", "Account deleted successfully.");
        en.put("SETTINGS_SAVED", "Settings saved successfully.");
        en.put("AVATAR_SAVED", "Avatar saved successfully.");
        en.put("NO_AVATAR_CHANGES", "No avatar changes detected.");
        en.put("LEVEL_COMPLETED", "Level Completed");
        en.put("COMPLETED", "Completed");
        en.put("SECONDS", "seconds");
        en.put("PETERS_ARCADE_TRUSTS_1", "Looks like Peter's arcade");
        en.put("PETERS_ARCADE_TRUSTS_2", "trusts");
        en.put("PETERS_ARCADE_TRUSTS_3", "more today..");
        en.put("ACCOUNT_DISABLED_TO_REACTIVATE", "Account disabled. To proceed reactivate your account.");
        en.put("ERROR_NO_ACTIVE_SESSION", "There is no active session.");
        en.put("LOGOUT_SUCCESS", "Session closed successfully.");
        en.put("ERROR_CURRENT_PASSWORD_WRONG", "The current password is incorrect.");
        en.put("ERROR_ACCOUNT_DELETE_FAILED", "The account could not be completely deleted.");
        en.put("ERROR_RIVAL_NOT_FOUND", "The rival user does not exist.");
        en.put("ERROR_CANNOT_ADD_SELF", "You cannot add yourself.");
        en.put("FRIEND_ADDED_RIVAL", "User added as friend/rival.");
        en.put("ERROR_USER_NOT_AVAILABLE", "The user is not available.");
        en.put("ERROR_CANNOT_REQUEST_SELF", "You cannot send a request to yourself.");
        en.put("ERROR_ALREADY_FRIEND", "This user is already your friend.");
        en.put("FRIEND_REQUEST_ALREADY_SENT", "Friend request already sent.");
        en.put("FRIEND_REQUEST_ACCEPTED", "Friend request accepted.");
        en.put("FRIEND_REQUEST_DECLINED", "Friend request declined.");
        en.put("ERROR_COULD_NOT_LOAD_REQUEST", "Could not load the request.");
        en.put("ERROR_REQUEST_NOT_CURRENT_USER", "This request does not belong to the current user.");
        en.put("ERROR_USER_NO_LONGER_AVAILABLE", "The user is no longer available.");
        en.put("ERROR_COULD_NOT_LOAD_CHALLENGE", "Could not load the challenge.");
        en.put("ERROR_CHALLENGE_NOT_CURRENT_USER", "This challenge does not belong to the current user.");
        en.put("CHALLENGE_DECLINED", "Challenge declined.");
        en.put("ERROR_CHALLENGE_NOT_FINISHED", "Challenge is not finished yet.");
        en.put("ERROR_REWARD_NOT_USER", "This reward does not belong to this user.");
        en.put("ERROR_REWARD_ALREADY_CLAIMED", "Reward already claimed.");
        en.put("LEVEL_LOCKED", "Level locked.");
        en.put("LEVEL_STARTED", "Level started.");
        en.put("LEVEL_NOT_FOUND", "Level not found.");
        en.put("LEVEL_PROGRESS_SAVED", "Level completed successfully.");
        en.put("LEVEL_REPLAY_NO_POINTS", "Level replayed successfully. No additional points were added.");
        en.put("STATS_UPDATED", "Stats updated successfully.");
        en.put("RESULT_ALREADY_REGISTERED", "This result was already registered.");
        en.put("AUDIO_CONFIG_UPDATED", "Audio configuration updated.");
        en.put("ERROR_VOLUME_RANGE", "Volume must be between 0 and 100.");
        en.put("ERROR_INVALID_AVATAR", "Invalid avatar.");
        en.put("ERROR_INVALID_COLOR", "Invalid color.");
        en.put("AVATAR_CHANGED", "Avatar changed successfully.");
        en.put("PROFILE_UPDATED", "Profile updated successfully.");
    }

    private static void cargarEspanol() {
        es.put("LANGUAGE_PREFERENCE", "PREFERENCIA DE IDIOMA");
        es.put("ENGLISH", "INGLÉS");
        es.put("SPANISH", "ESPAÑOL");
        es.put("SKIP", "OMITIR");

        es.put("LOG_IN", "INICIAR SESIÓN");
        es.put("SIGN_IN", "REGISTRARSE");
        es.put("EXIT", "SALIR");
        es.put("BACK", "VOLVER");
        es.put("GO_BACK", "REGRESAR");
        es.put("CANCEL", "CANCELAR");
        es.put("SAVE", "GUARDAR");
        es.put("ACCEPT", "ACEPTAR");
        es.put("DECLINE", "RECHAZAR");
        es.put("PLAY", "JUGAR");
        es.put("CONTINUE", "CONTINUAR");
        es.put("SEARCH", "BUSCAR");

        es.put("USER", "USUARIO");
        es.put("USERNAME", "USUARIO");
        es.put("NAME", "NOMBRE");
        es.put("PASSWORD", "CONTRASEÑA");
        es.put("CURRENT_PASSWORD", "CONTRASEÑA ACTUAL");
        es.put("NEW_PASSWORD", "NUEVA CONTRASEÑA");
        es.put("WELCOME", "Bienvenido");
        es.put("NOT_REGISTERED_SIGN_UP", "¿NO TIENES CUENTA? REGÍSTRATE>");

        es.put("HOME", "INICIO");
        es.put("FRIENDS_HUB", "AMIGOS");
        es.put("MY_STATS", "MIS ESTADÍSTICAS");
        es.put("MY_PROFILE", "MI PERFIL");
        es.put("WHATS_NEW", "NOVEDADES");
        es.put("SETTINGS", "AJUSTES");
        es.put("LOG_OUT", "CERRAR SESIÓN");
        es.put("MY_ACTIVITY", "MI ACTIVIDAD");

        es.put("FLOW_FREE", "FLOW FREE");
        es.put("WELCOME_BACK", "BIENVENIDO DE VUELTA");

        es.put("PLAY_TITLE", "JUGAR");
        es.put("CHALLENGE", "RETO");
        es.put("CHOOSE_OPPONENT", "ESCOGE UN OPONENTE");
        es.put("CHOOSE_DIFFICULTY", "ESCOGE DIFICULTAD");
        es.put("CHALLENGE_STARTED", "Reto iniciado.");
        es.put("CHALLENGE_ACCEPTED", "Reto aceptado.");
        es.put("COULD_NOT_START_CHALLENGE", "No se pudo iniciar el reto.");
        es.put("SELECT_OPPONENT_DIFFICULTY", "Debes seleccionar un oponente y una dificultad.");
        es.put("CHALLENGE_STARTED_TITLE", "RETO INICIADO");
        es.put("CHALLENGE_STARTED_TEXT", "El arcade no se reparará solo.<br>Es hora de demostrar tus habilidades.<br><br>Espero que tu rival sepa manejar cables sueltos.");
        es.put("CHALLENGE_RESULTS", "RESULTADOS DEL RETO");
        es.put("TIME", "TIEMPO");
        es.put("SCORE", "PUNTAJE");
        es.put("WINNER", "GANADOR");
        es.put("CLAIM_REWARD", "RECLAMAR PREMIO");
        es.put("YOU_WON", "¡GANASTE!");
        es.put("YOU_WON_CHALLENGE_AGAINST", "GANASTE EL RETO CONTRA");
        es.put("REWARD_CLAIMED", "Premio reclamado. Ganaste 50 puntos.");

        es.put("NEW_CHALLENGE", "¡NUEVO RETO!");
        es.put("CHALLENGED_YOU", "TE RETÓ!");
        es.put("LEVEL", "NIVEL");
        es.put("NEW_FRIEND_REQUEST", "¡NUEVA SOLICITUD DE AMISTAD!");
        es.put("WANTS_TO_ADD_YOU", "QUIERE AGREGARTE COMO AMIGO!");
        es.put("NO_NEW_NOTIFICATIONS", "NO HAY NOTIFICACIONES NUEVAS");

        es.put("FRIENDS", "AMIGOS");
        es.put("FIND_FRIENDS", "BUSCAR AMIGO(S)");
        es.put("REMOVE_FRIENDS", "ELIMINAR AMIGO(S)");
        es.put("ADD_FRIEND", "AGREGAR AMIGO");
        es.put("FRIEND_REQUEST_SENT", "Solicitud de amistad enviada.");
        es.put("FRIEND_REQUESTS_SENT", "solicitud(es) de amistad enviada(s).");
        es.put("SELECT_AT_LEAST_ONE_USER", "Debes seleccionar al menos un usuario.");
        es.put("SELECT_AT_LEAST_ONE_FRIEND", "Debes seleccionar al menos un amigo.");
        es.put("FRIENDS_REMOVED", "Amigo(s) eliminado(s) correctamente.");
        es.put("FRIEND_ADDED", "Amigo agregado correctamente.");

        es.put("ACCOUNT_ACTIVITY", "ACTIVIDAD DE CUENTA");
        es.put("GAME_ACTIVITY", "ACTIVIDAD DEL JUEGO");
        es.put("LOG", "REGISTRO");
        es.put("DATE", "FECHA");

        es.put("DISABLE_ACCOUNT", "DESACTIVAR CUENTA");
        es.put("DELETE_ACCOUNT", "ELIMINAR CUENTA");
        es.put("ROTATE_PASSWORD", "CAMBIAR CONTRASEÑA");
        es.put("CHANGE_AVATAR", "CAMBIAR AVATAR");
        es.put("MY_AVATAR", "MI AVATAR");

        es.put("DISABLE_ACCOUNT_TEXT", "AL DESACTIVAR TU CUENTA, NO PODRÁS<br>INICIAR SESIÓN NI ACCEDER A TUS DATOS<br>HASTA QUE LA REACTIVES NUEVAMENTE.<br><br><br>¿DESEAS CONTINUAR?");
        es.put("DELETE_ACCOUNT_TEXT", "ESTA ACCIÓN ES PERMANENTE. TU CUENTA<br>Y TODOS TUS DATOS SERÁN ELIMINADOS<br>Y NO PODRÁN RECUPERARSE.<br><br>ELIMINAR TU CUENTA BORRARÁ TODO<br>TU PROGRESO DE FORMA PERMANENTE.<br><br>¿DESEAS CONTINUAR?");
        es.put("CONFIRM_ACTION", "ENTIENDO Y CONFIRMO ESTA ACCIÓN");

        es.put("STATUS", "ESTADO");
        es.put("REGISTERED_ON", "REGISTRADO EL");
        es.put("LAST_LOGIN", "ÚLTIMO LOGIN");
        es.put("DIFFICULTY_MODE", "MODO DE DIFICULTAD");
        es.put("LEVELS_COMPLETED", "NIVELES COMPLETADOS");
        es.put("CHALLENGES_WON", "RETOS GANADOS");
        es.put("ACTIVE", "ACTIVA");
        es.put("DISABLED", "DESACTIVADA");

        es.put("GAMES_PLAYED", "PARTIDAS JUGADAS");
        es.put("AVG_TIME_PER_LEVEL", "TIEMPO PROM. POR NIVEL");
        es.put("RANKING", "RANKING");
        es.put("PERFORMANCE_RANKINGS", "RANKINGS DE RENDIMIENTO");
        es.put("GENERAL_RANKING", "RANKING GENERAL");
        es.put("FRIENDS_RANKING", "RANKING DE AMIGOS");
        es.put("COMPARE_WITH_PLAYER", "COMPARAR CON JUGADOR");
        es.put("COMPARE_STATS", "COMPARAR STATS");
        es.put("SELECT_PLAYER", "Debes seleccionar un jugador.");

        es.put("PLAYER", "JUGADOR");
        es.put("LEVELS", "NIVELES");
        es.put("AVG_TIME", "TIEMPO PROM.");
        es.put("HOURS_PLAYED", "HORAS JUGADAS");

        es.put("LANGUAGE", "IDIOMA");
        es.put("AUDIO", "AUDIO");
        es.put("MUSIC_VOLUME", "VOLUMEN DE MÚSICA");
        es.put("SFX_VOLUME", "VOLUMEN SFX");
        es.put("DEFAULT_DIFFICULTY", "DIFICULTAD POR DEFECTO");
        es.put("ENABLE_MUSIC", "ACTIVAR MÚSICA");
        es.put("ENABLE_SOUND_EFFECTS", "ACTIVAR EFECTOS DE SONIDO");

        es.put("NEON_CIRCUIT", "NEON CIRCUIT");
        es.put("POWER_GRID", "POWER GRID");
        es.put("VOLTAGE_RUN", "VOLTAGE RUN");
        es.put("ELECTRIC_DRIFT", "ELECTRIC DRIFT");
        es.put("OVERLOAD", "OVERLOAD");

        es.put("PASSWORD_REQ_1", "CONTENER AL MENOS 8 CARACTERES");
        es.put("PASSWORD_REQ_2", "INCLUIR AL MENOS UNA LETRA MAYÚSCULA (A-Z)");
        es.put("PASSWORD_REQ_3", "INCLUIR AL MENOS UNA LETRA MINÚSCULA (a-z)");
        es.put("PASSWORD_REQ_4", "CONTENER AL MENOS UN NÚMERO (0-9)");
        es.put("PASSWORD_REQ_5", "INCLUIR AL MENOS UN CARÁCTER ESPECIAL (!@#$%&)");

        es.put("ACCOUNT_DISABLED_REACTIVATE", "Esta cuenta está desactivada.\n¿Deseas reactivarla?");
        es.put("REACTIVATE_ACCOUNT", "Reactivar cuenta");
        es.put("LEAVE_GAME", "¿Salir del juego?");
        es.put("LEAVE_GAME_MESSAGE", "¿Deseas abandonar esta partida?\nTu progreso de este nivel incompleto no será guardado.");
        es.put("ALL_LEVELS_COMPLETED", "¡Todos los niveles completados!");
        es.put("GAME_COMPLETED", "Juego completado");
        es.put("LEVEL_STATUS", "Nivel");
        es.put("TIME_STATUS", "Tiempo");
        es.put("SECONDS_SHORT", "s");

        es.put("RESTART", "Reiniciar");
        es.put("TOOLTIP_SFX_VOLUME", "Volumen SFX");
        es.put("TOOLTIP_MUTE_SOUND", "Silenciar sonido");
        es.put("TOOLTIP_MUSIC_VOLUME", "Volumen de música");
        es.put("TOOLTIP_HOME", "Inicio");
        es.put("TOOLTIP_RESTART_LEVEL", "Reiniciar nivel");
        es.put("TOOLTIP_UNDO_LAST_MOVE", "Deshacer último movimiento");

        es.put("ERROR_FILL_FIELDS", "Debes llenar todos los campos.");
        es.put("ERROR_USER_EXISTS", "Ese nombre de usuario ya existe.");
        es.put("ERROR_USER_DOES_NOT_EXIST", "El usuario no existe.");
        es.put("ERROR_COULD_NOT_LOAD_USER", "No se pudo cargar el usuario.");
        es.put("ERROR_WRONG_PASSWORD", "Contraseña incorrecta.");
        es.put("ERROR_LOGIN_REQUIRED", "Debes iniciar sesión.");
        es.put("ERROR_PASSWORD_MIN", "La contraseña debe tener al menos 8 caracteres.");
        es.put("ERROR_PASSWORD_UPPER", "La contraseña debe incluir al menos una letra mayúscula.");
        es.put("ERROR_PASSWORD_LOWER", "La contraseña debe incluir al menos una letra minúscula.");
        es.put("ERROR_PASSWORD_NUMBER", "La contraseña debe incluir al menos un número.");
        es.put("ERROR_PASSWORD_SPECIAL", "La contraseña debe incluir al menos un carácter especial.");
        es.put("ERROR_SAME_PASSWORD", "La nueva contraseña no puede ser igual a la contraseña actual.");

        es.put("USER_CREATED", "Usuario creado correctamente.");
        es.put("PASSWORD_CHANGED", "Contraseña cambiada correctamente.");
        es.put("ACCOUNT_DISABLED_SUCCESS", "Cuenta desactivada correctamente.");
        es.put("ACCOUNT_RESTORED_SUCCESS", "Cuenta restaurada correctamente.");
        es.put("ACCOUNT_DELETED_SUCCESS", "Cuenta eliminada correctamente.");
        es.put("SETTINGS_SAVED", "Ajustes guardados correctamente.");
        es.put("AVATAR_SAVED", "Avatar guardado correctamente.");
        es.put("NO_AVATAR_CHANGES", "No se detectaron cambios en el avatar.");
        es.put("LEVEL_COMPLETED", "Nivel completado");
        es.put("COMPLETED", "Completado");
        es.put("SECONDS", "segundos");
        es.put("PETERS_ARCADE_TRUSTS_1", "Parece que el arcade de Peter");
        es.put("PETERS_ARCADE_TRUSTS_2", "confía más en");
        es.put("PETERS_ARCADE_TRUSTS_3", "hoy..");
        es.put("ACCOUNT_DISABLED_TO_REACTIVATE", "Cuenta desactivada. Para continuar debes reactivarla.");
        es.put("ERROR_NO_ACTIVE_SESSION", "No hay una sesión activa.");
        es.put("LOGOUT_SUCCESS", "Sesión cerrada correctamente.");
        es.put("ERROR_CURRENT_PASSWORD_WRONG", "La contraseña actual es incorrecta.");
        es.put("ERROR_ACCOUNT_DELETE_FAILED", "No se pudo eliminar la cuenta completamente.");
        es.put("ERROR_RIVAL_NOT_FOUND", "El usuario rival no existe.");
        es.put("ERROR_CANNOT_ADD_SELF", "No puedes agregarte a ti mismo.");
        es.put("FRIEND_ADDED_RIVAL", "Usuario agregado como amigo/rival.");
        es.put("ERROR_USER_NOT_AVAILABLE", "El usuario no está disponible.");
        es.put("ERROR_CANNOT_REQUEST_SELF", "No puedes enviarte una solicitud a ti mismo.");
        es.put("ERROR_ALREADY_FRIEND", "Este usuario ya es tu amigo.");
        es.put("FRIEND_REQUEST_ALREADY_SENT", "Solicitud de amistad ya enviada.");
        es.put("FRIEND_REQUEST_ACCEPTED", "Solicitud de amistad aceptada.");
        es.put("FRIEND_REQUEST_DECLINED", "Solicitud de amistad rechazada.");
        es.put("ERROR_COULD_NOT_LOAD_REQUEST", "No se pudo cargar la solicitud.");
        es.put("ERROR_REQUEST_NOT_CURRENT_USER", "Esta solicitud no pertenece al usuario actual.");
        es.put("ERROR_USER_NO_LONGER_AVAILABLE", "El usuario ya no está disponible.");
        es.put("ERROR_COULD_NOT_LOAD_CHALLENGE", "No se pudo cargar el reto.");
        es.put("ERROR_CHALLENGE_NOT_CURRENT_USER", "Este reto no pertenece al usuario actual.");
        es.put("CHALLENGE_DECLINED", "Reto rechazado.");
        es.put("ERROR_CHALLENGE_NOT_FINISHED", "El reto aún no ha finalizado.");
        es.put("ERROR_REWARD_NOT_USER", "Este premio no pertenece a este usuario.");
        es.put("ERROR_REWARD_ALREADY_CLAIMED", "Premio ya reclamado.");
        es.put("LEVEL_LOCKED", "Nivel bloqueado.");
        es.put("LEVEL_STARTED", "Nivel iniciado.");
        es.put("LEVEL_NOT_FOUND", "Nivel no encontrado.");
        es.put("LEVEL_PROGRESS_SAVED", "Nivel completado correctamente.");
        es.put("LEVEL_REPLAY_NO_POINTS", "Nivel completado nuevamente sin puntos adicionales.");
        es.put("STATS_UPDATED", "Estadísticas actualizadas correctamente.");
        es.put("RESULT_ALREADY_REGISTERED", "Este resultado ya fue registrado.");
        es.put("AUDIO_CONFIG_UPDATED", "Configuración de audio actualizada.");
        es.put("ERROR_VOLUME_RANGE", "El volumen debe estar entre 0 y 100.");
        es.put("ERROR_INVALID_AVATAR", "Avatar inválido.");
        es.put("ERROR_INVALID_COLOR", "Color inválido.");
        es.put("AVATAR_CHANGED", "Avatar cambiado correctamente.");
        es.put("PROFILE_UPDATED", "Perfil actualizado correctamente.");
    }
}
