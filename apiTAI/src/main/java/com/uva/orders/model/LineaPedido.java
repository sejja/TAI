package com.uva.orders.model;

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
@Table(name = "LineasPedido")
public class LineaPedido {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer sku;
    @Size(max = 50) // si no se pone esta anotación lo creo por defecto con size=255
    private String name;
    private Float price;
    private Integer units;
    
    @JoinColumn(name="pedido_id", nullable = false)
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
    @JsonIgnore
    private Pedido pedido;

    LineaPedido() {
    }

    public LineaPedido(String name, Float price, Integer units, Pedido pedido) {
        this.name = name;
        this.price = price;
        this.units = units;
        this.pedido = pedido;
    }

    public Integer getSku() {
        return this.sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getUnits() {
        return this.units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Pedido getPedido(){
        return this.pedido;
    }

    public void setPedido(Pedido pedido){
        this.pedido = pedido;
    }

}
