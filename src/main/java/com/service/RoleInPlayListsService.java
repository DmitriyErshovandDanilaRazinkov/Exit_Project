package com.service;

import com.model.RoleInPlayList;
import com.repository.PlayListRolesRepository;
import com.repository.RoleInPlayListRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleInPlayListsService {

    private RoleInPlayListRepository repository;

    private PlayListRolesRepository rolesRepository;

    public RoleInPlayListsService(RoleInPlayListRepository repository, PlayListRolesRepository rolesRepository) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
    }

    public boolean saveNewRole(RoleInPlayList roleInPlayList) {
        repository.save(roleInPlayList);
        return true;
    }

    public boolean addRoleOwner(RoleInPlayList roleInPlayList) {
        roleInPlayList.getPlayListRoles().addAll(rolesRepository.findAll());
        return true;
    }
}
