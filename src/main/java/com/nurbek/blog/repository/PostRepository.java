package com.nurbek.blog.repository;

import com.nurbek.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAuthorId(Long id);

    @Modifying
    @Query(value = "ALTER SEQUENCE comments_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();

}
