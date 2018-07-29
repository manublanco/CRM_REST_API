package com.theam.crmrestapi.model;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Set;

@Entity
@Proxy(lazy = false)
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "CRM_USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_USER", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinTable(name = "CRM_USER_ROLES", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
    private Set<Role> roles;


    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, Set<Role> roles) {
        this.name = name;
        this.roles = roles;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
