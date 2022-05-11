package com.example.eagerreader.app.dto;

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

    @NotNull @NotBlank
    private String password;

}
