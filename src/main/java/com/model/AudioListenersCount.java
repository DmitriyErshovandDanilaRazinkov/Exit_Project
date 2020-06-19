package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table
public class AudioListenersCount {
    @ApiModelProperty
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty
    @OneToOne(fetch = FetchType.LAZY)
    private FileAud file;
}
