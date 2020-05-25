package com.model.DTO.pages.user;

import com.model.PlayList;
import lombok.Data;

import java.util.List;

@Data
public class UserPageToAddPlaylist {
    private List<PlayList> playLists;
    private String result;
}
