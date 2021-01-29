package com.library.controller;

import com.library.model.dto.UserDto;
import com.library.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("registration")
    public Mono<Object> addNewUser(@Valid @RequestBody UserDto user) {
        log.info("User : "+user.toString());
        return userService.addNewUser(user);
    }

}
