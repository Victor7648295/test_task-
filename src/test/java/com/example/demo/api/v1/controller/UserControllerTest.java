package com.example.demo.api.v1.controller;

import com.example.demo.converter.impl.UserDtoToUserConverter;
import com.example.demo.converter.impl.UserToUserDtoConverter;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.impl.UserServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("UserController")
class UserControllerTest {

    private static final Long USER_ID_FIRST  = 1L;

    private static final Long USER_ID_SECOND = 2L;

    private static final String USER_NAME_FIRST = "Petya";

    private static final String USER_NAME_SECOND = "Vasya";

    @MockBean
    UserServiceImpl userService;

    @MockBean
    UserToUserDtoConverter converterToDto;

    @MockBean
    UserDtoToUserConverter converter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldAddUser() throws Exception {
        User user = createUser(USER_ID_FIRST,USER_NAME_FIRST);
        UserDto userDto = createUserDto(USER_ID_FIRST,USER_NAME_SECOND);
        String  expectedJson = mapper.writeValueAsString(user);
        when(converter.convert(userDto)).thenReturn(user);

        mockMvc.perform(post(UserController.BASE_URL + "/add")
                .contentType(APPLICATION_JSON_VALUE)
                .contentType(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete(UserController.BASE_URL + "/delete/{id}",USER_ID_FIRST)
        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService,times(1)).deleteUser(USER_ID_FIRST);
    }

    @Test
    void updateUser() throws Exception {
        User user = createUser(USER_ID_FIRST,USER_NAME_FIRST);
        UserDto userDto = createUserDto(USER_ID_FIRST,USER_NAME_FIRST);
        when(converter.convert(userDto)).thenReturn(user);

        String expectedJson = mapper.writeValueAsString(user);

        mockMvc.perform(put(UserController.BASE_URL + "/update")
        .contentType(APPLICATION_JSON)
        .contentType(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        List<User> users = List.of(createUser(USER_ID_FIRST,USER_NAME_FIRST),
                                    createUser(USER_ID_SECOND,USER_NAME_SECOND));
        List<UserDto> usersDto = List.of(createUserDto(USER_ID_FIRST,USER_NAME_FIRST),
                                        createUserDto(USER_ID_SECOND,USER_NAME_SECOND));
        when(userService.getAllUser()).thenReturn(users);
        when(converterToDto.convertList(users)).thenReturn(usersDto);
        String expectedJson = mapper.writeValueAsString(usersDto);

        mockMvc.perform(get(UserController.BASE_URL + "/all")
        .contentType(APPLICATION_JSON)
        .contentType(expectedJson))
                .andExpect(status().isOk());

    }

    private User createUser(Long id ,String name ) {
        return new User(id,name);
    }

    private UserDto createUserDto(Long id ,String name ){ return new UserDto(id,name);
    }

}