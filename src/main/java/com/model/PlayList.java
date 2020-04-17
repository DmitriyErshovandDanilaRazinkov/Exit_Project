package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User owner;

    @ApiModelProperty
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("playLists")
    private Set<Audio> listAudio;


    public PlayList() {
    }

    public PlayList(String name, boolean isPrivate) {
        this.isPrivate = isPrivate;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof PlayList))
            return false;

        return id != null && id.equals(((PlayList) o).getId());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
