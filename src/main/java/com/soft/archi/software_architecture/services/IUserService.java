package com.soft.archi.software_architecture.services;

import com.soft.archi.software_architecture.entities.User;

import java.util.List;

public interface IUserService {
    User findById(long id);
    List<User> findAll();
    User save(User user);
    void delete(User user);
    void delete(Long id);

}
