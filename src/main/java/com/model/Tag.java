package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@ApiModel
@Entity
@Data
public class Tag {
    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    @Transient
    @ManyToMany(mappedBy = "tags")
    private Set<Audio> audios;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }


    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
