package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;


@ApiModel
@Data
@Entity
public class User implements UserDetails {

    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    @Size(min = 2, message = "Не меньше 5 знаков")
    private String username;

    @ApiModelProperty
    @Size(min = 2, message = "Не меньше 5 знаков")
    private String password;

    @ApiModelProperty
    private double cash = 0;

    @ApiModelProperty
    private boolean premium = false;

    @ApiModelProperty
    private Date endPremium;

    @Transient
    private String passwordConfirm;

    @ApiModelProperty
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    private Set<Role> roles = new HashSet<>();

    @ApiModelProperty
    @OneToMany(mappedBy = "idComp.user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private Set<RoleInPlayList> roleInPlayLists = new HashSet<>();




    public User() {
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int hashCode() {
        return Long.hashCode(id);
    }
}
