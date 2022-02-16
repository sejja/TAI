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
    private Integer idTai;
    
    @Size(max = 50)
    private String asociacion1;
    private Integer tmedio1;
    private Integer std1;

    @Size(max = 50)
    private String asociacion2;
    private Integer tmedio2;
    private Integer std2;

    private Date created_at;

    private Integer tmedio;
    private Integer std;

    public Resultado() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    public Resultado(Integer idResp, Integer idTai, String asociacion1, Integer tmedio1, Integer std1, 
    String asociacion2, Integer tmedio2, Integer std2, Integer tmedio, Integer std ) {

        this.idResp = idResp;
        this.idTai = idTai;
        this.asociacion1 = asociacion1;
        this.tmedio1 = tmedio1;
        this.std1 = std1;
        this.asociacion2 = asociacion2;
        this.tmedio2 = tmedio2;
        this.std2 = std2;
        this.tmedio = tmedio;
        this.std = std;

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
    

    public Integer getIdTai() {
        return this.idTai;
    }

    public void setIdTai(Integer idTai) {
        this.idTai = idTai;
    }

    public String getAsociacion1(){
        return this.asociacion1;
    }

    public void setAsociacion1(String asociacion1){
        this.asociacion1 = asociacion1;
    }

    public Integer getTmedio1() {
        return this.tmedio1;
    }

    public void setTmedio1(Integer tmedio1) {
        this.tmedio1 = tmedio1;
    }

    public Integer getStd1() {
        return this.std1;
    }

    public void setStd1(Integer std1) {
        this.std1 = std1;
    }

    public String getAsociacion2() {
        return this.asociacion2;
    }

    public void setAsociacion2(String asociacion2) {
        this.asociacion2 = asociacion2;
    }

    public Integer getTmadio2() {
        return this.tmedio2;
    }

    public void setTrespuesta2(Integer tmedio2) {
        this.tmedio2 = tmedio2;
    }

    public Integer getStd2() {
        return this.std2;
    }

    public void setStd2(Integer std2) {
        this.std2 = std2;
    }

    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getTmedio() {
        return this.tmedio;
    }

    public void setTmedio(Integer tmedio) {
        this.tmedio = tmedio;
    }

    public Integer getStd() {
        return this.std;
    }

    public void setStd(Integer std) {
        this.std = std;
    }

    public Integer getDiff() {
        return this.tmedio1 - this.tmedio2;
    }

}
