package com.nurbek.blog.repository;

import com.nurbek.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


    @Modifying
    @Query(value = "ALTER SEQUENCE users_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();

}
