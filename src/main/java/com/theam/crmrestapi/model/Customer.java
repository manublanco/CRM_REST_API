package com.theam.crmrestapi.model;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Proxy(lazy = false)
@Table(name = "CRM_CUSTOMERS")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CUSTOMER", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false)
    @SafeHtml
    private String name;

    @Column(name = "SURNAME", nullable = false)
    @SafeHtml
    private String surname;

    @Column(name = "PHOTOFIELD")
    @SafeHtml
    private String photoField;

    @OneToOne
    @JoinColumn(name = "CREATEDBY")
    private User createdBy;

    @Column(name = "CREATIONDATE", nullable = false)
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "UPDATEDBY")
    private User updatedBy;

    @Column(name = "LASTUPDATE")
    private Date lastUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhotoField() {
        return photoField;
    }

    public void setPhotoField(String photoField) {
        this.photoField = photoField;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
