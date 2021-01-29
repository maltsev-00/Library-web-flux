package com.library.service.security;

import com.library.model.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Object> addNewUser(UserDto newUser);

}
