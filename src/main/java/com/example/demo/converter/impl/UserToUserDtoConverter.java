package com.example.demo.converter.impl;

import com.example.demo.converter.Converter;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public Class<User> getSourceClass() {
        return  User.class;
    }

    @Override
    public Class<UserDto> getTargetClass() {
        return UserDto.class;
    }

    @Override
    public UserDto convert(User source) {
        return new UserDto(source.getId(),source.getName());
    }

    @Override
    public List<UserDto> convertList(Collection<User> sourceList) {
        return sourceList
                .stream()
                .map(this::convert)
                .collect(toList());
    }
}