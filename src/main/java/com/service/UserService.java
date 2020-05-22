package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.*;
import com.repository.RoleRepository;
import com.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private RoleInPlayListsService roleInPlayListsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new NotFoundDataBaseException("User not found");
        }

        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("Пользователь не найден"));
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        if (!roleRepository.findById(1L).isPresent()) {
            throw new NotFoundDataBaseException("Роль не найдена");
        }
        user.getRoles().add(roleRepository.findById(1L).get());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    public boolean checkUserRights(Long userId, Long playListId, Role_PlayList role) {
        return roleInPlayListsService.getUserRoleInPlayList(userId, playListId).getPlayListRole().compare(role);
    }

    public boolean buyPremium(Long userId, Long period, double cost) {

        User nowUser = findUserById(userId);

        if (nowUser.getCash() < cost) {
            return false;
        }

        if (!nowUser.isPremium()) {
            nowUser.setEndPremium(new Date());
        }

        Date date = nowUser.getEndPremium();

        nowUser.setCash(nowUser.getCash() - cost);
        nowUser.setPremium(true);
        date.setTime(date.getTime() + period * 1000);
        nowUser.setEndPremium(date);

        saveUser(nowUser);

        return true;
    }

    public void addCash(Long userId, double addCash) {

        User nowUser = findUserById(userId);

        nowUser.setCash(nowUser.getCash() + addCash);

        saveUser(nowUser);
    }

    public List<User> getUsersByPlayList(Long id) {
        return userRepository.getAllByPlayList(id);
    }
}