package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;
import java.util.Date;

public class Paciente implements Serializable {

    private int id;
    private String nombre;
    private String apellido;
    private int dni;
    private String fechaNacimiento;
    private String domicilio;
    private String telefono;
    private String fechaAlta;
    private String fechaModificacion;
    private Estado estado;

    public Paciente() {
    }
    public Paciente(int id, String nombre, String apellido){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Paciente(int id, String nombre, String apellido, int dni, String fechaNacimiento, String domicilio, String telefono, String fechaAlta, String fechaModificacion, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.fechaAlta = fechaAlta;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String convertirFecha(String fecha){
        String[] partes = fecha.split("-");
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }
}
