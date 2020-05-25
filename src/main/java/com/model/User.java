package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;


@ApiModel
@Data
@Entity
public class User {

    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_USER = "ROLE_USER";

    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    @Size(min = 5, message = "Не меньше 5 знаков")
    private String username;

    @ApiModelProperty
    @Size(min = 5, message = "Не меньше 5 знаков")
    private String password;

    @ApiModelProperty
    private String role;

    @ApiModelProperty
    private double cash = 0;

    @ApiModelProperty
    private boolean premium = false;

    @ApiModelProperty
    private Date endPremium;

    @ApiModelProperty
    @OneToMany(mappedBy = "idComp.user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private Set<RoleInPlayList> roleInPlayLists = new HashSet<>();




    public User() {
    }

    public int hashCode() {
        return Long.hashCode(id);
    }
}
