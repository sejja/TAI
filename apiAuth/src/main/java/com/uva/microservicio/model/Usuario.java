package com.uva.microservicio.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.Id;

/**
 * Define los atibitos de la nos usuarios, será mediante esta clase
 * que se creara la tabla de la base de datos
 */
@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue
    private Integer Id;
    @Size(max = 50) // si no se pone esta anotación lo creo por defecto con size=255
    private String name;
    @Size(max = 30)
    private String firstName;
    @Size(max = 30)
    private String lastName;
    @Size(max = 50)
    private String email;
    private String password;
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;

    Usuario() {
    }

    public Usuario(String name, String firstName, String lastName, String email, String password,
            boolean enabled, Date createdAt, Date updatedAt) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
