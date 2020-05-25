package com.model.DTO.pages.playList;

import com.model.DTO.RoleInPlayListTo;
import com.model.RoleInPlayList;

import java.util.List;

import lombok.Data;

@Data
public class PlayListUsersTO {

    private Long playListId;

    private List<RoleInPlayListTo> roleInPlayListList;

    private Long NowUserRole;
}
