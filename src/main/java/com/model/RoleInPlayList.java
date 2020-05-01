package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@ApiModel
@Data
@Entity
public class RoleInPlayList {

    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    @Enumerated(EnumType.ORDINAL)
    private Role_PlayList playListRole;

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

    public boolean equals(RoleInPlayList roleInPlayList) {
        return id.equals(roleInPlayList.getId());
    }

    public int hashCode() {
        return Long.hashCode(id);
    }

}