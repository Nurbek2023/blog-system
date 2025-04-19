package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAuthorId(Long id);
}
