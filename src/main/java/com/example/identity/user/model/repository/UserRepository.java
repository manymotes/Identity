package com.example.identity.user.model.repository;


import java.util.UUID;

import com.example.identity.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, UUID> {
}
