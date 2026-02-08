package com.soft.archi.software_architecture.services;


import com.soft.archi.software_architecture.entities.Role;
import com.soft.archi.software_architecture.repositories.IRoleRepository;
import com.soft.archi.software_architecture.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private final IRoleRepository roleRepository;


    @Override
    public Role save(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Le rôle ne peut pas être null");
        }
        log.info("Enregistrement du rôle: {}", role.getRole());
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        log.info("Récupération de tous les rôles");
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        log.info("Recherche du rôle avec l'ID: {}", id);
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rôle avec l'ID " + id + " non trouvé"));
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression du rôle avec l'ID: {}", id);
        Role role = findById(id);
        roleRepository.delete(role);
    }

    @Override
    public void delete(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Le rôle ne peut pas être null");
        }
        log.info("Suppression du rôle: {}", role.getRole());
        roleRepository.delete(role);
    }
}
