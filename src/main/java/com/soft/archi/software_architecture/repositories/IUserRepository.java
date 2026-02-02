package com.soft.archi.software_architecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soft.archi.software_architecture.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByNom(String nom);
}