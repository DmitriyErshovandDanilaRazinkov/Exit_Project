package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.*;
import com.repository.RoleRepository;
import com.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new NotFoundDataBaseException("User not found");
        }

        return user;
    }

    public User findUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new NotFoundDataBaseException("Пользователь не найден");
        }
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

    public List<PlayList> getUserList(Long id) {
        return findUserById(id).getPlayLists();
    }

    public boolean saveUser(User user) {
        userRepository.save(user);
        return true;
    }

    public boolean saveUser(Long id) {
        userRepository.save(findUserById(id));
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }

        return false;
    }

    public List<User> userGetList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public boolean checkUserRights(Long userId, Long playListId, Role_PlayList role) {
        return findUserById(userId).getRoleInPlayLists().get(playListId).getPlayListRole().compare(role);
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
}