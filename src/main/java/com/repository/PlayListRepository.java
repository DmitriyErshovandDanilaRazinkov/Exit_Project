package com.repository;

import com.model.PlayList;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {
    PlayList findByJoinCode(String code);

    @Query("select p from PlayList p, RoleInPlayList role where role.idComp.user.id = :user and p = role.idComp.playList")
    List<PlayList> getAllByWithPlayList(@Param("user") Long userId);

}
