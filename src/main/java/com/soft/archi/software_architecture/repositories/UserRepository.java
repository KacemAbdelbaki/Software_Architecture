package com.soft.archi.software_architecture.repositories;


import com.soft.archi.software_architecture.entities.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository(){
        users.add(new User(1L, "user1", "user.1@email.com"));
        users.add(new User(2L, "user2", "user.2@email.com"));
        users.add(new User(3L, "user3", "user.3@email.com"));
        users.add(new User(4L, "user4", "user.4@email.com"));
        users.add(new User(5L, "user5", "user.5@email.com"));
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