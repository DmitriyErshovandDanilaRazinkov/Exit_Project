package com.model.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TagTo {

    private Long id;

    private Long countListen;

    private String name;
}
