package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;

public class AdmDeFarmacos implements Serializable {

    private int id;
    private String via;
    private String medicacion;
    private double dosis;
    private String ra;
    private String observaciones;
    private Visita visita;

    public AdmDeFarmacos() {
    }

    public AdmDeFarmacos(int id, String via, String medicacion, float dosis, String ra, String observaciones, Visita visita) {
        this.id = id;
        this.via = via;
        this.medicacion = medicacion;
        this.dosis = dosis;
        this.ra = ra;
        this.observaciones = observaciones;
        this.visita = visita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getMedicacion() {
        return medicacion;
    }

    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }

    public double getDosis() {
        return dosis;
    }

    public void setDosis(double dosis) {
        this.dosis = dosis;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }
}
