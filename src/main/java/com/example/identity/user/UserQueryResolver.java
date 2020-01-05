package com.example.identity.user;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.identity.user.model.UserResponse;

@Named
public class UserQueryResolver implements GraphQLQueryResolver {

    @Inject
    private UserService userService;

    public UserResponse getUserById(UUID id) {
        return UserResponse.builder()
            .firstName("kendall")
            .build();
    }
}
