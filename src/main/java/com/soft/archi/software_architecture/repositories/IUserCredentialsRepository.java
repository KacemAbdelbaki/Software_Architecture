package com.soft.archi.software_architecture.repositories;

import com.soft.archi.software_architecture.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);
}