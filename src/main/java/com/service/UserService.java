package com.service;

import com.exceptions.DontHaveRightsException;
import com.exceptions.NotFoundDataBaseException;
import com.model.*;
import com.model.DTO.UserDetailsTo;
import com.model.DTO.UserFormTO;
import com.model.enums.Role_PlayList;
import com.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private RoleInPlayListsService roleInPlayListsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static UserDetailsTo getCurrentUser() {
        return (UserDetailsTo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Override
    public UserDetailsTo loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDetailsTo detailsTo = userToUserDetailsTo(user);
        saveUser(user);

        return detailsTo;
    }

    public UserDetailsTo findUserToById(Long id) {
        return userToUserDetailsTo(findUserById(id));
    }

    User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("Пользователь не найден"));
    }

    public List<UserDetailsTo> allUsers() {
        return userRepository.findAll().stream()
                .map(UserService::userToUserDetailsTo)
                .collect(Collectors.toList());
    }

    public void addUser(UserFormTO userForm) {
        User userFromDB = userRepository.findByUsername(userForm.getUserName());

        if (userFromDB != null) {
            throw new DontHaveRightsException("Вы не можете создать пользователя с таким именем");
        }

        User newUser = new User();
        newUser.setUsername(userForm.getUserName());
        newUser.setRole(User.ROLE_USER);
        newUser.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userRepository.save(newUser);
    }


    void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void addToAdmin(Long id) {
        User nowUser = findUserById(id);
        nowUser.setRole(User.ROLE_ADMIN);
        saveUser(nowUser);
    }

    public boolean checkUserRights(Long playListId, Role_PlayList role) {
        return roleInPlayListsService.getUserRoleInPlayList(getCurrentUser().getId(), playListId).getPlayListRole().compare(role);
    }

    public boolean buyPremium(Long userId, Long period, double cost) {

        User nowUser = findUserById(userId);

        if (nowUser.getCash() < cost) {
            return false;
        }

        if (!nowUser.isPremium()) {
            nowUser.setEndPremium(new Date());
        }

        Date date = new Date(nowUser.getEndPremium().getTime() + period * 1000L);

        nowUser.setCash(nowUser.getCash() - cost);
        nowUser.setPremium(true);
        nowUser.setEndPremium(date);

        saveUser(nowUser);

        return true;
    }

    public void addCash(Long userId, double addCash) {

        User nowUser = findUserById(userId);

        nowUser.setCash(nowUser.getCash() + addCash);

        saveUser(nowUser);
    }

    public boolean checkUserPremium() {
        User nowUser = findUserById(getCurrentUser().getId());
        if (!nowUser.isPremium()) return false;
        Date nowTime = new Date();
        nowUser.setPremium(!(nowTime.compareTo(nowUser.getEndPremium()) > 0));
        return nowUser.isPremium();
    }

    public static UserDetailsTo userToUserDetailsTo(User user) {
        UserDetailsTo userTo = new UserDetailsTo();
        userTo.setId(user.getId());
        userTo.setUsername(user.getUsername());
        userTo.setRole(user.getRole());
        userTo.setPassword(user.getPassword());
        userTo.setCash(user.getCash());
        userTo.setEndPremium(user.getEndPremium());
        userTo.setPremium(user.isPremium());
        return userTo;
    }

}