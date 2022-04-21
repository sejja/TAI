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
@Table(name = "Tais")
public class Tai {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(max = 100)
    private String name;
    @Size(max = 100)
    private String grupo;
    @Size(max = 10)
    private String code;
    @Size(max = 50)
    private String palabra1;
    @Size(max = 50) // si no se pone esta anotación lo creo por defecto con size=255
    private String palabra2;
    @Size(max = 100) // si no se pone esta anotación lo creo por defecto con size=255
    private String imagen1;
    @Size(max = 100) // si no se pone esta anotación lo creo por defecto con size=255
    private String imagen2;
 
    @OneToMany(mappedBy = "tai", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Concepto> concepts;//OJO

    private Date created_at;
    private boolean enable;

    public Tai() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    Tai(String name, String grupo, List<Concepto> concepts, boolean enable) {

        this.name = name;
        this.grupo = grupo;
        this.concepts = concepts;
        this.enable = enable;
        this.created_at = Date.valueOf(LocalDate.now());
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrupo() {
        return this.grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPalabra1() {
        return this.palabra1;
    }

    public void setPalabra1(String palabra1) {
        this.palabra1 = palabra1;
    }

    public String getPalabra2() {
        return this.palabra2;
    }

    public void setPalabra2(String palabra2) {
        this.palabra2 = palabra2;
    }

    public String getImagen1() {
        return this.imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return this.imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public List<Concepto> getConcepts() {
        return this.concepts;
    }

    public void setConcepts(List<Concepto> concepts) {
        this.concepts = concepts;
    }

    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
