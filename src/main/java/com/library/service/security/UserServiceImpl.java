package com.library.service.security;

import com.library.enums.UserRole;
import com.library.mappers.UserMapper;
import com.library.model.User;
import com.library.model.dto.UserDto;
import com.library.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Data
@Slf4j
public class UserServiceImpl implements ReactiveUserDetailsService, UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).cast(UserDetails.class)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND," User is not found in database")));
    }

    @Override
    public Mono<Object> addNewUser(UserDto newUser){

        User user = new User(UUID.randomUUID(),newUser.getUsername(),
                passwordEncoder.encode(newUser.getPassword()),
                UserRole.ROLE_USER);

        return userRepository.findByUsername(user.getUsername())
                .flatMap(error-> Mono.error(new ResponseStatusException(HttpStatus.CREATED,
                        newUser.getUsername()+" already created")))
                .switchIfEmpty(userRepository.save(user));
    }

}
