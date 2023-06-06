package com.example.tfg_dampc.db;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.bson.types.ObjectId;

public class ZonasDB {
    ObjectId _id;
    private SimpleStringProperty nombre;
    private SimpleStringProperty tipoZona;
    private SimpleIntegerProperty item1;
    private SimpleIntegerProperty item2;
    private SimpleIntegerProperty item3;
    private SimpleIntegerProperty item4;

    public ZonasDB(){

    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getTipoZona() {
        return tipoZona.get();
    }

    public void setTipoZona(String tipoZona) {
        this.tipoZona = new SimpleStringProperty(tipoZona);
    }

    public int getItem1() {
        return item1.get();
    }

    public void setItem1(int item1) {
        this.item1 = new SimpleIntegerProperty(item1);
    }

    public int getItem2() {
        return item2.get();
    }

    public void setItem2(int item2) {
        this.item2 = new SimpleIntegerProperty(item2);
    }

    public int getItem3() {
        return item3.get();
    }

    public void setItem3(int item3) {
        this.item3 = new SimpleIntegerProperty(item3);
    }

    public int getItem4() {
        return item4.get();
    }

    public void setItem4(int item4) {
        this.item4 = new SimpleIntegerProperty(item4);
    }
}
