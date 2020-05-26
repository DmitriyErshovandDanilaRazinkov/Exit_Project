package com.model.DTO.pages.playList;

import com.model.Audio;
import com.model.DTO.AudioTo;
import com.model.DTO.PlayListTo;
import com.model.DTO.RoleInPlayListTo;
import com.model.DTO.UserDetailsTo;
import com.model.RoleInPlayList;
import lombok.Data;

import java.util.List;

@Data
public class PlayListPageTo {

    private PlayListTo playList;
    private RoleInPlayListTo userRole;
    private List<AudioTo> audioList;
}
