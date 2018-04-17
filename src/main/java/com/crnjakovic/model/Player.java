package com.crnjakovic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lukacrnjakovic on 4/17/18.
 */
@Entity
@Table(name="users")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="username")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role;
    @Column(name="full_name")
    private String fullName;

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
