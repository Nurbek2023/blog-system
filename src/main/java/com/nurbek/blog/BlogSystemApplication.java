package com.nurbek.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nurbek.blog.entity.*;
import com.nurbek.blog.repository.*;
import org.springframework.boot.CommandLineRunner;


import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class BlogSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSystemApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner dataLoader(UserRepository userRepo,
//										PostRepository postRepo,
//										CommentRepository commentRepo,
//										LikeRepository likeRepo) {
//		return args -> {
//			// 1. Create a User
//			User user = new User();
//			user.setUsername("nurbek");
//			user.setEmail("nurbek@example.com");
//			user.setPassword("password"); // in real apps, use encoded password
//			userRepo.save(user);
//
//			// 2. Create a Post
//			Post post = new Post();
//			post.setTitle("My First Post");
//			post.setContent("Hello, this is my first post!");
//			post.setAuthor(user);
//			post.setCreatedAt(LocalDateTime.now());
//			postRepo.save(post);
//
//			// 3. Create a Comment
//			Comment comment = new Comment();
//			comment.setContent("Great post!");
//			comment.setPost(post);
//			comment.setAuthor(user);
//			comment.setCreatedAt(LocalDateTime.now());
//			commentRepo.save(comment);
//
//			// 4. Add a Like
//			Like like = new Like();
//			like.setPost(post);
//			like.setUser(user);
//			likeRepo.save(like);
//
//			System.out.println("🌱 Test data inserted into H2 database!");
//		};
//	}
}
