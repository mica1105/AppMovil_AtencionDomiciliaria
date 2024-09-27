package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;

public class HigieneyConfort implements Serializable {
    private int id;
    private String tipo;
    private String materiales;
    private boolean paniales;
    private boolean sondaVesical;
    private boolean sondaNasoGastrica;
    private String observaciones;
    private Visita visita;
    private int visitaId;

    public HigieneyConfort() {
    }

    public HigieneyConfort(int id, String materiales, String tipo, boolean paniales, boolean sondaVesical, boolean sondaNasoGastrica, String observaciones, Visita visita, int visitaId) {
        this.id = id;
        this.materiales = materiales;
        this.tipo = tipo;
        this.paniales = paniales;
        this.sondaVesical = sondaVesical;
        this.sondaNasoGastrica = sondaNasoGastrica;
        this.observaciones = observaciones;
        this.visita = visita;
        this.visitaId = visitaId;
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

    public boolean isPaniales() {
        return paniales;
    }

    public void setPaniales(boolean pañales) {
        this.paniales = pañales;
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

    public int getVisitaId() {
        return visitaId;
    }

    public void setVisitaId(int visitaId) {
        this.visitaId = visitaId;
    }
}
