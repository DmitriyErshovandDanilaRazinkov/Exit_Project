package com.repository;

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u from User u, RoleInPlayList role where role.idComp.playList = :playList")
    List<User> getAllByPlayList(@Param("playList") Long playList);

}
