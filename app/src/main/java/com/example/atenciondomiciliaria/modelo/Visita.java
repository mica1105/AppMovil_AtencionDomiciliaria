package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Visita implements Serializable {
    private int id;
    private Paciente paciente;
    private Enfermero enfermero;
    private String fechaAtencion;
    private String inicioAtencion;
    private String finAtencion;
    private String prestaciones;
    private int pacienteId;
    private boolean estado;

    public Visita() {
    }

    public Visita(int id, Paciente paciente, Enfermero enfermero, String fecha, String horaInicio, String horaFin, String prestaciones, boolean estado) {
        this.id = id;
        this.paciente = paciente;
        this.enfermero = enfermero;
        this.fechaAtencion = fecha;
        this.inicioAtencion = horaInicio;
        this.finAtencion = horaFin;
        this.prestaciones = prestaciones;
        this.estado = estado;

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

    public String getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(String fecha) {
        this.fechaAtencion = fecha;
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

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String convertirFecha(String fecha){
        String[] partes = fecha.split("-");
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }

    public String convertirHora(String hora){
        return hora.substring(0,5)+"hs";
    }

    public String modificarPrestaciones(List<String> prestaciones){
        String modificado = "";
        if(!prestaciones.isEmpty()){
            for (int i = 0; i < prestaciones.size(); i++) {
                modificado += prestaciones.get(i);
                if(i < prestaciones.size() - 1){
                    modificado += ", ";
                }
            }
        }
        return modificado;
    }
}
