package com.soft.archi.software_architecture.services;


import com.soft.archi.software_architecture.entities.UserCredentials;

import java.util.List;

public interface IUserCredentialsService {
    UserCredentials findById(long id);
    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);
    List<UserCredentials> findAll();
    UserCredentials save(UserCredentials userCredentials);
    void delete(Long id);
    void delete(UserCredentials userCredentials);
}
