package com.nurbek.blog.service;

import com.nurbek.blog.dto.PostDto;
import com.nurbek.blog.entity.Post;
import com.nurbek.blog.mapper.PostMapper;
import com.nurbek.blog.repository.PostRepository;
import com.nurbek.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        post.setAuthor(userRepository.findById(postDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found")));
        post.setCreatedAt(java.time.LocalDateTime.now());
        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return postMapper.toDto(post);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);

    }
}
