package com.model.DTO.pages.user;

import com.model.DTO.PlayListTo;
import com.model.DTO.UserDetailsTo;
import com.model.PlayList;
import lombok.Data;

import java.util.List;

@Data
public class UserPageTo {

    private UserDetailsTo user;
    private List<PlayListTo> playLists;
}
