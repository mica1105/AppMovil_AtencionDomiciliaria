package com.example.atenciondomiciliaria.modelo;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Enfermero implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private int dni;
    private String telefono;
    private String domicilio;
    private String email;
    private String password;
    private String avatar;
    private Estado estado;
    private int estadoId;

    public Enfermero() {
    }

    public Enfermero(int id, String nombre, String apellido, int dni, String telefono, String domicilio, String email, String password, String avatar, Estado estado, int estadoId) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.estado = estado;
        this.estadoId= estadoId;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Enfermero enfermero = (Enfermero) obj;
        return id == enfermero.id &&
                dni == enfermero.dni &&
                Objects.equals(nombre, enfermero.nombre) &&
                Objects.equals(apellido, enfermero.apellido) &&
                Objects.equals(telefono, enfermero.telefono) &&
                Objects.equals(domicilio, enfermero.domicilio) &&
                Objects.equals(email, enfermero.email) &&
                Objects.equals(password, enfermero.password) &&
                Objects.equals(avatar, enfermero.avatar) &&
                Objects.equals(estado, enfermero.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dni, nombre, apellido, telefono, domicilio, email, password, avatar, estado);
    }

}
