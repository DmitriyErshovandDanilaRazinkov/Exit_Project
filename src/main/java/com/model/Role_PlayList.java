package com.model;


import java.util.ArrayList;
import java.util.List;

public enum Role_PlayList {

    ROLE_OWNER(4L, "ROLE_OWNER"),
    ROLE_ADMIN(3L, "ROLE_ADMIN"),
    ROLE_MODERATOR(2L, "ROLE_MODERATOR"),
    ROLE_USER(1L, "ROLE_USER");

    private Long id;

    private String name;

    Role_PlayList(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean compare(Role_PlayList role) {
        return this.id >= role.getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    static public List<Role_PlayList> getAll() {
        ArrayList<Role_PlayList> list = new ArrayList<>();
        list.add(ROLE_OWNER);
        list.add(ROLE_ADMIN);
        list.add(ROLE_MODERATOR);
        list.add(ROLE_USER);
        return list;
    }

}
