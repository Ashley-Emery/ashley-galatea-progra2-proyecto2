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

public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;

    private String log;
    private String timestamp;

    public Actividad(String log) {
        this.log = log;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getLog() {
        return log;
    }

    public String getTimestamp() {
        return timestamp;
    }
}