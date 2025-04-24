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
import org.springframework.transaction.annotation.Transactional;


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
//            resetDatabase(); // 👈 resets IDs
//            loadUsers();
//            loadPosts();
//            loadComments();
        };
    }

    private void loadUsers() throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("⚠️ Users already exist, skipping loading.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/users.csv"), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    User user = new User();
                    user.setUsername(fields[0]);
                    user.setEmail(fields[1]);
                    user.setPassword(fields[2]); // ⚠️ Still recommend encoding
                    user.setRole(fields[3]);
                    userRepository.save(user);
                }
            });
            System.out.println("✅ Loaded users from CSV");
        }
    }


    private void loadPosts() throws Exception {

        if (postRepository.count() > 0) {
            System.out.println("⚠️ Posts already exist, skipping loading.");
            return;
        }


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/posts.csv"), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    Post post = new Post();
                    post.setTitle(fields[0]);
                    post.setContent(fields[1]);
                    Long userId = Long.parseLong(fields[2]);
                    User author = userRepository.findById(userId).orElse(null);
                    post.setAuthor(author);
                    postRepository.save(post);
                }
            });
            System.out.println("✅ Loaded posts from CSV");
        }
    }

    private void loadComments() throws Exception {

        if (commentRepository.count() > 0) {
            System.out.println("⚠️ Posts already exist, skipping loading.");
            return;
        }


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/comments.csv"), StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    Comment comment = new Comment();
                    comment.setContent(fields[1]);
                    Long userId = Long.parseLong(fields[2]);
                    Long postId = Long.parseLong(fields[3]);
                    User author = userRepository.findById(userId).orElse(null);
                    Post post = postRepository.findById(postId).orElse(null);
                    comment.setAuthor(author);
                    comment.setPost(post);
                    commentRepository.save(comment);
                }
            });
            System.out.println("✅ Loaded comments from CSV");
        }
    }

    @Transactional
    protected void resetDatabase() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();

        try {
            userRepository.resetSequence();
            postRepository.resetSequence();
            commentRepository.resetSequence();
        } catch (Exception e) {
            System.out.println("⚠️ Skipping sequence reset (probably running H2 or wrong sequence name)");
        }

        System.out.println("✅ Database reset");
    }





}
