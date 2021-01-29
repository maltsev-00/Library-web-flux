package com.library.mappers;

import com.library.model.User;
import com.library.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class UserMapperTest {

    @Autowired
    private User user;

    @Autowired
    private UserMapper userMapper;

    @Test
    void toUserDto() {
        UserDto userDto = userMapper.toUserDto(user);
        assertNotNull(userDto);
        assertNotNull(userDto.getUsername());
        assertNull(userDto.getPassword());
        assertNotNull(userDto.getRole());
    }
}