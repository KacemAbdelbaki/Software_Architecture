package com.soft.archi.software_architecture.repositories;


import com.soft.archi.software_architecture.entities.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository(){
        users.add(new User(1L, "Jean Dupont", "jean.dupont@example.com"));
        users.add(new User(2L, "Marie Curie", "marie.curie@science.org"));
        users.add(new User(3L, "Victor Hugo", "victor.hugo@literature.fr"));
        users.add(new User(4L, "Thomas Pesquet", "thomas.space@esa.int"));
        users.add(new User(5L, "Sophie Germain", "sophie.math@uni.fr"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public User delete(User user) {
        User temp = user;
        users.remove(user);
        return temp;
    }

    public User findById(Long id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
}