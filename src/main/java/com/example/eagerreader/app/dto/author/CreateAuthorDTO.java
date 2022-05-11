package com.example.eagerreader.app.dto.author;

import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateAuthorDTO {

    @NotBlank(message = "Firstname cannot be blank")
    private String firstname;

    @NotBlank(message = "Lastname cannot be blank")
    private String lastname;

    @Nullable
    private String info;
}
