package com.model.DTO.pages.user;

import com.model.DTO.AudioTo;
import com.model.DTO.TagTo;
import com.model.PlayList;
import lombok.Data;

import java.util.List;

@Data
public class AudioPageTo {

    private List<TagTo> tags;
    private List<AudioTo> listAudio;
}
