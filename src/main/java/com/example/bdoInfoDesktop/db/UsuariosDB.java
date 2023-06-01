package com.example.bdoInfoDesktop.db;

import org.bson.types.ObjectId;

public class UsuariosDB {
    ObjectId _id;
    String usuario;
    String contraseña;

    String email;
    int maestriaCarne;
    int maestriaHierbas;
    int maestriaSangre;
    int maestriaTala;
    public UsuariosDB(){

    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMaestriaCarne() {
        return maestriaCarne;
    }

    public void setMaestriaCarne(int maestriaCarne) {
        this.maestriaCarne = maestriaCarne;
    }

    public int getMaestriaHierbas() {
        return maestriaHierbas;
    }

    public void setMaestriaHierbas(int maestriaHierbas) {
        this.maestriaHierbas = maestriaHierbas;
    }

    public int getMaestriaSangre() {
        return maestriaSangre;
    }

    public void setMaestriaSangre(int maestriaSangre) {
        this.maestriaSangre = maestriaSangre;
    }

    public int getMaestriaTala() {
        return maestriaTala;
    }

    public void setMaestriaTala(int maestriaTala) {
        this.maestriaTala = maestriaTala;
    }

}
