package com.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Data
@Entity
public class User {
    private @Id @GeneratedValue Long id;

    private String name;

    private UserTypes userType;

    public User(){}

    public User(String name, UserTypes userType){
        this.name = name;
        this.userType = userType;
    }

}
