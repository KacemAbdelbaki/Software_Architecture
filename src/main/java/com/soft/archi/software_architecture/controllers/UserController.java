package com.soft.archi.software_architecture.controllers;

import com.soft.archi.software_architecture.entities.User;
import com.soft.archi.software_architecture.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private final IUserService userServices;

    @PostMapping("/add")
    public User add(@RequestBody User user){
        return userServices.addUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void add(@PathVariable("id") Long id){
        userServices.deleteUser(userServices.getUser(id));
    }

    @GetMapping("/get")
    public List<User> getUsers(){
        return userServices.getUsers();
    }

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") Integer id){
        return userServices.getUser(id);
    }
}
