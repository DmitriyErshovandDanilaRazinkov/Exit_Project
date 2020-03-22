package com.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Audio {

    private @Id @GeneratedValue Long id;

    private String name;

    private Long fileId;

    private String tag;

    public Audio() {}

    public Audio(String name,  String tag, Long fileId){
        this.name = name;
        this.tag = tag;
        this.fileId = fileId;
    }
}


