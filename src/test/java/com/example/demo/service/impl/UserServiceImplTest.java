package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImplTest")
class UserServiceImplTest {

    private static final Long USER_ID_FIRST  = 1L;

    private static final Long USER_ID_SECOND = 2L;

    private static final String USER_NAME_FIRST = "Petya";

    private static final String USER_NAME_SECOND = "Vasya";

    @Mock
    private UserRepository repository;

    @Test
    void shouldCreateNewUser() {
        User expected = createUser(USER_ID_FIRST,USER_NAME_FIRST);
        when(repository.save(expected)).thenReturn(expected);
        User result = repository.save(expected);
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void shouldUpdateUser() {
        User expected = createUser(USER_ID_FIRST,USER_NAME_FIRST);
        when(repository.save(expected)).thenReturn(expected);
        User result = repository.save(expected);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldReturnAllUser() {
        List<User> expected = List.of(
                createUser(USER_ID_FIRST,USER_NAME_FIRST),
                createUser(USER_ID_SECOND,USER_NAME_SECOND));
        when(repository.findAll()).thenReturn(expected);
        List<User> result = repository.findAll();
        assertThat(expected).containsExactlyElementsOf(result);
    }

    @Test
    void shouldDeleteUserById(){
        User user = createUser(USER_ID_FIRST,USER_NAME_FIRST);
        when(repository.findById(USER_ID_FIRST)).thenReturn(Optional.of(user));
        Optional<User> expected = repository.findById(USER_ID_FIRST);
        repository.deleteById(expected.get().getId());
        verify(repository).findById(USER_ID_FIRST);
        verify(repository).deleteById(USER_ID_FIRST);
    }

    @Test
    void shouldThrowExceptionIfNoUserWithId(){
        when(repository.findById(eq(USER_ID_FIRST))).thenThrow(new ResourceNotFoundException("exception"));
        assertThrows(ResourceNotFoundException.class,() -> repository.findById(USER_ID_FIRST));
    }

    private User createUser(Long id ,String name ){
        return new User(id,name);
    }
}