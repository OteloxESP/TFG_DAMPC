package com.example.tfg_dampc.db;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.bson.types.ObjectId;

public class UsuariosDB {
    ObjectId _id;
    private SimpleStringProperty usuario;
    private SimpleStringProperty contrasena;
    private SimpleStringProperty email;
    private SimpleIntegerProperty maestriaCarne;
    private SimpleIntegerProperty maestriaHierbas;
    private SimpleIntegerProperty maestriaSangre;
    private SimpleIntegerProperty maestriaTala;
    private SimpleIntegerProperty administrador;

    public UsuariosDB(){

    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario = new SimpleStringProperty(usuario);
    }

    public String getContrasena() {
        return contrasena.get();
    }

    public void setContrasena(String contrasena) {
        this.contrasena = new SimpleStringProperty(contrasena);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public int getMaestriaCarne() {
        return maestriaCarne.get();
    }

    public void setMaestriaCarne(int maestriaCarne) {
        this.maestriaCarne = new SimpleIntegerProperty(maestriaCarne);
    }

    public int getMaestriaHierbas() {
        return maestriaHierbas.get();
    }

    public void setMaestriaHierbas(int maestriaHierbas) {
        this.maestriaHierbas = new SimpleIntegerProperty(maestriaHierbas);
    }

    public int getMaestriaSangre() {
        return maestriaSangre.get();
    }

    public void setMaestriaSangre(int maestriaSangre) {
        this.maestriaSangre = new SimpleIntegerProperty(maestriaSangre);
    }

    public int getMaestriaTala() {
        return maestriaTala.get();
    }

    public void setMaestriaTala(int maestriaTala) {
        this.maestriaTala = new SimpleIntegerProperty(maestriaTala);
    }

    public int getAdministrador() {
        return administrador.get();
    }

    public void setAdministrador(int administrador) {
        this.administrador = new SimpleIntegerProperty(administrador);
    }
}
