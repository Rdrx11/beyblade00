package com.example.beyblade00.Model;

public class BeybladeModel {
    private String nombre;
    private String sistema;
    private String contrasena;

    public BeybladeModel (String nombre, String sistema, String contrasena) {
        this.nombre = nombre;
        this.sistema = sistema;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSistema() {
        return sistema;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString(){
        return "["+ sistema + "] " + nombre;
    }
}
