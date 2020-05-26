package com.model.enums;


import com.model.RoleInPlayList;

import java.util.ArrayList;
import java.util.List;

public enum Role_PlayList {

    ROLE_NONE(0, "NONE"),
    ROLE_USER(1, "USER"),
    ROLE_MODERATOR(2, "MODERATOR"),
    ROLE_ADMIN(3, "ADMIN"),
    ROLE_OWNER(4, "OWNER");

    private int id;

    private String name;

    Role_PlayList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean compare(Role_PlayList role) {
        return this.id >= role.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public boolean compareUnder(Role_PlayList role) {
        return this.id <= role.getId();
    }
}
