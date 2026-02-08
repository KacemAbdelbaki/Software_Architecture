package com.soft.archi.software_architecture.entities;

//import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class User implements Serializable {

    @Id
    @Schema(hidden = true)
    @GeneratedValue()
    Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    String nom;
    
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Format de numéro de téléphone invalide")
    String phoneNumber;

    @ManyToOne
    @NotNull(message = "Le rôle est obligatoire")
    Role role;
}
