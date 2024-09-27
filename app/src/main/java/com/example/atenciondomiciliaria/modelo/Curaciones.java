package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;

public class Curaciones implements Serializable {
    private int id;
    private String tipo;
    private String ubicacion;
    private String clase;
    private float tamanio;
    private String bordes;
    private String signosInfeccion;
    private String dolor;
    private String observaciones;
    private Visita visita;
    private int visitaId;

    public Curaciones() {
    }

    public Curaciones(int id, String tipo, String ubicacion, String clase, float tamanio, String bordes, String signosInfeccion, String dolor, String observaciones, Visita visita, int v) {
        this.id = id;
        this.tipo = tipo;
        this.ubicacion= ubicacion;
        this.clase = clase;
        this.tamanio = tamanio;
        this.bordes = bordes;
        this.signosInfeccion = signosInfeccion;
        this.dolor = dolor;
        this.observaciones = observaciones;
        this.visita = visita;
        this.visitaId = v;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public float getTamanio() {
        return tamanio;
    }

    public void setTamanio(float tamaño) {
        this.tamanio = tamanio;
    }

    public String getBordes() {
        return bordes;
    }

    public void setBordes(String bordes) {
        this.bordes = bordes;
    }

    public String getSignosInfeccion() {
        return signosInfeccion;
    }

    public void setSignosInfeccion(String signosInfeccion) {
        this.signosInfeccion = signosInfeccion;
    }

    public String getDolor() {
        return dolor;
    }

    public void setDolor(String dolor) {
        this.dolor = dolor;
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
