package com.example.eagerreader.app.dto;

import com.example.eagerreader.security.validation.ValidPassword;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {

    @NotNull @NotBlank
    private String email;

    @ValidPassword(message = "Password has to be 8 character long, contain minimum 3 digits, 1 uppercase letter and 1 special character") @NotBlank
    private String password;

}
