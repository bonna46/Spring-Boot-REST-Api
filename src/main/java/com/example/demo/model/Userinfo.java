package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="USER_INFO")
public class Userinfo{

    @Id
    @GeneratedValue
    @Column
    public Integer id;
    @Column
    public String username;
    @Column
    public String password;

    public Userinfo(String username, String password)
    {
        this.username=username;
        this.password=password;
    }
    public Userinfo()
    {
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username=username;
    }

    public String getPassword()
    {
        return this.password;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
}
