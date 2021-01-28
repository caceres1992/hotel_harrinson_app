package com.example.harrinsonhotelapp.response;

import com.example.harrinsonhotelapp.model.Huesped;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HuespedResponse {


    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Huesped")
    @Expose
    private Huesped huesped;
    @SerializedName("Valido")
    @Expose
    private Boolean valido;

    public HuespedResponse() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }
}
