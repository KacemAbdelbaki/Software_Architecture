package com.soft.archi.software_architecture.services;

import com.soft.archi.software_architecture.entities.Role;
import com.soft.archi.software_architecture.entities.User;

import java.util.List;

public interface IRoleService {
    Role save(Role role);
    List<Role> findAll();
    Role findById(Long id);
    void delete(Long id);
    void delete(Role role);
}
