package com.theam.crmrestapi.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Proxy(lazy = false)
@Table(name = "CRM_CUSTOMERS")
@PrimaryKeyJoinColumn(name="ID_USER")
public class Customer extends User {

    @Column(name = "PHOTOFIELD")
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
