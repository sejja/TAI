package com.uva.orders.model;

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
    @Size(max = 50)
    private String name;
 
    @OneToMany(mappedBy = "tai", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Concepto> concepts;//OJO

    private Date created_at;

    Tai() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    Tai(String name, List<Concepto> concepts) {

        this.name = name;
        this.concepts = concepts;
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

}
