package com.theam.crmrestapi.model;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

/**
 * The Class Role.
 */
@Entity
@Proxy(lazy = false)
@Table(name = "CRM_ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ROLE", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

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
}
