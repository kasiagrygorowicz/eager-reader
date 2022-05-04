package com.example.eagerreader.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
