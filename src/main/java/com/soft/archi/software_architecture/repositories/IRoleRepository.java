package com.soft.archi.software_architecture.repositories;

import com.soft.archi.software_architecture.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}