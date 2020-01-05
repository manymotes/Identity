package com.example.identity.session.model;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue
    private UUID uuid;
    private UUID userUuid;
    private Instant expiration;
    private Instant createdAt;
}
