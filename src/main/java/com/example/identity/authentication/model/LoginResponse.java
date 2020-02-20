package com.example.identity.authentication.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    //    private List<Feature> features;
    private AuthState authState;
    private UUID userUuid;
    private String jwt;
}
