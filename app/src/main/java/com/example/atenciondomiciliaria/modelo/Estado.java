package com.example.atenciondomiciliaria.modelo;

import java.io.Serializable;

public class Estado implements Serializable {

    private int id;
    private String descripcion;

    public Estado(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}



