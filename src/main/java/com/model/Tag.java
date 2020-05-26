package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long countListen = 0L;

    @ApiModelProperty
    @Transient
    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tag")
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
