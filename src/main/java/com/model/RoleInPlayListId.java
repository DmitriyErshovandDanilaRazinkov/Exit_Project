package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class RoleInPlayListId implements Serializable {

    @ApiModelProperty
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("roleUserInPlayLists")
    private User user;

    @ApiModelProperty
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("roleUserInPlayLists")
    private PlayList playList;

    public RoleInPlayListId() {
    }

    public RoleInPlayListId(User user, PlayList playList) {
        this.user = user;
        this.playList = playList;
    }
}