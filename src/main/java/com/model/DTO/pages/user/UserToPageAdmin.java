package com.model.DTO.pages.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserToPageAdmin {

    private Long id;

    private String username;

    private String role;

    private double cash = 0;

    private boolean premium = false;

    private Date endPremium;
}
