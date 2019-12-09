package com.example.identity.user;

import javax.inject.Inject;
import javax.inject.Named;

import com.example.identity.user.model.User;
import com.example.identity.user.model.UserInput;
import com.example.identity.user.model.UserResponse;
import com.example.identity.user.repository.UserRepository;

@Named
public class UserService {

    @Inject
    private UserRepository userRepository;

    public UserResponse createUser(UserInput userInput) {

        User user = User.builder()
            .email(userInput.getEmail())
            .firstName(userInput.getFirstName())
            .lastName(userInput.getLastName())
            .password(userInput.getPassword())
            .build();

        user = userRepository.save(user);

        UserResponse userResponse = UserResponse.builder()
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .uuid(user.getUuid())
            .build();

        return userResponse;
    }
}
