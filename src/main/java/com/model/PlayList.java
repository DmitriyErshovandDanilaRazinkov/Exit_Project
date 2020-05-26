package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("playLists")
    private List<Audio> listAudio = new ArrayList<>();

    @ApiModelProperty
    @OneToMany(mappedBy = "idComp.playList", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("playList")
    private Set<RoleInPlayList> roleInPlayLists = new HashSet<>();

    public PlayList() {
    }

    public PlayList(String name, boolean isPrivate) {
        this.isPrivate = isPrivate;
        this.name = name;
    }

    public int hashCode() {
        return Long.hashCode(id);
    }

}
