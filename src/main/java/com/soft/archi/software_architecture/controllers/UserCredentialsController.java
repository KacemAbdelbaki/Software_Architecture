package com.soft.archi.software_architecture.controllers;

import com.soft.archi.software_architecture.entities.Role;
import com.soft.archi.software_architecture.entities.User;
import com.soft.archi.software_architecture.entities.UserCredentials;
import com.soft.archi.software_architecture.security.jwt.JwtUtils;
import com.soft.archi.software_architecture.security.jwt.payloads.Requests.LoginRequest;
import com.soft.archi.software_architecture.security.jwt.payloads.Requests.SignupRequest;
import com.soft.archi.software_architecture.security.jwt.payloads.Responses.MessageResponse;
import com.soft.archi.software_architecture.security.jwt.payloads.Responses.UserInfoResponse;
import com.soft.archi.software_architecture.services.IRoleService;
import com.soft.archi.software_architecture.services.IUserCredentialsService;
import com.soft.archi.software_architecture.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("credentials")
@RestController
public class UserCredentialsController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    final private IUserCredentialsService userCredentialsService;
    @Autowired
    final private IUserService userService;
    @Autowired
    final private IRoleService roleService;


//    @PostMapping("/add")
//    public ResponseEntity<?> add(@Valid @RequestBody UserCredentials credentials){
//        // Encoder le mot de passe avant de sauvegarder
//        credentials.setPassword(encoder.encode(credentials.getPassword()));
//        UserCredentials savedCredentials = userCredentialsService.save(credentials);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedCredentials);
//    }

    @GetMapping()
    public ResponseEntity<List<UserCredentials>> getAllCredentials(){
        List<UserCredentials> credentials = userCredentialsService.findAll();
        return ResponseEntity.ok(credentials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCredentials> getCredentials(@PathVariable("id") Long id){
        UserCredentials credentials = userCredentialsService.findById(id);
        return ResponseEntity.ok(credentials);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCredentials(@PathVariable("id") Long id, @Valid @RequestBody UserCredentials credentials){
        UserCredentials existingCredentials = userCredentialsService.findById(id);
        existingCredentials.setUsername(credentials.getUsername());
        existingCredentials.setEmail(credentials.getEmail());
        
        // Encoder le nouveau mot de passe s'il est fourni
        if (credentials.getPassword() != null && !credentials.getPassword().isEmpty()) {
            existingCredentials.setPassword(encoder.encode(credentials.getPassword()));
        }
        
        existingCredentials.setEnabled(credentials.isEnabled());
        existingCredentials.setUser(credentials.getUser());
        
        UserCredentials updatedCredentials = userCredentialsService.save(existingCredentials);
        return ResponseEntity.ok(updatedCredentials);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCredentials(@PathVariable("id") Long id){
        userCredentialsService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Credentials supprimés avec succès");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserCredentials userCredentials = (UserCredentials) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userCredentials);
        System.err.println(jwtCookie);

        List<String> roles = userCredentials.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userCredentials
                        .getId(),
                        userCredentials.getUsername(),
                        userCredentials.getEmail(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userCredentialsService.findByEmail(signUpRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userCredentialsService.findByEmail(signUpRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserCredentials userCredentials = new UserCredentials(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                true
                );

        Role strRole = signUpRequest.getRole();
        Role role = new Role();

        if (strRole == null) {
            role = roleService.findByRole("GUEST");
        } else {
            role = roleService.findByRole(strRole.getRole());
        }

        userCredentials.setRole(role);
        User user = new User("kacem", "0666361642");
        user = userService.save(user);
        userCredentials.setUser(user);
        System.err.println(userCredentials);
        userCredentialsService.save(userCredentials);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Content.";
    }
}
