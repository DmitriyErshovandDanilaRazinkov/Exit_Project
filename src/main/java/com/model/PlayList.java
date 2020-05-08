package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ApiModel
@Data
@Entity
public class PlayList {

    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    private boolean isPrivate;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private String joinCode;

    @ApiModelProperty
    @ManyToMany(mappedBy = "playLists", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("playLists")
    private Set<User> users = new HashSet<>();

    @ApiModelProperty
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("playLists")
    private Set<Audio> listAudio = new HashSet<>();

    @ApiModelProperty
    @OneToMany(mappedBy = "playList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("playList")
    private Set<RoleInPlayList> roleInPlayLists = new HashSet<>();

    public PlayList() {
    }

    public PlayList(String name, boolean isPrivate) {
        this.isPrivate = isPrivate;
        this.name = name;
    }

    public boolean equals(PlayList playList) {
        return id.equals(playList.getId());
    }

    public int hashCode() {
        return Long.hashCode(id);
    }

}
