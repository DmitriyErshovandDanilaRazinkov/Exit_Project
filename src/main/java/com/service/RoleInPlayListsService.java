package com.service;

import com.model.RoleInPlayList;
import com.repository.RoleInPlayListRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleInPlayListsService {

    private RoleInPlayListRepository repository;


    public RoleInPlayListsService(RoleInPlayListRepository repository) {
        this.repository = repository;
    }

    public boolean saveRole(RoleInPlayList roleInPlayList) {
        repository.save(roleInPlayList);
        return true;
    }

    public boolean deleteRole(RoleInPlayList roleInPlayList) {
        repository.delete(roleInPlayList);
        return true;
    }

}
