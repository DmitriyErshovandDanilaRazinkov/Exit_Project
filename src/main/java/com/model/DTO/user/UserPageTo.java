package com.model.DTO.user;

import com.model.PlayList;
import com.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserPageTo {

    private User user;
    private List<PlayList> playLists;
}
