package com.model.DTO;

import com.model.enums.Role_PlayList;
import lombok.Data;

@Data
public class RoleInPlayListTo {

    private UserDetailsTo user;

    private PlayListTo playList;

    private Role_PlayList playListRole;
}
