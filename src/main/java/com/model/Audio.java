package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ApiModel
@Data
@Entity
public class Audio {

    @ApiModelProperty
    private @Id
    @GeneratedValue
    Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private Long fileId;

    @ApiModelProperty
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("audio")
    private Set<Tag> tags;

    @ApiModelProperty
    @ManyToMany(mappedBy = "listAudio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("listAudio")
    private Set<PlayList> playLists;

    public Audio() {
    }

    public Audio(String name, Long fileId) {
        this.name = name;
        this.fileId = fileId;
        tags = new HashSet<>();
    }

    public void setOneTag(Tag tag) {
        tags.add(tag);
    }

    public boolean checkTag(Tag tag) {
        return tags.contains(tag);
    }
}


