package com.nurbek.blog.mapper;

import com.nurbek.blog.dto.UserDto;
import com.nurbek.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
