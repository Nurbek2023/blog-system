package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save post and find it by author ID")
    void findByAuthorId() {
        // Given
        User user = new User();
        user.setUsername("postuser");
        user.setEmail("postuser@example.com");
        user.setPassword("testpass");
        user.setRole("USER");
        user = userRepository.save(user);

        Post post = new Post();
        post.setTitle("First Post");
        post.setContent("Content of the first post.");
        post.setAuthor(user);
        postRepository.save(post);

        // When
        List<Post> posts = postRepository.findByAuthorId(user.getId());

        // Then
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("First Post");
        assertThat(posts.get(0).getAuthor().getUsername()).isEqualTo("postuser");

    }
}