package com.example.identity.user;

import java.util.UUID;
import javax.inject.Named;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.identity.user.model.User;
import com.example.identity.user.model.UserResponse;
import org.springframework.stereotype.Component;

@Named
@Component
public class UserResolver implements GraphQLQueryResolver {
    public UserResponse getUserById(UUID id) {
        return UserResponse.builder()
            .firstName("kendall")
            .build();
    }

    public UserResponse createUser(User user) {
        return UserResponse.builder()
            .build();
    }
}
