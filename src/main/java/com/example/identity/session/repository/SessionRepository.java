package com.example.identity.session.repository;

import java.util.UUID;

import com.example.identity.session.model.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, UUID> {
}
