package com.example.demo.converter.impl;

import com.example.demo.converter.Converter;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public Class<UserDto> getSourceClass() {
        return UserDto.class;
    }

    @Override
    public Class<User> getTargetClass() {
        return User.class;
    }

    @Override
    public User convert(UserDto source) {
        return new User(source.getId(),source.getName());
    }

    @Override
    public List<User> convertList(Collection<UserDto> sourceList) {
        return sourceList
                .stream()
                .map(this::convert)
                .collect(toList());
    }


}