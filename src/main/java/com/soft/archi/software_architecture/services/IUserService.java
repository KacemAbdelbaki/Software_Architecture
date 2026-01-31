package com.soft.archi.software_architecture.services;

import com.soft.archi.software_architecture.entities.User;

import java.util.List;

public interface IUserService {
    User getUser(long id);
    List<User> getUsers();
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
}
