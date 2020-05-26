package com.model.composite_key;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.model.PlayList;
import com.model.User;
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
    @JsonIgnoreProperties("roleInPlayLists")
    private PlayList playList;

    public RoleInPlayListId() {
    }

    public RoleInPlayListId(User user, PlayList playList) {
        this.user = user;
        this.playList = playList;
    }

    public int hashCode() {
        return Integer.hashCode(user.hashCode() + playList.hashCode());
    }
}