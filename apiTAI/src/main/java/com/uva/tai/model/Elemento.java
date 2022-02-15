package com.uva.tai.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Define los atributos de las Lineas de Pedido, será mediante esta clase
 * que se creara la tabla de la base de datos
 */
@Entity
@Table(name = "Elementos")
public class Elemento {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    @Size(max = 50) // si no se pone esta anotación lo creo por defecto con size=255
    private String tipo;
    private Boolean correcta;
    private Integer tiempo;
    
    @JoinColumn(name="resp_id", nullable = false)
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
    @JsonIgnore
    private Respuesta resp;

    Elemento() {
    }

    Elemento(String tipo, Boolean correcta, Integer tiempo, Respuesta resp) {
        this.tipo = tipo;
        this.correcta = correcta;
        this.tiempo = tiempo;
        this.resp = resp;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getCorrecta() {
        return this.correcta;
    }

    public void setCorrecta(Boolean correcta) {
        this.correcta = correcta;
    }

    public Respuesta getResp(){
        return this.resp;
    }

    public void setResp(Respuesta resp){
        this.resp = resp;
    }

    public Integer getTiempo() {
        return this.tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

}
