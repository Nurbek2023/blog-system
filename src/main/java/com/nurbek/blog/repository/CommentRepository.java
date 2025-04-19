package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long id);
    List<Comment> findByAuthorId(Long authorId);

}
