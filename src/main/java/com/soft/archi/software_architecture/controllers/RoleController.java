package com.soft.archi.software_architecture.controllers;

import com.soft.archi.software_architecture.entities.Role;
import com.soft.archi.software_architecture.services.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("roles")
@RestController
public class RoleController {

    @Autowired
    private final IRoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Role role){
        Role savedRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id){
        Role role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable("id") Long id, @Valid @RequestBody Role role){
        Role existingRole = roleService.findById(id);
        existingRole.setRole(role.getRole());
        Role updatedRole = roleService.save(existingRole);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id){
        roleService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rôle supprimé avec succès");
        return ResponseEntity.ok(response);
    }
}
