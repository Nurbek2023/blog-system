package com.nurbek.blog.bootstrap;


import com.nurbek.blog.repository.CommentRepository;
import com.nurbek.blog.repository.PostRepository;
import com.nurbek.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nurbek.blog.entity.User;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.entity.Comment;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Bean
    public ApplicationRunner loadData(){
        return args -> {
            loadUsers();
            loadPosts();
            loadComments();
        };
    }

    private void loadUsers() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/users.csv"), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",");
                User user = new User();
//                user.setId(Long.parseLong(fields[0]));
                user.setUsername(fields[0]);
                user.setEmail(fields[1]);
                user.setPassword(fields[2]); // Consider encoding password
                user.setRole(fields[3]);
                userRepository.save(user);
            });
            System.out.println("✅ Loaded users from CSV");
        }
    }

    private void loadPosts() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/posts.csv"), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",");
                Post post = new Post();
//                post.setId(Long.parseLong(fields[0]));
                post.setTitle(fields[1]);
                post.setContent(fields[2]);
                User user = userRepository.findById(Long.parseLong(fields[3])).orElse(null);
                post.setAuthor(user);
                postRepository.save(post);
            });
        }
    }

    private void loadComments() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/comments.csv"), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",");
                Comment comment = new Comment();
//                comment.setId(Long.parseLong(fields[0]));
                comment.setContent(fields[1]);
                User user = userRepository.findById(Long.parseLong(fields[2])).orElse(null);
                Post post = postRepository.findById(Long.parseLong(fields[3])).orElse(null);
                comment.setAuthor(user);
                comment.setPost(post);
                commentRepository.save(comment);
            });
        }
    }
}
