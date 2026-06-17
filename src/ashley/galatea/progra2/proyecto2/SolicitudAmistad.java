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

public class SolicitudAmistad implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String solicitante;
    private String receptor;
    private Date fechaCreacion;
    private boolean finalizada;
    private boolean aceptada;

    public SolicitudAmistad(String solicitante, String receptor) {
        this.id = "FR-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        this.solicitante = solicitante;
        this.receptor = receptor;
        this.fechaCreacion = new Date();
        this.finalizada = false;
        this.aceptada = false;
    }

    public String getId() {
        return id;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getReceptor() {
        return receptor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public void aceptar() {
        this.aceptada = true;
        this.finalizada = true;
    }

    public void declinar() {
        this.aceptada = false;
        this.finalizada = true;
    }
}
