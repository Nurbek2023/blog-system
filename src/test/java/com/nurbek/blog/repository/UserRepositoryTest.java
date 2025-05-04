package com.nurbek.blog.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("pass123");
        userRepository.save(user);
        userRepository.flush(); // Ensure DB is updated

        userRepository.findAll().forEach(System.out::println); // 🖨️ Print all users

        boolean exists = userRepository.existsByUsername("testuser");

        assertThat(userRepository.existsByUsername("testuser")).isTrue();
    }

    @Test
    void existsByEmail() {
        User user = new User();
        user.setUsername("anotheruser");
        user.setEmail("another@example.com");
        user.setPassword("pass456");
        user.setRole("USER");

        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("another@example.com");
        assertThat(exists).isTrue();
    }

    @Test
    void findByUsername() {
        User user = new User();
        user.setUsername("cooluser");
        user.setEmail("cool@example.com");
        user.setPassword("pass789");
        user.setRole("ADMIN");

        userRepository.save(user);

        // Update: Handle the Optional properly
        User found = userRepository.findByUsername("cooluser")
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("cool@example.com");
        assertThat(found.getRole()).isEqualTo("ADMIN");
    }
}