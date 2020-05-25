package com.model.DTO.pages.playList;

import com.model.DTO.RoleInPlayListTo;
import com.model.RoleInPlayList;

import java.util.Set;

import lombok.Data;

@Data
public class PlayListListUserTO {

    private Long playListId;
    private Set<RoleInPlayListTo> roleInPlayListList;
    private String joinCode;
    private int nowUserRole;
}
