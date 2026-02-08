package com.soft.archi.software_architecture.services;


import com.soft.archi.software_architecture.entities.UserCredentials;
import com.soft.archi.software_architecture.repositories.IUserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCredentialsServiceImpl implements IUserCredentialsService {

    @Autowired
    private final IUserCredentialsRepository userCredentialsRepository;

    @Override
    public UserCredentials findById(long id) {
        log.info("Recherche des credentials avec l'ID: {}", id);
        return userCredentialsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Credentials avec l'ID " + id + " non trouvés"));
    }

    @Override
    public List<UserCredentials> findAll() {
        log.info("Récupération de tous les credentials");
        return userCredentialsRepository.findAll();
    }

    @Override
    public UserCredentials save(UserCredentials userCredentials) {
        if (userCredentials == null) {
            throw new IllegalArgumentException("Les credentials ne peuvent pas être null");
        }
        log.info("Enregistrement des credentials pour: {}", userCredentials.getUsername());
        return userCredentialsRepository.save(userCredentials);
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression des credentials avec l'ID: {}", id);
        UserCredentials credentials = findById(id);
        userCredentialsRepository.delete(credentials);
    }

    @Override
    public void delete(UserCredentials userCredentials) {
        if (userCredentials == null) {
            throw new IllegalArgumentException("Les credentials ne peuvent pas être null");
        }
        log.info("Suppression des credentials: {}", userCredentials.getUsername());
        userCredentialsRepository.delete(userCredentials);
    }
}
