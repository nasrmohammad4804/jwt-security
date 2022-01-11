package com.example.demo.service.impl;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User save(User user) {
        log.info("saving new user {} to the database", user.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to  the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void addRoleToUser(String userName, String roleName) {
        log.info("adding role {} to  user with name : {} ", roleName, userName);
        User user = userRepository.findUserByUsername(userName);
        Role role = roleRepository.findRoleByName(roleName);
        user.getRoleList().add(role);

    }

    @Override
    public User getUser(String userName) {
        log.info("fetching user {} ", userName);

        return userRepository.findUserByUsername(userName);
    }

    @Override
    public List<User> getAllUser() {
        log.info("fetching all users");

        return userRepository.findAll();
    }


}
