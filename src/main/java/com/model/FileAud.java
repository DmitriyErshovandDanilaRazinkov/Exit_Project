package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ApiModel
@Data
@Entity
public class FileAud {

    @ApiModelProperty
    private @Id @GeneratedValue Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private String originalName;

    public FileAud(){}

    public FileAud(String name, String originalName) {
        this.name = name;
        this.originalName = originalName;
    }
}
