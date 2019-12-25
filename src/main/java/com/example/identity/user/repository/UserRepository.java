package com.example.identity.user.repository;


import java.util.UUID;

import com.example.identity.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
}
