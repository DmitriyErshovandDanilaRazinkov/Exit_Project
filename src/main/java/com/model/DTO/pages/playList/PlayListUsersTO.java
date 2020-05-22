package com.model.DTO.pages.playList;

import com.model.RoleInPlayList;

import java.util.List;

import lombok.Data;

@Data
public class PlayListUsersTO {

    private Long playListId;

    private List<RoleInPlayList> roleInPlayListList;

    private Long NowUserRole;
}
