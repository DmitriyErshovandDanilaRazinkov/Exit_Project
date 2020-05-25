package com.model.DTO;

import lombok.Data;

@Data
public class AudioTo {

    private Long id;

    private String name;

    private boolean premium;

    private FileTo file;
}
