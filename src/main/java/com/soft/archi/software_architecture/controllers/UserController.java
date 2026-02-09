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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> add(@Valid @RequestBody User user){
        User savedUser = userServices.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userServices.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Utilisateur supprimé avec succès");
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userServices.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        User user = userServices.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user){
        User existingUser = userServices.findById(id);
        existingUser.setNom(user.getNom());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        User updatedUser = userServices.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/jwt/{jwt}")
    public ResponseEntity<?> getJwt(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok(jwtUtils.generateJwtToken(jwt));
    }

    @GetMapping("/jwt/validate/{jwt}")
    public ResponseEntity<?> validateJwt(@PathVariable("jwt") String jwt){
        return jwtUtils.validateJwtToken(jwt) ? ResponseEntity.ok().build() : ResponseEntity.status(400).build();
    }

    @GetMapping("/jwt/body/{jwt}")
    public ResponseEntity<?> getJwtBody(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok(jwtUtils.getBodyFromJwtToken(jwt));
    }
}
