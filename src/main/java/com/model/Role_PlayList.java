package com.model;


import java.util.ArrayList;
import java.util.List;

public enum Role_PlayList {

    ROLE_OWNER(4, "OWNER"),
    ROLE_ADMIN(3, "ADMIN"),
    ROLE_MODERATOR(2, "MODERATOR"),
    ROLE_USER(1, "USER"),
    ROLE_NONE(0, "NONE");

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


    static public List<Role_PlayList> getAll() {
        ArrayList<Role_PlayList> list = new ArrayList<>();
        list.add(ROLE_OWNER);
        list.add(ROLE_ADMIN);
        list.add(ROLE_MODERATOR);
        list.add(ROLE_USER);
        return list;
    }

    public static int compareTo(RoleInPlayList role1, RoleInPlayList role2) {
        return role1.getPlayListRole().compare(role2.getPlayListRole()) ? -1 : 1;
    }

    public boolean compareUnder(Role_PlayList role) {
        return this.id <= role.getId();
    }
}
