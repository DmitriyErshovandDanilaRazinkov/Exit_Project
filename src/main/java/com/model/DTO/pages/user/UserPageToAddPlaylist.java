package com.model.DTO.pages.user;

import com.model.DTO.PlayListTo;
import lombok.Data;

import java.util.List;

@Data
public class UserPageToAddPlaylist {
    private List<PlayListTo> playLists;
    private String result;
}
