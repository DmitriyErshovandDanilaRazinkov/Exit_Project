package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel
@Data
@Entity
public class RoleInPlayList {

    @EmbeddedId
    private RoleInPlayListId idComp;

    @ApiModelProperty
    @Enumerated(EnumType.ORDINAL)
    private Role_PlayList playListRole;


    public RoleInPlayList() {
    }

    public RoleInPlayList(RoleInPlayListId roleInPlayListId) {
        this.idComp = roleInPlayListId;
    }

}