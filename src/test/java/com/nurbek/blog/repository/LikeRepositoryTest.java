package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Like;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("likeuser");
        user.setEmail("like@example.com");
        user.setPassword("password");
        user.setRole("USER");
        user = userRepository.save(user);

        post = new Post();
        post.setTitle("Like Test Post");
        post.setContent("Post content for testing likes");
        post.setAuthor(user);
        post = postRepository.save(post);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
    }

    @Test
    void findByPostId() {
        List<Like> likes = likeRepository.findByPostId(post.getId());
        assertThat(likes).hasSize(1);
        assertThat(likes.get(0).getUser().getId()).isEqualTo(user.getId());

    }

    @Test
    void findByUserId() {
        List<Like> likes = likeRepository.findByUserId(user.getId());
        assertThat(likes).hasSize(1);
        assertThat(likes.get(0).getPost().getId()).isEqualTo(post.getId());

    }

    @Test
    void findByUserIdAndPostId() {
        Optional<Like> optionalLike = likeRepository.findByUserIdAndPostId(user.getId(), post.getId());
        assertThat(optionalLike).isPresent();
        assertThat(optionalLike.get().getUser().getId()).isEqualTo(user.getId());

    }

    @Test
    void existsByPostIdAndUserId() {
        boolean exists = likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
        assertThat(exists).isTrue();

    }

    @Test
    void deleteByPostIdAndUserId() {
        likeRepository.deleteByPostIdAndUserId(post.getId(), user.getId());
        boolean exists = likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
        assertThat(exists).isFalse();

    }
}