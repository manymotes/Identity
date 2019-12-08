package com.example.identity.user;

import java.util.UUID;
import javax.inject.Named;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.identity.user.model.User;
import org.springframework.stereotype.Component;

@Named
@Component
public class UserResolver implements GraphQLQueryResolver {
    public User getUserById(UUID id) {
        return User.builder()
            .firstName("kendall")
            .build();
    }
}
