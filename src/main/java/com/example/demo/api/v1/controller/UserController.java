package com.example.demo.api.v1.controller;

import com.example.demo.config.ApiVersion;
import com.example.demo.converter.impl.UserDtoToUserConverter;
import com.example.demo.converter.impl.UserToUserDtoConverter;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = UserController.BASE_URL)
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String BASE_URL = ApiVersion.VERSION_1_0 + "/user";

    final
    UserToUserDtoConverter converterToDto;

    final
    UserDtoToUserConverter converter;

    final
    UserService userService;

    public UserController(UserToUserDtoConverter converterToDto, UserDtoToUserConverter converter, UserService userService) {
        this.converterToDto = converterToDto;
        this.converter = converter;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addUser(@RequestBody @Valid UserDto userDto){
        User user =  converter.convert(userDto);
        userService.addUser(user);
        LOG.debug("In User Controller - Received POST request to add new user, request URI:[{}] ", WebUtil.getFullRequestUri());
        return new  ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        if(id != null) {
            userService.deleteUser(id);
            LOG.debug("Received Delete request to delete user, request URI:[{}] ", WebUtil.getFullRequestUri());
        }else{
            throw new ResourceNotFoundException("In User Controller - Received Delete request to delete user where id = null");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserDto userdto){
        User user = converter.convert(userdto);
        userService.updateUser(user);
        LOG.debug("In User Controller - Received PUT request to update user, request URI:[{}] ", WebUtil.getFullRequestUri());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        LOG.debug("In User Controller - Received Get request all user");
        List<User> list = userService.getAllUser();
        List<UserDto> listDto = converterToDto.convertList(list);
        return new ResponseEntity<>(listDto,HttpStatus.OK);
    }

}

