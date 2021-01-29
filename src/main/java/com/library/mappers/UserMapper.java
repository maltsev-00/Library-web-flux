package com.library.mappers;

import com.library.model.User;
import com.library.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = User.class)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);

}
