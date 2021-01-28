package com.example.harrinsonhotelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipoHuesped {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descuento")
    @Expose
    private Object descuento;


    public TipoHuesped() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getDescuento() {
        return descuento;
    }

    public void setDescuento(Object descuento) {
        this.descuento = descuento;
    }

}
