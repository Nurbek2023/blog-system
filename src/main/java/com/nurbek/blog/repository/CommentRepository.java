package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long id);
    List<Comment> findByAuthorId(Long authorId);

    @Modifying
    @Query(value = "ALTER SEQUENCE comments_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();


}
