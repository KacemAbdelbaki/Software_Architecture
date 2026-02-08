package com.soft.archi.software_architecture.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Role {

    @Id
    @Schema(hidden = true)
    @GeneratedValue()
    Long id;

    @NotBlank(message = "Le nom du rôle est obligatoire")
    @Pattern(regexp = "^(ADMIN|USER|MODERATOR|GUEST)$", 
             message = "Le rôle doit être: ADMIN, USER, MODERATOR ou GUEST")
    @Column(unique = true)
    String role;
}
