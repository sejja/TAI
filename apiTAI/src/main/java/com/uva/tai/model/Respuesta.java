package com.uva.tai.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

/**
 * Define los atributos de los Pedido, será mediante esta clase
 * que se creara la tabla de la base de datos
 */
@Entity
@Table(name = "Respuestas")
public class Respuesta {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer idTai;
    @Size(max = 10)
    private String codeEnc;
 
    @OneToMany(mappedBy = "resp", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Elemento> resp;//OJO
    private Date created_at;
    private String sex;
    private Integer age;

    public Respuesta() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    Respuesta(String codeEnc, Integer idTai, List<Elemento> resp) {

        this.codeEnc = codeEnc;
        this.idTai = idTai;
        this.resp = resp;
        this.created_at = Date.valueOf(LocalDate.now());
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeEnc() {
        return this.codeEnc;
    }

    public void setCodeEnc(String codeEnc) {
        this.codeEnc = codeEnc;
    }

    public Integer getIdTai() {
        return this.idTai;
    }

    public void setIdTai(Integer idTai) {
        this.idTai = idTai;
    }

    public List<Elemento> getResp() {
        return this.resp;
    }

    public void setResp(List<Elemento> resp) {
        this.resp = resp;
    }

    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
