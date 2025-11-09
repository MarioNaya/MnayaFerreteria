package com.example.mnayaferreteria;

public class Usuario {

    private String usuario, nombre, apellidos;
    private int edad;
    private String pass, tipo;

    public Usuario(String usuario, String nombre, String apellidos, int edad, String pass, String tipo) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.pass = pass;
        this.tipo = tipo;
    }

    public Usuario(String nombre, String apellidos, String tipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
