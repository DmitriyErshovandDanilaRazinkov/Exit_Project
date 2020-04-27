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
public class RoleInPlayList {

    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("roleInPlayLists")
    private Set<PlayListRoles> playListRoles = new HashSet<>();

    @ApiModelProperty
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("roleUserInPlayLists")
    private PlayList playList;

    @ApiModelProperty
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("roleUserInPlayLists")
    private User user;

    public RoleInPlayList() {
    }

    public int hashCode() {
        return Long.hashCode(id);
    }

}