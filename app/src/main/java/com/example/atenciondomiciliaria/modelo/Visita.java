package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Visita implements Serializable {
    private int id;
    private Paciente paciente;
    private Enfermero enfermero;
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;
    private String prestaciones;

    public Visita() {
    }

    public Visita(int id, Paciente paciente, Enfermero enfermero, Date fecha, Time horaInicio, Time horaFin, String prestaciones) {
        this.id = id;
        this.paciente = paciente;
        this.enfermero = enfermero;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.prestaciones = prestaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Enfermero getEnfermero() {
        return enfermero;
    }

    public void setEnfermero(Enfermero enfermero) {
        this.enfermero = enfermero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public String getPrestaciones() {
        return prestaciones;
    }

    public void setPrestaciones(String prestaciones) {
        this.prestaciones = prestaciones;
    }
}
