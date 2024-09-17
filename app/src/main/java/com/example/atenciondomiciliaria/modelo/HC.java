package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;

public class HC implements Serializable {
    private int id;
    private String diagnostico;
    private String medicacion;
    private String observaciones;
    private boolean fuma;
    private boolean bebe;
    private boolean drogas;
    private boolean dbt;
    private boolean hta;
    private String alergias;
    private String traumas;
    private String cirugias;
    private Paciente paciente;

    public HC() {
    }

    public HC(String diagnostico, String medicacion, String observaciones, boolean fuma, boolean bebe, boolean drogas, boolean dbt, boolean hta, String alergias, String traumas, String cirugias, Paciente paciente) {
        this.diagnostico = diagnostico;
        this.medicacion = medicacion;
        this.observaciones = observaciones;
        this.fuma = fuma;
        this.bebe = bebe;
        this.drogas = drogas;
        this.dbt = dbt;
        this.hta = hta;
        this.alergias = alergias;
        this.traumas = traumas;
        this.cirugias = cirugias;
        this.paciente = paciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMedicacion() {
        return medicacion;
    }

    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isFuma() {
        return fuma;
    }

    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    public boolean isBebe() {
        return bebe;
    }

    public void setBebe(boolean bebe) {
        this.bebe = bebe;
    }

    public boolean isDbt() {
        return dbt;
    }

    public void setDbt(boolean dbt) {
        this.dbt = dbt;
    }

    public boolean isDrogas() {
        return drogas;
    }

    public void setDrogas(boolean drogas) {
        this.drogas = drogas;
    }

    public boolean isHta() {
        return hta;
    }

    public void setHta(boolean hta) {
        this.hta = hta;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getTraumas() {
        return traumas;
    }

    public void setTraumas(String traumas) {
        this.traumas = traumas;
    }

    public String getCirugias() {
        return cirugias;
    }

    public void setCirugias(String cirugias) {
        this.cirugias = cirugias;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
