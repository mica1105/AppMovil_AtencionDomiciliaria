package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Visita implements Serializable {
    private int id;
    private Paciente paciente;
    private Enfermero enfermero;
    private Date fecha;
    private String inicioAtencion;
    private String finAtencion;
    private String prestaciones;

    public Visita() {
    }

    public Visita(int id, Paciente paciente, Enfermero enfermero, Date fecha, String horaInicio, String horaFin, String prestaciones) {
        this.id = id;
        this.paciente = paciente;
        this.enfermero = enfermero;
        this.fecha = fecha;
        this.inicioAtencion = horaInicio;
        this.finAtencion = horaFin;
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

    public String getInicioAtencion() {
        return inicioAtencion;
    }

    public void setInicioAtencion(String inicioAtencion) {
        this.inicioAtencion = inicioAtencion;
    }

    public String getFinAtencion() {
        return finAtencion;
    }

    public void setFinAtencion(String finAtencion) {
        this.finAtencion = finAtencion;
    }

    public String getPrestaciones() {
        return prestaciones;
    }

    public void setPrestaciones(String prestaciones) {
        this.prestaciones = prestaciones;
    }

    public String convertirFecha(Date fecha){
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); // Defines el formato
        return formato.format(fecha);
    }

    public String convertirHora(String hora){
        return hora.substring(0,5)+"hs";
    }
}
