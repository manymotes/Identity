package com.example.identity.user.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserUpdateInput {
    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;
}
