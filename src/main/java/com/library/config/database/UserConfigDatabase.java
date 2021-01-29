package com.library.config.database;

import com.library.enums.UserRole;
import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.valid.Validation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;


@Data
@Slf4j
public class UserConfigDatabase implements DatabaseInitialize {

    private final Validation validator;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    @Override
    public void initialize() {
        Flux<User> users = Flux.just(
                new User(UUID.randomUUID(),"user",passwordEncoder.encode("user"), UserRole.ROLE_USER),
                new User(UUID.randomUUID(),"admin",passwordEncoder.encode("admin"),UserRole.ROLE_ADMIN),
                new User(UUID.randomUUID(),"manager",passwordEncoder.encode("manager"),UserRole.ROLE_MANAGER));

        users.filter(user->validator.validNewOperand(user).equals(true))
                .cast(User.class)
                .flatMap(userRepository::save)
                .subscribe(user -> log.info(user.getUsername()+" is added to database ..."));
    }
}
