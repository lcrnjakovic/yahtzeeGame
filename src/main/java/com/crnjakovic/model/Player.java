package com.crnjakovic.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lukacrnjakovic on 4/17/18.
 */
@Entity
@Table(name="users")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    public Player() {
    }

    public Player(String userName, String password, String role, String fullName) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;
    @Column(name="username")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;
    @Column(name="full_name")
    private String fullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
