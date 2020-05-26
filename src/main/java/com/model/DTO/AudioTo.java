package com.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AudioTo {

    private Long id;

    private String name;

    private boolean premium;

    private Long countListen;

    private FileTo file;

    private List<TagTo> tags;
}
