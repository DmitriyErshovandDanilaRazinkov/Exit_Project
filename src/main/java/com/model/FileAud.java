package com.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class FileAud {


    private @Id @GeneratedValue Long id;

    private String name;

    private String originalName;

    public FileAud(){}

    public FileAud(String name, String originalName) {
        this.name = name;
        this.originalName = originalName;
    }
}
