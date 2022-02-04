package com.uva.orders.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(max = 50) // si no se pone esta anotación lo creo por defecto con size=255
    private String deliveryAddress;
    @Size(max = 50)
    private String deliveryPostalCode;
    @Size(max = 50)
    private String deliveryCity;
    @Size(max = 50) 
    private String deliveryPerson;
    private Integer idSeller;
 
    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<LineaPedido> products;//OJO
    
    private Estado status;
    private Date expectedDeliveryDate;
    private Date created_at;

    @Transient
    private Float cost;

    Pedido() {
        this.created_at = Date.valueOf(LocalDate.now());
    }

    Pedido(String deliveryAddress, String deliveryPostalCode,
    String deliveryCity, String deliveryPerson, Integer idSeller,
    List<LineaPedido> products, Estado status, Date expectedDeliveryDate) {
        this.deliveryAddress = deliveryAddress;
        this.deliveryPostalCode = deliveryPostalCode;
        this.deliveryCity = deliveryCity;
        this.deliveryPerson = deliveryPerson;
        this.idSeller = idSeller;
        this.products = products;
        this.status = status;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.created_at = Date.valueOf(LocalDate.now());
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryPostalCode() {
        return this.deliveryPostalCode;
    }

    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.deliveryPostalCode = deliveryPostalCode;
    }

    public String getDeliveryCity() {
        return this.deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryPerson() {
        return this.deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public Integer getIdSeller() {
        return this.idSeller;
    }

    public void setIdSeller(Integer idSeller) {
        this.idSeller = idSeller;
    }

    public List<LineaPedido> getProducts() {
        return this.products;
    }

    public void setProducts(List<LineaPedido> products) {
        this.products = products;
    }

    public Estado getStatus() {
        return this.status;
    }

    public void setStatus(Estado status) {
        this.status = status;
    }

    public Date getExpectedDeliveryDate() {
        return this.expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Date getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Float getCost() {
        this.cost = 0.0F;
        for(LineaPedido product: this.products)
            this.cost += product.getPrice() * product.getUnits();
        return this.cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
