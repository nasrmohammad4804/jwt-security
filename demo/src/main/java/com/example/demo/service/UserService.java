package com.example.demo.service;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import java.util.List;
public interface UserService {

    User save(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName,String roleName);
    User getUser(String userName);
    List<User> getAllUser();
}
