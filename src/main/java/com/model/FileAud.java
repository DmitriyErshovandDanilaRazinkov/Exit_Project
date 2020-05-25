package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@ApiModel
@Data
@Entity
public class FileAud {

    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private String originalName;

    @ApiModelProperty
    @Transient
    @ManyToMany(mappedBy = "listeners")
    @JsonIgnoreProperties("listeners")
    private Set<User> listeners;

    public FileAud(){}

    public FileAud(String name, String originalName) {
        this.name = name;
        this.originalName = originalName;
    }
}
