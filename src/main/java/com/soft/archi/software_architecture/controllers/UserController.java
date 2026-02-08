package com.soft.archi.software_architecture.controllers;

import com.soft.archi.software_architecture.entities.User;
import com.soft.archi.software_architecture.security.jwt.JwtUtils;
import com.soft.archi.software_architecture.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {

    @Autowired
    private final IUserService userServices;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/add")
    public User add(@RequestBody User user){
        return userServices.save(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> add(@PathVariable("id") Long id){
        userServices.delete(userServices.findById(id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public List<User> getUsers(){
        return userServices.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id){
        return userServices.findById(id);
    }

    @GetMapping("/jwt/{jwt}")
    public ResponseEntity<?> getJwt(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok(jwtUtils.generateJwtToken(jwt));
    }

    @GetMapping("/jwt/validate/{jwt}")
    public ResponseEntity<?> validateJwt(@PathVariable("jwt") String jwt){
        return jwtUtils.validateJwtToken(jwt) ? ResponseEntity.ok().build() : ResponseEntity.status(400).build();
    }
}
