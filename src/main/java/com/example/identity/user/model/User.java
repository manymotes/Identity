package com.example.identity.user.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID uuid;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
//    private UserStatus status;
}
