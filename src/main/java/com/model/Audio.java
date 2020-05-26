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
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private boolean premium;

    @ApiModelProperty
    private Long countListen = 0L;

    @ApiModelProperty
    @OneToOne(fetch = FetchType.LAZY)
    private FileAud file;

    @ApiModelProperty
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("audio")
    private Set<Tag> tags;

    @ApiModelProperty
    @ManyToMany(mappedBy = "listAudio", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("listAudio")
    private Set<PlayList> playLists;

    public Audio() {
    }

    public Audio(String name, boolean isPremium, FileAud file) {
        this.name = name;
        this.file = file;
        this.premium = isPremium;
        tags = new HashSet<>();
    }

    public void setOneTag(Tag tag) {
        tags.add(tag);
    }

    public boolean checkTag(Tag tag) {
        return tags.contains(tag);
    }
}


