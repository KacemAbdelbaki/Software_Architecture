package com.soft.archi.software_architecture.security.jwt.payloads.Responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {
    Long id;
    String username;
    String email;
    List<String> roles;
}
