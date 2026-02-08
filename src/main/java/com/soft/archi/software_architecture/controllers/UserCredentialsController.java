package com.soft.archi.software_architecture.controllers;

import com.soft.archi.software_architecture.entities.UserCredentials;
import com.soft.archi.software_architecture.services.IUserCredentialsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("credentials")
@RestController
public class UserCredentialsController {

    @Autowired
    private final IUserCredentialsService credentialsService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserCredentials credentials){
        // Encoder le mot de passe avant de sauvegarder
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        UserCredentials savedCredentials = credentialsService.save(credentials);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCredentials);
    }

    @GetMapping()
    public ResponseEntity<List<UserCredentials>> getAllCredentials(){
        List<UserCredentials> credentials = credentialsService.findAll();
        return ResponseEntity.ok(credentials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCredentials> getCredentials(@PathVariable("id") Long id){
        UserCredentials credentials = credentialsService.findById(id);
        return ResponseEntity.ok(credentials);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCredentials(@PathVariable("id") Long id, @Valid @RequestBody UserCredentials credentials){
        UserCredentials existingCredentials = credentialsService.findById(id);
        existingCredentials.setUsername(credentials.getUsername());
        existingCredentials.setEmail(credentials.getEmail());
        
        // Encoder le nouveau mot de passe s'il est fourni
        if (credentials.getPassword() != null && !credentials.getPassword().isEmpty()) {
            existingCredentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        }
        
        existingCredentials.setEnabled(credentials.isEnabled());
        existingCredentials.setUser(credentials.getUser());
        
        UserCredentials updatedCredentials = credentialsService.save(existingCredentials);
        return ResponseEntity.ok(updatedCredentials);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCredentials(@PathVariable("id") Long id){
        credentialsService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Credentials supprimés avec succès");
        return ResponseEntity.ok(response);
    }
}
