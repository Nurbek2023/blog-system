package com.nurbek.blog.mapper;

import com.nurbek.blog.dto.CommentDto;
import com.nurbek.blog.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mappings({
            @Mapping(source = "post.id", target = "postId"),
            @Mapping(source = "author.id", target = "authorId"),
            @Mapping(source = "author.username", target = "username")
    })
    CommentDto toDto(Comment comment);

    @Mappings({
            @Mapping(source = "postId", target = "post.id"),
            @Mapping(source = "authorId", target = "author.id"),
            @Mapping(target = "author.username", ignore = true) // usually ignored when reconstructing from ID
    })
    Comment toEntity(CommentDto commentDto);
}


