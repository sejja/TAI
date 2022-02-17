package com.uva.tai.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
//import javax.validation.constraints.Size;
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

    private Integer mb3;
    private Integer mb4;
    private Integer mb6;
    private Integer mb7;
    private Integer mb36;
    private Integer mb47;


    private Integer std3;
    private Integer std4;
    private Integer std6;
    private Integer std7;
    private Integer std36;
    private Integer std47;


    private Date created_at;


    public Resultado() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    public Resultado(Integer idResp, Integer idTai, Integer mb3, Integer mb4, Integer mb6, Integer mb7, Integer mb36, Integer mb47, 
        Integer std3, Integer std4, Integer std6, Integer std7, Integer std36, Integer std47) {

        this.idResp = idResp;
        this.idTai = idTai;
        this.mb3 = mb3;
        this.mb4 = mb4;
        this.mb6 = mb6;
        this.mb7 = mb7;
        this.mb36 = mb36;
        this.mb47 = mb47;
        this.std3 = std3;
        this.std4 = std4;
        this.std6 = std6;
        this.std7 = std7;
        this.std36 = std36;
        this.std47 = std47;





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

    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getMb3() {
        return this.mb3;
    }

    public void setMb3(Integer mb3) {
        this.mb3 = mb3;
    }

    public Integer getMb4() {
        return this.mb4;
    }

    public void setMb4(Integer mb4) {
        this.mb4 = mb4;
    }

    public Integer getMb6() {
        return this.mb6;
    }

    public void setMb6(Integer mb6) {
        this.mb6 = mb6;
    }

    public Integer getMb7() {
        return this.mb7;
    }

    public void setMb7(Integer mb7) {
        this.mb7 = mb7;
    }

    public Integer getMb36() {
        return this.mb36;
    }

    public void setMb36(Integer mb36) {
        this.mb36 = mb36;
    }

    public Integer getMb47() {
        return this.mb47;
    }

    public void setMb47(Integer mb47) {
        this.mb47 = mb47;
    }

    public Integer getStd3() {
        return this.std3;
    }

    public void setStd3(Integer std3) {
        this.std3 = std3;
    }

    public Integer getStd4() {
        return this.std4;
    }

    public void setStd4(Integer std4) {
        this.std4 = std4;
    }

    public Integer getStd6() {
        return this.std6;
    }

    public void setStd6(Integer std6) {
        this.std6 = std6;
    }

    public Integer getStd7() {
        return this.std7;
    }

    public void setStd7(Integer std7) {
        this.std7 = std7;
    }

    public Integer getStd36() {
        return this.std36;
    }

    public void setStd36(Integer std36) {
        this.std36 = std36;
    }

    public Integer getStd47() {
        return this.std47;
    }

    public void setStd47(Integer std47) {
        this.std47 = std47;
    }

    public Integer getDiff63() {
        return this.mb6 - this.mb3;
    }

    public Integer getDiff74() {
        return this.mb7 - this.mb4;
    }

    public Float getDscore36() {
        return this.getDiff63() * 1f / this.std36;
    }
    
    public Float getDscore47() {
        return this.getDiff74() * 1f / this.std47;
    }



}
