package com.kwetter.userservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByFirebaseUuid(String firebaseUuid);
    Optional<User> findByFirebaseUuid(String firebaseUuid);
}
