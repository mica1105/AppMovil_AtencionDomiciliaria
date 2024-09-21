package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;

public class HigieneyConfort implements Serializable {
    private int id;
    private String tipo;
    private String materiales;
    private boolean pañales;
    private boolean sondaVesical;
    private boolean sondaNasoGastrica;
    private String observaciones;
    private Visita visita;

    public HigieneyConfort() {
    }

    public HigieneyConfort(int id, String materiales, String tipo, boolean pañales, boolean sondaVesical, boolean sondaNasoGastrica, String observaciones, Visita visita) {
        this.id = id;
        this.materiales = materiales;
        this.tipo = tipo;
        this.pañales = pañales;
        this.sondaVesical = sondaVesical;
        this.sondaNasoGastrica = sondaNasoGastrica;
        this.observaciones = observaciones;
        this.visita = visita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public boolean isPañales() {
        return pañales;
    }

    public void setPañales(boolean pañales) {
        this.pañales = pañales;
    }

    public boolean isSondaVesical() {
        return sondaVesical;
    }

    public void setSondaVesical(boolean sondaVesical) {
        this.sondaVesical = sondaVesical;
    }

    public boolean isSondaNasoGastrica() {
        return sondaNasoGastrica;
    }

    public void setSondaNasoGastrica(boolean sondaNasoGastrica) {
        this.sondaNasoGastrica = sondaNasoGastrica;
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
