package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Comment;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void findByPostId() {
        // Given
        User user = new User();
        user.setUsername("commentuser");
        user.setEmail("commentuser@example.com");
        user.setPassword("testpass");
        user = userRepository.save(user);

        Post post = new Post();
        post.setTitle("Post with Comment");
        post.setContent("Post content.");
        post.setAuthor(user);
        post = postRepository.save(post);

        Comment comment = new Comment();
        comment.setContent("Nice post!");
        comment.setAuthor(user);
        comment.setPost(post);
        commentRepository.save(comment);

        // When
        List<Comment> comments = commentRepository.findByPostId(post.getId());

        // Then
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getContent()).isEqualTo("Nice post!");
        assertThat(comments.get(0).getAuthor().getUsername()).isEqualTo("commentuser");
    }

    @Test
    @DisplayName("Should find comments by author ID")
    void findByAuthorId() {
        // Given
        User user = new User();
        user.setUsername("anotheruser");
        user.setEmail("another@example.com");
        user.setPassword("pass");
        user = userRepository.save(user);

        Post post = new Post();
        post.setTitle("Another Post");
        post.setContent("More content.");
        post.setAuthor(user);
        post = postRepository.save(post);

        Comment comment = new Comment();
        comment.setContent("Second comment");
        comment.setAuthor(user);
        comment.setPost(post);
        commentRepository.save(comment);

        // When
        List<Comment> comments = commentRepository.findByAuthorId(user.getId());

        // Then
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getContent()).isEqualTo("Second comment");
    }
}