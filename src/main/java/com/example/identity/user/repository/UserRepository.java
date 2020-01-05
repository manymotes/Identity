package com.example.identity.user.repository;


import java.util.UUID;

import com.example.identity.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, UUID> {

    @Query(value = "SELECT uuid, first_name, last_name,  password, email, age, gender FROM users u WHERE u.email = :email", nativeQuery = true)
    User findUserByEmail(@Param("email") String email);
}
