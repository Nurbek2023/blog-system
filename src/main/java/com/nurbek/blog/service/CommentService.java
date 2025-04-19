package com.nurbek.blog.service;

import com.nurbek.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    CommentDto getCommentById(Long id);
    List<CommentDto> getAllComments();
    CommentDto updateComment(Long id, CommentDto commentDto);
    void deleteComment(Long id);
}
