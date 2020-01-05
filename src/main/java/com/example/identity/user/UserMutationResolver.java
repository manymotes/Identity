package com.example.identity.user;

import javax.inject.Inject;
import javax.inject.Named;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.identity.user.model.UserInput;
import com.example.identity.user.model.UserResponse;
import com.example.identity.user.model.UserUpdateInput;


@Named
public class UserMutationResolver implements GraphQLMutationResolver {

    @Inject
    private UserService userService;

    public UserResponse createUser(UserInput userInput) {
        return userService.createUser(userInput);
    }

    public UserResponse updateUser(UserUpdateInput userUpdateInput) {
        return userService.updateUser(userUpdateInput);
    }
}
