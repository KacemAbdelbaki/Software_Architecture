package com.soft.archi.software_architecture.security.jwt.payloads.Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
