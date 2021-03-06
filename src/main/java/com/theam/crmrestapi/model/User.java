package com.theam.crmrestapi.model;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.SafeHtml;
import javax.persistence.*;

@Entity
@Proxy(lazy = false)
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "CRM_USERS")
@NamedQueries(
        {@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
         @NamedQuery(name="User.findByUsername", query="SELECT u FROM User u WHERE u.username=:username")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_USER", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false)
    private @SafeHtml String name;

    @Column(name = "SURNAME", nullable = false)
    private @SafeHtml String surname;

    @Column(name = "ROLE", nullable = false)
    private @SafeHtml String role;

    @Column(name = "USERNAME")
    private @SafeHtml String username;

    @Column(name = "PASSWORD")
    private @SafeHtml String password;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
