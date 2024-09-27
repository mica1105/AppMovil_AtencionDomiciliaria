package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;

public class Csv implements Serializable {
  private int id;
  private String ta;
  private int fc;
  private int so2;
  private float temp;
  private int hgt;
  private String observaciones;
  private Visita visita;
  private int visitaId;

  public Csv() {

  }
  public Csv(int id, String ta, int fc, int so2, float temp, int hgt, String observaciones, Visita visita, int visitaId) {
    this.id = id;
    this.ta = ta;
    this.fc = fc;
    this.so2 = so2;
    this.temp = temp;
    this.hgt = hgt;
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

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public int getSo2() {
        return so2;
    }

    public void setSo2(int so2) {
        this.so2 = so2;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getHgt() {
        return hgt;
    }

    public void setHgt(int hgt) {
        this.hgt = hgt;
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
