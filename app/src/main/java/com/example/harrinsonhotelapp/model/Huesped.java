package com.example.harrinsonhotelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Huesped {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("documento")
    @Expose
    private String documento;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("estado")
    @Expose
    private Boolean estado;
    @SerializedName("tipoDocumento")
    @Expose
    private TipoDocumento tipoDocumento;
    @SerializedName("tipoHuesped")
    @Expose
    private TipoHuesped tipoHuesped;


    public Huesped() {
    }

    public Huesped(String password, String correo) {
        this.password = password;
        this.correo = correo;
    }


    public Huesped(String nombre, String apellido, String password, String correo,String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.correo = correo;
        this.documento = dni;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public TipoHuesped getTipoHuesped() {
        return tipoHuesped;
    }

    public void setTipoHuesped(TipoHuesped tipoHuesped) {
        this.tipoHuesped = tipoHuesped;
    }
}
