package com.uva.orders.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
//import javax.persistence.Transient;
//import javax.validation.constraints.Size;
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
 
    @OneToMany(mappedBy = "tai", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Palabra> palabrasPositivas;//OJO

    @OneToMany(mappedBy = "tai", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Palabra> palabrasNegativas;// OJO

    @OneToMany(mappedBy = "tai", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Imagen> imagenes1;// OJO

    @OneToMany(mappedBy = "tai", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Imagen> imagenes2;// OJO
    
    private Estado status;
    private Date created_at;

    Tai() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    Tai(Set<Palabra> palabrasPositivas, 
            Set<Palabra> palabrasNegativas, Estado status,
            Set<Imagen> imagenes1, Set<Imagen> imagenes2) {

        this.imagenes1 = imagenes1;
        this.imagenes2 = imagenes1;
        this.palabrasPositivas = palabrasPositivas;
        this.palabrasNegativas = palabrasNegativas;
        this.status = status;
        this.created_at = Date.valueOf(LocalDate.now());
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Palabra> getPalabrasPositivas() {
        return new ArrayList<>(this.palabrasPositivas);
    }

    public List<Palabra> getPalabrasNegativas() {
        return new ArrayList<>(this.palabrasNegativas);
    }

    public List<Imagen> getImagenes1() {
        return new ArrayList<>(this.imagenes1);
    }

    public List<Imagen> getImagenes2() {
        return new ArrayList<>(this.imagenes2);
    }

    public void setPalabrasPositivas(List<Palabra> palabrasPositivas) {
        this.palabrasPositivas = new HashSet<Palabra>(palabrasPositivas);
    }

    public void setPalabrasNegativas(List<Palabra> palabrasNegativas) {
        this.palabrasNegativas = new HashSet<Palabra>(palabrasNegativas);
    }

    public void setImagenes1(List<Imagen> imagenes1) {
        this.imagenes1 = new HashSet<Imagen>(imagenes1);
    }

    public void setImagenes2(List<Imagen> imagenes2) {
        this.imagenes2 = new HashSet<Imagen>(imagenes2);
    }

    public Estado getStatus() {
        return this.status;
    }

    public void setStatus(Estado status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }
}
