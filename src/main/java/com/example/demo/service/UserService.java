package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    List<User> getAllUser();

    Optional<User> getUserById(Long id );

}