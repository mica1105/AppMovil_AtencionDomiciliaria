package com.example.atenciondomiciliaria.modelo;

public class Registro {
    private int rutaImagen;
    private String descripcion;

    public Registro(int rutaImagen, String descripcion){
        this.rutaImagen = rutaImagen;
        this.descripcion= descripcion;
    }

    public int getRutaImagen() {
        return rutaImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }
}