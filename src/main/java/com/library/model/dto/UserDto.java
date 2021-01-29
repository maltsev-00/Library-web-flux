package com.library.model.dto;

import com.library.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Length(min =3, max = 50,message = "Username length can be 50 max")
    private String username;

    @Length(min =3, max = 130,message = "Password length can be 130 max , min 3")
    private String password;

    @NotNull
    private UserRole role;
}
