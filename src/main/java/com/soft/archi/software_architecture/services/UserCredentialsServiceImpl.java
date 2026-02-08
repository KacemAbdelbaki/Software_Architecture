package com.soft.archi.software_architecture.services;


import com.soft.archi.software_architecture.entities.UserCredentials;
import com.soft.archi.software_architecture.repositories.IUserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCredentialsServiceImpl implements IUserCredentialsService {

    @Autowired
    private final IUserCredentialsRepository userCredentialsRepository;

    @Override
    public UserCredentials findById(long id) {
        return userCredentialsRepository.findById(id).get();
    }

    @Override
    public List<UserCredentials> findAll() {
        return userCredentialsRepository.findAll();
    }

    @Override
    public UserCredentials save(UserCredentials userCredentials) {
        return userCredentialsRepository.save(userCredentials);
    }

    @Override
    public void delete(Long id) {
        userCredentialsRepository.delete(findById(id));
    }

    @Override
    public void delete(UserCredentials userCredentials) {
        userCredentialsRepository.delete(userCredentials);
    }
}
