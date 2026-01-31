package com.soft.archi.software_architecture.entities;

//import jakarta.persistence.*;
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
//@Entity
@Component
public class User implements Serializable {

//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    String nom;
    String email;
}
