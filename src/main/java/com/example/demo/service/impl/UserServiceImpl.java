package com.example.demo.service.impl;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    final
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value ="usersCache",key = "'all'")
    public void addUser(User user) {
        LOG.debug("In userService -  Successfully create user :" + user.getName());
        userRepository.save(user);
    }

    @Override
    @Transactional
    @CacheEvict(value ="usersCache",key = "'all'")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("In userService - Not found user with ID: " + id));
        userRepository.delete(user);
        LOG.debug("In userService -  Successfully delete user with id : " + id );
    }

    @Override
    @Transactional
    @CacheEvict(value ="usersCache",key = "'all'")
    public void updateUser(User user) {
        Optional<User> oldUser = userRepository.findById(user.getId());
        if (oldUser.isPresent()) {
            userRepository.saveAndFlush(user);
            LOG.debug("In userService -  Successfully update user with id " + user.getId());
        } else {
            throw new ResourceNotFoundException("In userService - Failed to update user with ID:" + user.getId());
        }
    }

    @Cacheable(value = "usersCache",key = "'all'")
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
