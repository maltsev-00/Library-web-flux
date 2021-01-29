package com.library.config.database;

import com.library.config.database.DatabaseInit;
import com.library.model.User;
import com.library.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;

@Data
public class UserDataBase implements DatabaseInit {

    @Autowired
    private Map<String, User> mapUser;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initialize(){
        userRepository.save(mapUser.get("user")).subscribe();
        userRepository.save(mapUser.get("admin")).subscribe();
        userRepository.save(mapUser.get("manager")).subscribe();
    }
}
