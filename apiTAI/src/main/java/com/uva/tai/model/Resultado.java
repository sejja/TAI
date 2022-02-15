package com.uva.tai.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.Id;


/**
 * Define los atributos de los Pedido, será mediante esta clase
 * que se creara la tabla de la base de datos
 */
@Entity
@Table(name = "Resultados")
public class Resultado {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer idResp;
    @Size(max = 50)
    private String asociacion1;
    private Integer trespuesta1;
    @Size(max = 50)
    private String asociacion2;
    private Integer trespuesta2;

    private Date created_at;

    public Resultado() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    Resultado(Integer idResp, String asociacion1, Integer trespuesta1, String asociacion2, Integer trespuesta2) {

        this.idResp = idResp;
        this.asociacion1 = asociacion1;
        this.trespuesta1 = trespuesta1;
        this.asociacion2 = asociacion2;
        this.trespuesta2 = trespuesta2;

        this.created_at = Date.valueOf(LocalDate.now());
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdResp() {
        return this.idResp;
    }

    public void setIdResp(Integer idResp) {
        this.idResp = idResp;
    }


    public String getAsociacion1(){
        return this.asociacion1;
    }

    public void setAsociacion1(String asociacion1){
        this.asociacion1 = asociacion1;
    }

    public Integer getTrespuesta1() {
        return this.trespuesta1;
    }

    public void setTrespuesta1(Integer trespuesta1) {
        this.trespuesta1 = trespuesta1;
    }

    public String getAsociacion2() {
        return this.asociacion2;
    }

    public void setAsociacion2(String asociacion2) {
        this.asociacion2 = asociacion2;
    }

    public Integer getTrespuesta2() {
        return this.trespuesta2;
    }

    public void setTrespuesta2(Integer trespuesta2) {
        this.trespuesta2 = trespuesta2;
    }


    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

}
