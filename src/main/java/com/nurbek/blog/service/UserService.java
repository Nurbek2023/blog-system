package com.nurbek.blog.service;

import com.nurbek.blog.dto.RegisterUserDto;
import com.nurbek.blog.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    UserDto registerUser(RegisterUserDto userRegisterDto);

}
