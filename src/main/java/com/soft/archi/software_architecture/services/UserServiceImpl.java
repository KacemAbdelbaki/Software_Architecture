package com.soft.archi.software_architecture.services;

import com.soft.archi.software_architecture.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soft.archi.software_architecture.entities.User;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private final IUserRepository userRepository;

    @Override
    public User findById(long id) {
        log.info("Recherche de l'utilisateur avec l'ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur avec l'ID " + id + " non trouvé"));
    }

    @Override
    public List<User> findAll() {
        log.info("Récupération de tous les utilisateurs");
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }
        log.info("Enregistrement de l'utilisateur: {}", user.getNom());
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }
        log.info("Suppression de l'utilisateur: {}", user.getNom());
        userRepository.delete(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression de l'utilisateur avec l'ID: {}", id);
        User user = findById(id);
        userRepository.delete(user);
    }
}
