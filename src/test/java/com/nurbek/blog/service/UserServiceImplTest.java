package com.nurbek.blog.service;

import com.nurbek.blog.dto.UserDto;
import com.nurbek.blog.entity.User;
import com.nurbek.blog.mapper.UserMapper;
import com.nurbek.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        UserDto dto = new UserDto();
        dto.setUsername("nurbek");
        dto.setEmail("nurbek@example.com");

        User user = new User();
        user.setUsername("nurbek");
        user.setEmail("nurbek@example.com");

        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);

        UserDto result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("nurbek", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void getUserById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("nurbek");

        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setUsername("nurbek");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        UserDto result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        List<User> users = Arrays.asList(user1, user2);

        UserDto dto1 = new UserDto();
        dto1.setId(1L);
        dto1.setUsername("user1");

        UserDto dto2 = new UserDto();
        dto2.setId(2L);
        dto2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user1)).thenReturn(dto1);
        when(userMapper.toDto(user2)).thenReturn(dto2);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void updateUser() {
        Long id = 1L;
        UserDto dto = new UserDto();
        dto.setUsername("updatedUser");
        dto.setEmail("updated@example.com");

        User user = new User();
        user.setId(id);
        user.setUsername("oldUser");
        user.setEmail("old@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);

        UserDto result = userService.updateUser(id, dto);

        assertEquals("updatedUser", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser() {
        Long id = 1L;
        userService.deleteUser(id);
        verify(userRepository).deleteById(id);
    }
}