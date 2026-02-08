package com.soft.archi.software_architecture.services;


import com.soft.archi.software_architecture.entities.Role;
import com.soft.archi.software_architecture.repositories.IRoleRepository;
import com.soft.archi.software_architecture.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private final IRoleRepository roleRepository;


    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(findById(id));
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
