package com.nurbek.blog.mapper;

import com.nurbek.blog.dto.LikeDto;
import com.nurbek.blog.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LikeMapper {

    @Mappings({
            @Mapping(source = "post.id", target = "postId"),
            @Mapping(source = "user.id", target = "userId")
    })
    LikeDto toDto(Like like);

    @Mappings({
            @Mapping(source = "postId", target = "post.id"),
            @Mapping(source = "userId", target = "user.id")
    })
    Like toEntity(LikeDto likeDto);
}

