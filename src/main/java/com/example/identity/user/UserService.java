package com.example.identity.user;

import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

import com.example.identity.user.model.User;
import com.example.identity.user.model.UserInput;
import com.example.identity.user.model.UserResponse;
import com.example.identity.user.model.UserUpdateInput;
import com.example.identity.user.passwordService.PasswordService;
import com.example.identity.user.repository.UserRepository;
import com.example.identity.util.exceptions.NotFoundException;

@Named
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordService passwordService;

    public UserResponse createUser(UserInput userInput) {

        User user = User.builder()
            .email(userInput.getEmail())
            .firstName(userInput.getFirstName())
            .lastName(userInput.getLastName())
            .password(passwordService.hashPassword(userInput.getPassword()))
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

    public UserResponse updateUser(UserUpdateInput userUpdateInput) {

        Optional<User> user = userRepository.findById(userUpdateInput.getUuid());
        if (user.isEmpty()) {
//            todo graphql exceptions
//            throw new NotFoundException();
            return null;
        }
        User userToSave = user.get();

        userToSave.setEmail(userUpdateInput.getEmail());
        userToSave.setFirstName(userUpdateInput.getFirstName());
        userToSave.setLastName(userUpdateInput.getLastName());

        User savedUser = userRepository.save(userToSave);

        UserResponse userResponse = UserResponse.builder()
            .email(savedUser.getEmail())
            .firstName(savedUser.getFirstName())
            .lastName(savedUser.getLastName())
            .uuid(savedUser.getUuid())
            .build();

        return userResponse;
    }

    public void deleteUser(UUID userUuid) {
        userRepository.deleteById(userUuid);
    }
}
