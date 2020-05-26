package com.model;

import com.model.composite_key.RoleInPlayListId;
import com.model.enums.Role_PlayList;
import com.sun.org.apache.xpath.internal.objects.XString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

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

    public String toString() {
        return idComp.hashCode() + "";
    }
}