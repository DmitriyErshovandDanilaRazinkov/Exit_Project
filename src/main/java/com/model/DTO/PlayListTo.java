package com.model.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlayListTo {

    private Long id;

    private boolean isPrivate;

    private String name;

    private String joinCode;
}
