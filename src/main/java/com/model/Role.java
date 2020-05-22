package com.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@ApiModel
@Entity
@Data
public class Role implements GrantedAuthority {
    @ApiModelProperty
    @Id
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    @Transient
    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("role")
    private Set<User> users;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}