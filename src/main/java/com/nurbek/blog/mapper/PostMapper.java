package com.nurbek.blog.mapper;
import com.nurbek.blog.dto.PostDto;
import com.nurbek.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.username", target = "authorUsername")
    PostDto toDto(Post post);

    @Mapping(source = "authorId", target = "author.id")
    Post toEntity(PostDto postDto);
}


