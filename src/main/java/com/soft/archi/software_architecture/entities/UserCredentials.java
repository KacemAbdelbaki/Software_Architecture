package com.soft.archi.software_architecture.entities;

//import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class UserCredentials implements Serializable {

    @Id
    @Schema(hidden = true)
    @GeneratedValue()
    Long id;

    String username;
    String email;
    String password;
    boolean enabled;

    @OneToOne
    User user;
}
