package com.soft.archi.software_architecture.controllers;

import com.soft.archi.software_architecture.entities.User;
import com.soft.archi.software_architecture.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private final IUserService userServices;

    List users = new ArrayList();

    @PostMapping("/add")
    public User add(@RequestBody User user){
        return userServices.addUser(user);
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
