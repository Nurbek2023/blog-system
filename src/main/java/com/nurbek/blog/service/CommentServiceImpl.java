package com.nurbek.blog.service;

import com.nurbek.blog.dto.CommentDto;
import com.nurbek.blog.entity.Comment;
import com.nurbek.blog.mapper.CommentMapper;
import com.nurbek.blog.repository.CommentRepository;
import com.nurbek.blog.repository.PostRepository;
import com.nurbek.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthor(userRepository.findById(commentDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found")));
        comment.setPost(postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found")));
        comment.setCreatedAt(LocalDateTime.now());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto getCommentById(Long id) {
        return commentMapper.toDto(commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found")));

    }

    @Override
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setContent(commentDto.getContent());
        return commentMapper.toDto(commentRepository.save(comment));

    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
