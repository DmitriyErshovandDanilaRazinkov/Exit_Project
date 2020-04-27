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
@EqualsAndHashCode
public class PlayListRoles {

    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    @ManyToMany(mappedBy = "playListRoles", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("playListRoles")
    private Set<RoleInPlayList> roleInPlayLists = new HashSet<>();


}
